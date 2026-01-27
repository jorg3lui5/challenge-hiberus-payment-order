package com.test.payment.order.infrastructure.exception.resolver;

import com.test.payment.order.infrastructure.exception.custom.GetRegisterException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetRegisterExceptionTest {
    @Test
    void testGetRegisterExceptionCreatesExceptionWithCorrectParameters() {
        GetRegisterException exception = new GetRegisterException();

        assertEquals("Error al recuperar el registro de la base de datos", exception.getMessage());
    }
}
