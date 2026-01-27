package com.test.payment.order.infrastructure.exception.resolver;

import com.test.payment.order.infrastructure.exception.custom.RegisterNotFoundException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegisterNotFoundExceptionTest {
    @Test
    void testRegisterNotFoundExceptionCreatesExceptionWithCorrectParameters() {
        RegisterNotFoundException exception = new RegisterNotFoundException();

        assertEquals("El registro a recuperar no existe", exception.getMessage());
    }
}
