package com.test.payment.order.util;

import com.test.payment.order.infrastructure.input.adapter.rest.bs.bean.ErrorModel;
import lombok.experimental.UtilityClass;
import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.MissingRequestValueException;

@UtilityClass
@SuppressWarnings("java:S1118")
public class InfrastructureTestUtils {
    private static final String ERROR_CODE_FIELD_NAME = "title";
    private static final String ERROR_CODE_LABEL = "Error code";

    @NonNull
    public static MissingRequestValueException getMissingRequestValueException()
            throws NoSuchMethodException {
        final var fieldName = ERROR_CODE_FIELD_NAME;
        return new MissingRequestValueException(
                fieldName,
                String.class,
                ERROR_CODE_LABEL,
                MethodParameter.forParameter(
                        ErrorModel.class.getMethod(fieldName, String.class).getParameters()[0])
        );
    }

    @NonNull
    public static WebExchangeBindException buildWebExchangeBindException(
            @NonNull final String errorMessage)
            throws NoSuchMethodException {
        final var target = ERROR_CODE_FIELD_NAME;
        final var objectName = ErrorModel.class.getName();
        final var bindException = new BindException(target, objectName);
        bindException.getBindingResult().addError(
                new FieldError(objectName, target, errorMessage)
        );
        return new WebExchangeBindException(
                MethodParameter.forParameter(
                        ErrorModel.class.getMethod(target, String.class).getParameters()[0]),
                bindException
        );
    }

}
