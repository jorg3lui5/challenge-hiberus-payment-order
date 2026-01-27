package com.test.payment.order.infrastructure.exception.resolver;

import com.test.payment.order.infrastructure.input.adapter.rest.config.PropertiesTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;

import java.nio.file.AccessDeniedException;

import static com.test.payment.order.util.MockDataUtils.URL_TEST;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {AccessDeniedExceptionResolver.class})
@ImportAutoConfiguration({PropertiesTest.class})
class AccessDeniedExceptionResolverTest extends ErrorResolverTest<AccessDeniedExceptionResolver> {
    private static final String EXCEPTION_MESSAGE = "Access denied test exception";

    @Test
    void givenAccessDeniedExceptionWhenAccessDeniedExceptionResolverIsCalledThenExpectError() {
        final var exception = new AccessDeniedException(EXCEPTION_MESSAGE);
        callToErrorResolverAndExpectError(
                exception,
                "Access denied",
                exception.getMessage(),
                HttpStatus.FORBIDDEN.value(),
                URL_TEST
        );
    }

}