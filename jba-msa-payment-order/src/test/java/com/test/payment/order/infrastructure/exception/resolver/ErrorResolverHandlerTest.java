package com.test.payment.order.infrastructure.exception.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.payment.order.infrastructure.exception.ErrorResolverHandler;
import com.test.payment.order.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import reactor.test.StepVerifier;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ErrorResolverHandlerTest {

    private ErrorResolverHandler handler;
    private ObjectMapper objectMapper;
    private Map<Class<? extends Throwable>, ErrorResolver<?>> resolvers;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        resolvers = new HashMap<>();

        handler = new ErrorResolverHandler(
                objectMapper,
                resolvers
        );

        // set version manually (no @Value en unit test)
        TestUtils.setField(handler, "version", "1.0.0");

        // simula @PostConstruct
        TestUtils.invokePostConstruct(handler);
    }

    @Test
    void shouldHandleIllegalArgumentException() {
        var exchange = createExchange();

        StepVerifier.create(
                        handler.handle(exchange, new IllegalArgumentException("invalid"))
                )
                .verifyComplete();

        var response = exchange.getResponse();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
    }

    @Test
    void shouldHandleCodeException() {
        var exchange = createExchange();
        var exception = new CodeException(10, "custom error");

        StepVerifier.create(
                        handler.handle(exchange, exception)
                )
                .verifyComplete();

        assertThat(exchange.getResponse().getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldFallbackToUnexpectedErrorResolver() {
        var exchange = createExchange();

        StepVerifier.create(
                        handler.handle(exchange, new RuntimeException("boom"))
                )
                .verifyComplete();

        assertThat(exchange.getResponse().getStatusCode())
                .isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private MockServerWebExchange createExchange() {
        return MockServerWebExchange.from(
                MockServerHttpRequest.get("/payment-orders/123").build()
        );
    }
}
