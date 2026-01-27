package com.test.payment.order.infrastructure.exception.resolver;

import com.test.payment.order.infrastructure.input.adapter.rest.config.PropertiesTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;

import static com.test.payment.order.util.Constants.BAD_INPUT;
import static com.test.payment.order.util.MockDataUtils.URL_TEST;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {IllegalArgumentExceptionResolver.class})
@ImportAutoConfiguration({PropertiesTest.class})
@SuppressWarnings("java:S2699")
class IllegalArgumentExceptionResolverTest
        extends ErrorBadRequestResolverTest<IllegalArgumentExceptionResolver> {
    private static final String EXCEPTION_MESSAGE = "Illegal argument test exception";

    @Test
    void givenIllegalArgumentExceptionWhenIllegalArgumentExceptionResolverIsCalledThenReturnError() {
        final var exception = new IllegalArgumentException(EXCEPTION_MESSAGE);
        callToErrorResolverAndExpectError(
                exception,
                BAD_INPUT,
                "The input data is invalid",
                HttpStatus.BAD_REQUEST.value(),
                URL_TEST
        );
    }

    @Test
    void givenWrongExceptionWhenIllegalArgumentExceptionResolverIsCalledThenThrowClassCastException() {
        callToErrorResolverAndExpectClassCastException(new Exception(EXCEPTION_MESSAGE));
    }
}