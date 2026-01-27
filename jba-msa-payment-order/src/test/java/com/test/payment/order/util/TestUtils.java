package com.test.payment.order.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import lombok.experimental.UtilityClass;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.MissingRequestValueException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;
import reactor.util.context.ContextView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.test.payment.order.util.MockDataUtils.HTTP_REQUEST_HEADERS_TEST;

@UtilityClass
@SuppressWarnings("java:S1118")
public final class TestUtils {

    public static final String HEADER_CALLER_NAME = "d1dda86d-f95b-4da2-8875-b84053b363ac";
    public static final String CUSTOMER_AFFILIATION_ID = "2d55b920-4dd7-4b0c-8a6d-536f1bbb069a";
    public static final String CUSTOMER_USER_ID = "3e208ec3-f843-4732-b4c2-d5da28817783";
    public static final String CONTEXT_INTERNAL_CALLER_NAME_VALUE = "d1dda86d-f95b-4da2-8875-b84053b363ac";
    private static final HttpHeaders HTTP_REQUEST_HEADERS = new HttpHeaders();
    private static final ContextView CONTEXT_VIEW;
    private static final String ERROR_CODE_FIELD_NAME = "errorCode";
    private static final String ERROR_CODE_LABEL = "Error code";

    static {
        HTTP_REQUEST_HEADERS.add("Constant.HTTP_HEADER_APP_NAME", "BackOffice");
        CONTEXT_VIEW = buildContextView(
                Stream.concat(
                                HTTP_REQUEST_HEADERS.entrySet().stream(),
                                Map.of("CONTEXT_INTERNAL_CALLER_NAME", CONTEXT_INTERNAL_CALLER_NAME_VALUE).entrySet().stream()
                        )
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (requestHeadersValue, contextValue) -> requestHeadersValue))
        );
    }

    @NonNull
    public static HttpHeaders getRequestHeaders() {
        return HTTP_REQUEST_HEADERS;
    }

    @NonNull
    public static ContextView getContextView() {
        return CONTEXT_VIEW;
    }

    @NonNull
    public static ContextView buildContextView(@NonNull final Map<?, ?> data) {
        return new ContextView() {
            @NonNull
            @Override
            @SuppressWarnings("unchecked")
            public <T> T get(@NonNull final Object key) {
                return (T) data.get(key);
            }

            @Override
            public boolean hasKey(@NonNull final Object key) {
                return data.containsKey(key);
            }

            @Override
            public int size() {
                return data.size();
            }

            @NonNull
            @Override
            public Stream<Map.Entry<Object, Object>> stream() {
                return data.entrySet()
                        .stream()
                        .map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey(), entry.getValue()));
            }
        };
    }

    @NonNull
    private static Context mockContext(@NonNull final Context context) {
        return context.put("CONTEXT_INTERNAL_CALLER_NAME", CONTEXT_INTERNAL_CALLER_NAME_VALUE)
                .putAllMap(
                        getRequestHeaders().entrySet()
                                .stream()
                                .filter(entry -> HTTP_REQUEST_HEADERS_TEST.contains(entry.getKey()))
                                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().get(0)))
                );
    }

    @NonNull
    public static <T> Mono<T> mockContext(@NonNull final Mono<T> mono) {
        return mono.contextWrite(TestUtils::mockContext);
    }

    @NonNull
    public static <T> Flux<T> mockContext(@NonNull final Flux<T> flux) {
        return flux.contextWrite(TestUtils::mockContext);
    }

    @NonNull
    public static MissingRequestValueException getMissingRequestValueException() throws NoSuchMethodException {
        final var fieldName = ERROR_CODE_FIELD_NAME;
        return new MissingRequestValueException(
                fieldName,
                String.class,
                ERROR_CODE_LABEL,
                MethodParameter.forParameter(Error.class.getMethod(fieldName, String.class).getParameters()[0])
        );
    }

    @NonNull
    public static WebClientResponseException buildWebClientResponseException(int statusCode, @Nullable byte[] body) {
        return new WebClientResponseException(
                statusCode,
                HttpStatus.valueOf(statusCode).name(),
                getRequestHeaders(),
                body,
                StandardCharsets.UTF_8
        );
    }

    @NonNull
    public static WebClientRequestException buildWebClientRequestException(int statusCode, @Nullable String path) throws URISyntaxException {
        return new WebClientRequestException(
                new Throwable(),
                HttpMethod.GET,
                new URI(path),
                getRequestHeaders()
        );
    }

    @NonNull
    public static WebExchangeBindException buildWebExchangeBindException(@NonNull final String errorMessage)
            throws NoSuchMethodException {
        final var target = ERROR_CODE_FIELD_NAME;
        final var objectName = Error.class.getName();
        final var bindException = new BindException(target, objectName);
        bindException.getBindingResult().addError(new FieldError(objectName, target, errorMessage));
        return new WebExchangeBindException(
                MethodParameter.forParameter(Error.class.getMethod(target, String.class).getParameters()[0]),
                bindException
        );
    }

    @NonNull
    public static InputStream getResourceInputStream(@NonNull final String resourceName) {
        return Objects.requireNonNull(TestUtils.class.getResourceAsStream("/spreadsheets/invoices.xlsx"));
    }

    public static void setField(Object target, String fieldName, Object value) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void invokePostConstruct(Object target) {
        for (Method method : target.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(PostConstruct.class)) {
                try {
                    method.setAccessible(true);
                    method.invoke(target);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
