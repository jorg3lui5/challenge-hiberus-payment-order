package com.test.payment.order.infrastructure.exception.resolver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;

import static com.test.payment.order.util.MockDataUtils.URL_TEST;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {NotFoundErrorCustomerResolver.class})
class NotFoundErrorCustomerResolverTest extends ErrorResolverTest<NotFoundErrorCustomerResolver> {
    @Test
    void givenExceptionWhenNotFoundExceptionResolverIsCalledThenExpectError() {
        final var exception = new Exception("Not found test exception");
        callToErrorResolverAndExpectError(
                exception,
                "Not found",
                "Not found payment order data",
                HttpStatus.NOT_FOUND.value(),
                URL_TEST
        );
        assertNotNull(exception.getMessage());

    }
}