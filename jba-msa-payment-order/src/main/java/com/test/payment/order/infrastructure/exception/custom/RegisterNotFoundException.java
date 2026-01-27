package com.test.payment.order.infrastructure.exception.custom;

import com.test.payment.order.infrastructure.exception.resolver.FailureException;
import com.test.payment.order.infrastructure.input.adapter.rest.bs.bean.ErrorModel;
import org.springframework.http.HttpStatus;

import static com.test.payment.order.util.Constants.CONFLICT;

public class RegisterNotFoundException extends FailureException {
    public RegisterNotFoundException() {
        super(new ErrorModel()
                    .type(CONFLICT)
                    .title("Registro no encontrado")
                    .detail("El registro a recuperar no existe")
                , HttpStatus.CONFLICT.value()
        );
    }
}
