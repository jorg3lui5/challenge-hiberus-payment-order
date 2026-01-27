package com.test.payment.order.infrastructure.exception.resolver;

import com.test.payment.order.util.InfrastructureTestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static com.test.payment.order.util.Constants.BAD_INPUT;
import static com.test.payment.order.util.MockDataUtils.URL_TEST;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {WebExchangeBindExceptionResolver.class})
@SuppressWarnings("java:S2699")
class WebExchangeBindExceptionResolverTest
        extends ErrorBadRequestResolverTest<WebExchangeBindExceptionResolver> {
    private static final String EXCEPTION_MESSAGE = "Web exchange bind test exception";

    @Test
    void givenWebExchangeBindExceptionWhenWebExchangeBindExceptionResolverIsCalledThenExpectError()
            throws NoSuchMethodException, SecurityException {
        final var exception = InfrastructureTestUtils.buildWebExchangeBindException(EXCEPTION_MESSAGE);
        callToErrorResolverAndExpectError(
                exception,
                BAD_INPUT,
                exception.getReason(),
                exception.getStatusCode().value(),
                URL_TEST
        );
    }

    @Test
    void givenWrongExceptionWhenWebExchangeBindExceptionResolverIsCalledThenThrowClassCastException() {
        callToErrorResolverAndExpectClassCastException(new Exception(EXCEPTION_MESSAGE));
    }
}