package com.test.payment.order.infrastructure.exception.custom;

import com.test.payment.order.infrastructure.exception.resolver.FailureException;
import com.test.payment.order.infrastructure.input.adapter.rest.bs.bean.ErrorModel;
import org.springframework.http.HttpStatus;

import static com.test.payment.order.util.Constants.CONFLICT;

public class SaveException extends FailureException {
    public SaveException() {
        super(new ErrorModel()
                    .type(CONFLICT)
                    .title("Registro no guardado")
                    .detail("Error al guardar el registro en la base de datos")
                , HttpStatus.CONFLICT.value()
        );
    }
}
