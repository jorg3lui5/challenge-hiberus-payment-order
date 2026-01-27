package com.test.payment.order.application.service;

import com.test.payment.order.application.input.port.PaymentOrderService;
import com.test.payment.order.application.output.port.PaymentOrderOutputPort;
import com.test.payment.order.domain.PaymentOrder;
import com.test.payment.order.infrastructure.exception.custom.*;
import io.micrometer.common.lang.NonNull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentOrderServiceImp implements PaymentOrderService {

    PaymentOrderOutputPort paymentOrderOutputPort;

    @Override
    @NonNull
    public Mono<PaymentOrder> getPaymentOrderById(
            @NotNull @NotBlank String paymentOrderId)
    {
        log.info("Start payment order recovery with paymentOrderId: {}",paymentOrderId);
        return paymentOrderOutputPort.getPaymentOrderById(paymentOrderId)
                .doOnError(error->log.error("Error retrieving payment order by identification, detail: {}", error.getMessage()))
                .doOnSuccess(success->log.info("Payment order retrieved by identification successfully"));
    }

    @Override
    @NonNull
    public Mono<PaymentOrder> getOrderStatusById(
            @NotNull @NotBlank String paymentOrderId
    ) {
        log.info("Start status payment order recovery with paymentOrderId: {}",paymentOrderId);
        return paymentOrderOutputPort.getPaymentOrderById(paymentOrderId)
                .doOnError(error->log.error("Error retrieving status payment order by ID, detail: {}", error.getMessage()))
                .doOnSuccess(success->log.info("Status payment order retrieved by Id successfully"));
    }

    @Override
    @NonNull
    public Mono<PaymentOrder> initiatePaymentOrder(
            @NotNull @Valid PaymentOrder paymentOrder
    ) {
        log.info("Starting save initial payment order");
        log.info("Request in post: [{}]", paymentOrder);
        return paymentOrderOutputPort.initiatePaymentOrder(paymentOrder)
                .doOnError(error->log.error("Error saving initial payment order, detail: {}", error.getMessage()))
                .doOnSuccess(success->log.info("Initial payment order save successfully"));
    }



}
