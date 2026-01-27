package com.test.payment.order.infrastructure.exception.resolver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import static com.test.payment.order.util.Constants.UNEXPECTED_ERROR;
import static com.test.payment.order.util.MockDataUtils.URL_TEST;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {CodeExceptionResolver.class})
@SuppressWarnings("java:S2699")
class CodeExceptionResolverTest extends ErrorResolverTest<CodeExceptionResolver> {
    private static final String EXCEPTION_MESSAGE = "Error code test exception";

    @Test
    void givenCodeExceptionWhenCodeExceptionResolverIsCalledThenExpectError() {
        final var exception = new CodeException(HttpStatus.INTERNAL_SERVER_ERROR.value(), EXCEPTION_MESSAGE);
        callToErrorResolverAndExpectError(
                exception,
                UNEXPECTED_ERROR,
                "Error code test exception",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                URL_TEST
        );
    }

    @Test
    void givenWrongExceptionWhenCodeExceptionResolverIsCalledThenThrowClassCastException() {
        callToErrorResolverAndExpectClassCastException(new Exception(EXCEPTION_MESSAGE));
    }
}
