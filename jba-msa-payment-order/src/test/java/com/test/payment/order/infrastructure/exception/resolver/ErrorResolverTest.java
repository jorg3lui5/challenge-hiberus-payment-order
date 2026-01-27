package com.test.payment.order.infrastructure.exception.resolver;

import com.test.payment.order.infrastructure.input.adapter.rest.bs.bean.ErrorModel;
import com.test.payment.order.infrastructure.input.adapter.rest.config.Properties;
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
        Properties.class
})

abstract class ErrorResolverTest<R extends ErrorResolver<ErrorModel>>
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
        final var error = errorResolver().apply(serverWebExchange(), thrownException, expectedVersion());
        assertThat(error, notNullValue());
        assertThat(error.getTitle(), is(expectTitle));
        assertThat(error.getDetail(), is(expectDetail));
        assertThat(error.getInstance(), is(ErrorUtils.buildErrorCode(expectInstance)));
        assertThat(error.getType(), is(expectType));
    }
}
