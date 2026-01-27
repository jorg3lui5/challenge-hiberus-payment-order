package com.test.payment.order.infrastructure.exception.resolver;

import com.test.payment.order.infrastructure.input.adapter.rest.bs.bean.ErrorList;
import com.test.payment.order.infrastructure.input.adapter.rest.bs.bean.ErrorModel;
import com.test.payment.order.util.ErrorUtils;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import java.util.List;

import static com.test.payment.order.util.Constants.BAD_INPUT;

public class IllegalArgumentExceptionResolver extends ErrorResolver<ErrorModel> {

    @Override
    protected int status() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @NonNull
    private List<ErrorList> getErrors(@NonNull final IllegalArgumentException exception) {
        return List.of(
                new ErrorList()
                        .message("Bad Request")
                        .businessMessage(exception.getMessage())
        );
    }

    @NonNull
    @Override
    protected ErrorModel buildError(@NonNull final String requestPath,
                                    @NonNull final Throwable throwable,
                                    @NonNull final String version) {
        final var exception = (IllegalArgumentException) throwable;
        return new ErrorModel()
                .title(BAD_INPUT)
                .detail("The input data is invalid")
                .errors(getErrors(exception))
                .instance(ErrorUtils.buildErrorCode(status()))
                .type(requestPath);
    }
}
