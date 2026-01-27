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
@ContextConfiguration(classes = {UnexpectedErrorResolver.class})
@SuppressWarnings("java:S2699")
class UnexpectedErrorResolverTest extends ErrorResolverTest<UnexpectedErrorResolver> {
    @Test
    void givenExceptionWhenUnexpectedErrorResolverIsCalledThenExpectError() {
        final var exception = new Exception("Unexpected error test exception");
        callToErrorResolverAndExpectError(
                exception,
                UNEXPECTED_ERROR,
                "An unexpected error has occurred",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                URL_TEST
        );
    }
}
