package com.test.payment.order;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class PaymentOrderMainApplicationTest {
    @Mock
    private ConfigurableApplicationContext configurableApplicationContext;

    @Test
    void shouldReturnPaymentOrderMainApplicationWhenCreatingInstanceOfPaymentOrderMainApplication() {
        assertDoesNotThrow(PaymentOrderMainApplication::new);
    }

    @Test
    void givenApplicationArgumentsWhenRunApplicationThenApplicationExecutionIsVerified() {
        try (final var mockedSpringApplication = mockStatic(SpringApplication.class)) {
            final var applicationArguments = new String[] {};
            mockedSpringApplication.when(() -> SpringApplication.run(com.test.payment.order.PaymentOrderMainApplication.class, applicationArguments))
                    .thenReturn(configurableApplicationContext);
            PaymentOrderMainApplication.main(applicationArguments);
            mockedSpringApplication.verify(() -> SpringApplication.run(PaymentOrderMainApplication.class, applicationArguments));
        }
    }
}
