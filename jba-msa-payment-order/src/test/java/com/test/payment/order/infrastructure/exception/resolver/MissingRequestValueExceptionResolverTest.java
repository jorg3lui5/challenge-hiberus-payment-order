package com.test.payment.order.infrastructure.exception.resolver;

import com.test.payment.order.infrastructure.input.adapter.rest.config.PropertiesTest;
import com.test.payment.order.util.InfrastructureTestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static com.test.payment.order.util.MockDataUtils.URL_TEST;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {MissingRequestValueExceptionResolver.class})
@ImportAutoConfiguration({
        PropertiesTest.class
})
@SuppressWarnings("java:S2699")
class MissingRequestValueExceptionResolverTest
        extends ErrorBadRequestResolverTest<MissingRequestValueExceptionResolver> {
    @Test
    void givenConstraintViolationExceptionWhenConstraintViolationExceptionResolverIsCalledThenReturnError()
            throws NoSuchMethodException {
        final var exception = InfrastructureTestUtils.getMissingRequestValueException();
        callToErrorResolverAndExpectError(
                exception,
                "Missing Input",
                "The input data is missing",
                exception.getStatusCode().value(),
                URL_TEST
        );
    }

    @Test
    void givenWrongExceptionWhenMissingRequestValueExceptionResolverIsCalledThenThrowClassCastException() {
        callToErrorResolverAndExpectClassCastException(
                new Exception("Missing request value test exception"));
    }
}