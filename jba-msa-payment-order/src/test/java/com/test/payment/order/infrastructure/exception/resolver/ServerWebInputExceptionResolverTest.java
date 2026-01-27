package com.test.payment.order.infrastructure.exception.resolver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.server.ServerWebInputException;

import static com.test.payment.order.util.Constants.BAD_INPUT;
import static com.test.payment.order.util.MockDataUtils.URL_TEST;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {ServerWebInputExceptionResolver.class})
@SuppressWarnings("java:S2699")
class ServerWebInputExceptionResolverTest
        extends ErrorBadRequestResolverTest<ServerWebInputExceptionResolver> {
    private static final String EXCEPTION_MESSAGE = "Server web input test exception";
    private static final String CAUSE_ERROR = "Bad input request";

    @Test
    void givenServerWebInputExceptionWhenServerWebInputExceptionResolverIsCalledThenExpectError() {

        final var exception =
                new ServerWebInputException(EXCEPTION_MESSAGE, null, new Throwable(CAUSE_ERROR));
        callToErrorResolverAndExpectError(
                exception,
                BAD_INPUT,
                exception.getReason(),
                HttpStatus.BAD_REQUEST.value(),
                URL_TEST
        );
    }

    @Test
    void givenWrongExceptionWhenServerWebInputExceptionResolverIsCalledThenThrowClassCastException() {
        callToErrorResolverAndExpectClassCastException(new Exception(EXCEPTION_MESSAGE));
    }

}
