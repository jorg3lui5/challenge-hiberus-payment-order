package com.test.payment.order.infrastructure.exception.resolver;

import com.test.payment.order.infrastructure.input.adapter.rest.config.Properties;
import com.test.payment.order.util.DummyConstraintViolation;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashSet;
import java.util.Set;

import static com.test.payment.order.util.Constants.BAD_INPUT;
import static com.test.payment.order.util.MockDataUtils.URL_TEST;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {ConstraintViolationExceptionResolver.class})
@ImportAutoConfiguration({
  Properties.class
})
@SuppressWarnings("java:S2699")
class ConstraintViolationExceptionResolverTest
  extends ErrorBadRequestResolverTest<ConstraintViolationExceptionResolver> {

  private static final String EXCEPTION_MESSAGE = "Constraint violation test exception";

  @Test
  void givenConstraintViolationExceptionWhenConstraintViolationExceptionResolverIsCalledThenReturnError() {
    Set<ConstraintViolation<?>> violations = new HashSet<>();
    violations.add(createConstraintViolation("firstName", "must not be null"));
    violations.add(createConstraintViolation("age", "must be greater than or equal to 18"));
    final var exception = new ConstraintViolationException(EXCEPTION_MESSAGE, violations);
    callToErrorResolverAndExpectError(
      exception,
      BAD_INPUT,
      "The input data is invalid",
      HttpStatus.BAD_REQUEST.value(),
            URL_TEST
    );
  }

  @Test
  void givenWrongExceptionWhenConstraintViolationExceptionResolverIsCalledThenThrowClassCastException() {
    callToErrorResolverAndExpectClassCastException(new Exception(EXCEPTION_MESSAGE));
  }

  private static ConstraintViolation<?> createConstraintViolation(String propertyPath, String message) {
    return new DummyConstraintViolation(propertyPath, message);
  }
}