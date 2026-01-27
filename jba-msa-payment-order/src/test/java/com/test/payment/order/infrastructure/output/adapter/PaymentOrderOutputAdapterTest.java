package com.test.payment.order.infrastructure.output.adapter;

import com.test.payment.order.application.service.PaymentOrderServiceImp;
import com.test.payment.order.infrastructure.input.adapter.rest.mapper.PaymentOrderMapper;
import com.test.payment.order.infrastructure.input.adapter.rest.mapper.PaymentOrderMapperImpl;
import com.test.payment.order.infrastructure.output.adapter.repository.PaymentOrderRepository;
import com.test.payment.order.infrastructure.output.adapter.repository.mapper.PaymentOrderEntityMapper;
import com.test.payment.order.infrastructure.output.adapter.repository.mapper.PaymentOrderEntityMapperImpl;
import com.test.payment.order.util.MockDataUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {
        PaymentOrderOutputAdapter.class,
        PaymentOrderEntityMapperImpl.class
})
public class PaymentOrderOutputAdapterTest {

    @MockitoSpyBean
    private PaymentOrderEntityMapper paymentOrderEntityMapper;
    @MockitoBean
    private PaymentOrderRepository paymentOrderRepository;

    @InjectMocks
    @Autowired
    private PaymentOrderOutputAdapter paymentOrderOutputAdapter;

    @Test
    void getPaymentOrderByIdThenReturnOk () {
        when(paymentOrderRepository.findByPaymentOrderId(any()))
                .thenReturn(Mono.just(MockDataUtils.getPaymentOrderEntity()));
        StepVerifier.create(paymentOrderOutputAdapter.getPaymentOrderById(
                        MockDataUtils.PAYMENT_ORDER_ID_STRING
                ))
                .consumeNextWith(Assertions::assertNotNull)
                .verifyComplete();
        verify(paymentOrderRepository, Mockito.times(1)).findByPaymentOrderId(any());
    }

    @Test
    void initiatePaymentOrderThenReturnOk () {
        when(paymentOrderRepository.save(any()))
                .thenReturn(Mono.just(MockDataUtils.getPaymentOrderEntity()));
        StepVerifier.create(paymentOrderOutputAdapter.initiatePaymentOrder(
                        MockDataUtils.getPaymentOrder()
                ))
                .consumeNextWith(Assertions::assertNotNull)
                .verifyComplete();
        verify(paymentOrderRepository, Mockito.times(1)).save(any());
    }

//
//    @Test
//    void givenDataNotFoundWhenGetParametersByFilterThenExpectDataNotFoundException() {
//        when(parameterRepository.findProductParameterByFilter(any(),any(),any(),any(),any())).thenReturn(Flux.empty());
//        StepVerifier.create(productParameterServiceAdapter.getParametersByFilter(GENERIC_COMPANY_CONTRACT_PRODUCT_UUID, null,null,null,null))
//                .verifyError(DataNotFoundException.class);
//    }


}
