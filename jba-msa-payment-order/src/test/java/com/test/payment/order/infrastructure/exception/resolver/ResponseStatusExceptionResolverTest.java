package com.test.payment.order.infrastructure.exception.resolver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.server.ResponseStatusException;

import static com.test.payment.order.util.MockDataUtils.URL_TEST;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {ResponseStatusExceptionResolver.class})
@SuppressWarnings("java:S2699")
class ResponseStatusExceptionResolverTest
        extends ErrorResolverTest<ResponseStatusExceptionResolver> {
    @ParameterizedTest
    @ValueSource(ints = {400, 404, 500})
    void givenResponseStatusExceptionWhenResponseStatusExceptionResolverIsCalledThenExpectError(
            final int httpStatusCode) {
        final var exception = new ResponseStatusException(HttpStatusCode.valueOf(httpStatusCode));
        callToErrorResolverAndExpectError(
                exception,
                "Response Error",
                exception.getMessage(),
                httpStatusCode,
                URL_TEST
        );
    }

    @Test
    void givenWrongExceptionWhenResponseStatusExceptionResolverIsCalledThenThrowClassCastException() {
        callToErrorResolverAndExpectClassCastException(new Exception("Response status exception test"));
    }
}
