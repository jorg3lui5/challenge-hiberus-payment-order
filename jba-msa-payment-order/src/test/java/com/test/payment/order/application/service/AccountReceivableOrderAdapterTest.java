package com.test.payment.order.application.service;

import com.test.payment.order.application.output.port.PaymentOrderOutputPort;
import com.test.payment.order.application.service.PaymentOrderServiceImp;
import com.test.payment.order.infrastructure.input.adapter.rest.mapper.PaymentOrderMapper;
import com.test.payment.order.infrastructure.input.adapter.rest.mapper.PaymentOrderMapperImpl;
import com.test.payment.order.util.MockDataUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {
        PaymentOrderServiceImp.class,
        PaymentOrderMapperImpl.class
})
class AccountReceivableOrderAdapterTest {

    @MockitoBean
    private PaymentOrderOutputPort paymentOrderOutputPort;
    @MockitoSpyBean
    private PaymentOrderMapper paymentOrderMapper;
    @Autowired
    @InjectMocks
    private PaymentOrderServiceImp paymentOrderServiceImp;

    @Test
    void getPaymentOrderByIdThenReturnOk () {
        when(paymentOrderOutputPort.getPaymentOrderById(anyString()))
                .thenReturn(Mono.just(MockDataUtils.getPaymentOrder()));
        StepVerifier.create(paymentOrderServiceImp.getPaymentOrderById(
                        MockDataUtils.PAYMENT_ORDER_ID_STRING
                ))
                .consumeNextWith(Assertions::assertNotNull)
                .verifyComplete();
        verify(paymentOrderOutputPort, Mockito.times(1)).getPaymentOrderById(anyString());
    }

    @Test
    void getOrderStatusByIdThenReturnOk () {
        when(paymentOrderOutputPort.getPaymentOrderById(anyString()))
                .thenReturn(Mono.just(MockDataUtils.getPaymentOrder()));
        StepVerifier.create(paymentOrderServiceImp.getOrderStatusById(
                        MockDataUtils.PAYMENT_ORDER_ID_STRING
                ))
                .consumeNextWith(Assertions::assertNotNull)
                .verifyComplete();
        verify(paymentOrderOutputPort, Mockito.times(1)).getPaymentOrderById(anyString());
    }

    @Test
    void initiatePaymentOrderThenReturnOk () {
        when(paymentOrderOutputPort.initiatePaymentOrder(any()))
                .thenReturn(Mono.just(MockDataUtils.getPaymentOrder()));
        StepVerifier.create(paymentOrderServiceImp.initiatePaymentOrder(
                        MockDataUtils.getPaymentOrder()
                ))
                .consumeNextWith(Assertions::assertNotNull)
                .verifyComplete();
        verify(paymentOrderOutputPort, Mockito.times(1)).initiatePaymentOrder(any());
    }
}