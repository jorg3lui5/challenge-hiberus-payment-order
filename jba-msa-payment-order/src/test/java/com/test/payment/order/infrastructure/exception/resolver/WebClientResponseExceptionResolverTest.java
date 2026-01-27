package com.test.payment.order.infrastructure.exception.resolver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.payment.order.infrastructure.input.adapter.rest.bs.bean.ErrorModel;
import com.test.payment.order.util.ErrorUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;

import static com.test.payment.order.util.Constants.UNEXPECTED_ERROR;
import static com.test.payment.order.util.MockDataUtils.URL_TEST;
import static com.test.payment.order.util.TestUtils.buildWebClientResponseException;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {WebClientResponseExceptionResolver.class, ObjectMapper.class})
@SuppressWarnings("java:S2699")
class WebClientResponseExceptionResolverTest extends ErrorResolverTest<WebClientResponseExceptionResolver> {
  private static final String EXCEPTION_MESSAGE = "Web client response test exception";

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void givenInvalidWebClientJsonBodyWhenWebClientResponseExceptionResolverIsCalledThenExpectError() {
    final var status = HttpStatus.INTERNAL_SERVER_ERROR.value();
    final var exception = buildWebClientResponseException(status, "".getBytes());
    callToErrorResolverAndExpectError(
      exception,
      UNEXPECTED_ERROR,
      "The service failed to serve the request due to an internal error",
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            URL_TEST
    );
    assertNotNull(exception.getMessage());

  }

  @Test
  void givenWebClientJsonBodyWhenWebClientResponseExceptionResolverIsCalledThenExpectError() throws JsonProcessingException {
    final var status = HttpStatus.NOT_FOUND.value();
    final var error = new ErrorModel()
            .title(EXCEPTION_MESSAGE)
            .detail(EXCEPTION_MESSAGE)
            .instance(ErrorUtils.buildErrorCode(status))
            .type("path");
    final var exception = buildWebClientResponseException(status, objectMapper.writeValueAsBytes(error));
    callToErrorResolverAndExpectError(
      exception,
      error.getTitle(),
      error.getDetail(),
      status,
      error.getType()
    );
  }

  @Test
  void givenWrongExceptionWhenWebClientResponseExceptionResolverIsCalledThenThrowClassCastException() {
    callToErrorResolverAndExpectClassCastException(new Exception(EXCEPTION_MESSAGE));
  }
}
