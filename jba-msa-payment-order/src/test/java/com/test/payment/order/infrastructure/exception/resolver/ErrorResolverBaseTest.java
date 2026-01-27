package com.test.payment.order.infrastructure.exception.resolver;

import com.test.payment.order.infrastructure.input.adapter.rest.config.Properties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

import static com.test.payment.order.util.MockDataUtils.URL_TEST;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@Getter(AccessLevel.PROTECTED)
@Accessors(fluent = true)
abstract class ErrorResolverBaseTest<E, R extends ErrorResolver<E>> {
  @Value("${info.project.version}")
  private String expectedVersion;
  @Mock
  private ServerWebExchange serverWebExchange;
  @Mock
  private ServerHttpResponse serverHttpResponse;
  @Autowired
  private R errorResolver;
  @Autowired
  private Properties properties;

  protected void mockServerWebExchangeRequest(final String result) {
    when(serverWebExchange.getRequest())
      .thenReturn(MockServerHttpRequest.get(result)
        .build());
  }

  protected void mockServerWebExchangeResponse() {
    when(serverWebExchange.getResponse()).thenReturn(serverHttpResponse);
  }

  protected abstract void callToErrorResolverAndExpectError(
    final Throwable thrownException,
    final String expectTitle,
    final String expectDetail,
    final int expectInstance,
    final String expectType
  );

  protected void callToErrorResolverAndExpectClassCastException(final Throwable wrongException) {
    mockServerWebExchangeRequest(URL_TEST);
    assertThat(errorResolver, notNullValue());
    assertThat(serverWebExchange, notNullValue());
    assertThat(expectedVersion, notNullValue());
    assertThrows(ClassCastException.class,
      () -> errorResolver.apply(serverWebExchange, wrongException, expectedVersion));
  }
}