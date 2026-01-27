package com.test.payment.order.infrastructure.output.adapter.repository;

import com.test.payment.order.infrastructure.output.adapter.repository.entity.PaymentOrderEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PaymentOrderRepository extends ReactiveCrudRepository<PaymentOrderEntity, Long> {

    Mono<PaymentOrderEntity> findByPaymentOrderId(Long paymentOrderId);
}
