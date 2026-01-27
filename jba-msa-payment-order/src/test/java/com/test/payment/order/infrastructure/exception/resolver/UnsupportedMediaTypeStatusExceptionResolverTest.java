package com.test.payment.order.infrastructure.exception.resolver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;

import static com.test.payment.order.util.MockDataUtils.URL_TEST;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {UnsupportedMediaTypeStatusExceptionResolver.class})
@SuppressWarnings("java:S2699")
class UnsupportedMediaTypeStatusExceptionResolverTest extends ErrorResolverTest<UnsupportedMediaTypeStatusExceptionResolver> {
    private static final String EXCEPTION_MESSAGE = "Unsupported media type";

    @Test
    void givenUnsupportedMediaTypeStatusExceptionWhenUnsupportedMediaTypeStatusExceptionResolverIsCalledThenExpectError
            () {
        final var exception = new UnsupportedMediaTypeStatusException(EXCEPTION_MESSAGE);
        callToErrorResolverAndExpectError(
                exception,
                EXCEPTION_MESSAGE,
                exception.getReason(),
                HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),
                URL_TEST
        );
    }

    @Test
    void givenWrongExceptionWhenUnsupportedMediaTypeStatusExceptionResolverIsCalledThenThrowClassCastException() {
        callToErrorResolverAndExpectClassCastException(new Exception(EXCEPTION_MESSAGE));
    }
}