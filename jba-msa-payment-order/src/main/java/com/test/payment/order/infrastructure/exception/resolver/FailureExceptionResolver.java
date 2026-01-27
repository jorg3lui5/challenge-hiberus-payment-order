package com.test.payment.order.infrastructure.exception.resolver;

import com.test.payment.order.infrastructure.input.adapter.rest.bs.bean.ErrorModel;
import com.test.payment.order.util.ErrorUtils;
import com.test.payment.order.util.JsonSerializer;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

public class FailureExceptionResolver extends ErrorResolver<ErrorModel> {

    @Override
    protected int status() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @NonNull
    @Override
    protected ErrorModel buildError(@NonNull final String requestPath,
                                    @NonNull final Throwable throwable,
                                    @NonNull final String version) {
        final var exception = (FailureException) throwable;
        return JsonSerializer.jsonStringToObject(exception.getMessage(), ErrorModel.class);
    }
}
