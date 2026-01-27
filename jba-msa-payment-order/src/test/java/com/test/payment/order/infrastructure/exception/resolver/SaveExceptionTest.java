package com.test.payment.order.infrastructure.exception.resolver;

import com.test.payment.order.infrastructure.exception.custom.SaveException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SaveExceptionTest {
    @Test
    void testSaveExceptionCreatesExceptionWithCorrectParameters() {
        SaveException exception = new SaveException();

        assertEquals("Error al guardar el registro en la base de datos", exception.getMessage());
    }
}
