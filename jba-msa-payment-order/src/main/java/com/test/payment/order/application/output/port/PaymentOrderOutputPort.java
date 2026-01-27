package com.test.payment.order.application.output.port;

import com.test.payment.order.domain.PaymentOrder;
import io.micrometer.common.lang.NonNull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Validated
public interface PaymentOrderOutputPort {

    @NonNull
    Mono<PaymentOrder> getPaymentOrderById(
            @NotNull @NotBlank String paymentOrderId
    );

    @NonNull
    Mono<PaymentOrder> initiatePaymentOrder(
            @NotNull @Valid PaymentOrder paymentOrder
    );
}
