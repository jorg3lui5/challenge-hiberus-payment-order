package com.test.payment.order.infrastructure.exception.resolver;

import com.test.payment.order.infrastructure.input.adapter.rest.bs.bean.ErrorModel;
import com.test.payment.order.infrastructure.input.adapter.rest.config.PropertiesTest;
import com.test.payment.order.util.ErrorUtils;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;

import static com.test.payment.order.util.MockDataUtils.URL_TEST;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@ExtendWith(MockitoExtension.class)
@ImportAutoConfiguration({
        PropertiesTest.class
})

abstract class ErrorBadRequestResolverTest <R extends ErrorResolver<ErrorModel>>
        extends ErrorResolverBaseTest<ErrorModel, R> {
    @Override
    protected void callToErrorResolverAndExpectError(
            final Throwable thrownException,
            final String expectTitle,
            final String expectDetail,
            final int expectInstance,
            final String expectType
    ) {
        mockServerWebExchangeRequest(URL_TEST);
        mockServerWebExchangeResponse();
        assertThat(errorResolver(), notNullValue());
        final var errorBadRequest = errorResolver().apply(serverWebExchange(), thrownException, expectedVersion());
        assertThat(errorBadRequest, notNullValue());
        assertThat(errorBadRequest.getTitle(), is(expectTitle));
        assertThat(errorBadRequest.getDetail(), is(expectDetail));
        assertThat(errorBadRequest.getInstance(), is(ErrorUtils.buildErrorCode(expectInstance)));
        assertThat(errorBadRequest.getType(), is(expectType));
    }
}
