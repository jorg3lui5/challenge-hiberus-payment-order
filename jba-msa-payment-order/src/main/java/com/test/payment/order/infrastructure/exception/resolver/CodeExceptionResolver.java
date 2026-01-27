package com.test.payment.order.infrastructure.exception.resolver;

import com.test.payment.order.infrastructure.input.adapter.rest.bs.bean.ErrorModel;
import com.test.payment.order.util.ErrorUtils;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

import static com.test.payment.order.util.Constants.UNEXPECTED_ERROR;


public class CodeExceptionResolver extends ErrorResolver<ErrorModel> {

    @Override
    protected int status() {
        return HttpStatus.NOT_FOUND.value();
    }

    @NonNull
    @Override
    protected ErrorModel buildError(@NonNull final String requestPath, @NonNull final Throwable throwable, @NonNull final String version) {
        final var exception = (CodeException) throwable;
        return new ErrorModel()
                .title(UNEXPECTED_ERROR)
                .detail(exception.getMessage())
                .instance(ErrorUtils.buildErrorCode(exception.getCode()))
                .type(requestPath);
    }
}
