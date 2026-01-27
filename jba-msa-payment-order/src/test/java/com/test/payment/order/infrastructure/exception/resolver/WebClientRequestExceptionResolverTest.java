package com.test.payment.order.infrastructure.exception.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;

import java.net.URISyntaxException;

import static com.test.payment.order.util.Constants.CLIENT_ERROR;
import static com.test.payment.order.util.MockDataUtils.URL_TEST;
import static com.test.payment.order.util.TestUtils.buildWebClientRequestException;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {WebClientRequestExceptionResolver.class, ObjectMapper.class})
@SuppressWarnings("java:S2699")
class WebClientRequestExceptionResolverTest extends ErrorResolverTest<WebClientRequestExceptionResolver> {

    @Test
    void givenInvalidWebClientJsonBodyWhenWebClientResponseExceptionResolverIsCalledThenExpectError() throws URISyntaxException {
        final var status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        final var exception = buildWebClientRequestException(status, URL_TEST);
        callToErrorResolverAndExpectError(
                exception,
                CLIENT_ERROR,
                "Connection refused of client path=" + URL_TEST,
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                URL_TEST
        );
        assertNotNull(exception);

    }
}