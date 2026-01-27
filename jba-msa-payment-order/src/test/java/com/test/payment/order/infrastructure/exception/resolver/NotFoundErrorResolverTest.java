package com.test.payment.order.infrastructure.exception.resolver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;

import static com.test.payment.order.util.MockDataUtils.URL_TEST;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {NotFoundErrorResolver.class})
@SuppressWarnings("java:S2699")
class NotFoundErrorResolverTest extends ErrorResolverTest<NotFoundErrorResolver> {
    @Test
    void givenExceptionWhenNotFoundExceptionResolverIsCalledThenExpectError() {
        final var exception = new Exception("Not found test exception");
        callToErrorResolverAndExpectError(
                exception,
                "Not found",
                exception.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                URL_TEST
        );
    }
}