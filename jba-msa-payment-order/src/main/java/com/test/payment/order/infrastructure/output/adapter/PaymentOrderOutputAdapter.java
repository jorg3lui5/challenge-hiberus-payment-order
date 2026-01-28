package com.test.payment.order.infrastructure.output.adapter;

import com.test.payment.order.application.output.port.PaymentOrderOutputPort;
import com.test.payment.order.domain.PaymentOrder;
import com.test.payment.order.infrastructure.exception.custom.GetRegisterException;
import com.test.payment.order.infrastructure.exception.custom.RegisterNotFoundException;
import com.test.payment.order.infrastructure.exception.custom.SaveException;
import com.test.payment.order.infrastructure.output.adapter.repository.PaymentOrderRepository;
import com.test.payment.order.infrastructure.output.adapter.repository.mapper.PaymentOrderEntityMapper;
import com.test.payment.order.util.TransformUtils;
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
public class PaymentOrderOutputAdapter implements PaymentOrderOutputPort {

    PaymentOrderRepository paymentOrderRepository;
    PaymentOrderEntityMapper paymentOrderEntityMapper;

    @Override
    @NonNull
    public Mono<PaymentOrder> getPaymentOrderById(
            @NotNull @NotBlank String paymentOrderId)
    {
        log.info("Starts getting payment order with paymentOrderId: {}", paymentOrderId);
        return paymentOrderRepository.findByPaymentOrderId(TransformUtils.deletePrefixPaymentOrderId(paymentOrderId))
                .doOnError(error->log.error("Error retrieving payment order by ID, detail: {}", error.getMessage()))
                .onErrorMap(error->new GetRegisterException())
                .switchIfEmpty(Mono.error(new RegisterNotFoundException()))
                .map(paymentOrderEntityMapper::entityToPaymentOrder)
                .doOnSuccess(success->log.info("Payment order retrieved successfully."));
    }

    @Override
    @NonNull
    public Mono<PaymentOrder> initiatePaymentOrder(
            @NotNull @Valid PaymentOrder paymentOrder
    ) {
        log.info("Start saving initial payment order.");
        log.debug("Save initial payment order with Request: [{}]", paymentOrder);
        return paymentOrderRepository.save(paymentOrderEntityMapper.initPaymentOrderToEntity(paymentOrder))
                .doOnError(error->log.error("Error saving initial payment order, detail: {}", error.getMessage()))
                .onErrorMap(error->new SaveException())
                .map(paymentOrderEntityMapper::entityToPaymentOrder)
                .doOnSuccess(success->log.info("Initial Payment order saved successfully"));
    }
}
