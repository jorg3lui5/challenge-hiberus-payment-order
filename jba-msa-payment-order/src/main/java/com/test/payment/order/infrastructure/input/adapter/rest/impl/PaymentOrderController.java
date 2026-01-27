package com.test.payment.order.infrastructure.input.adapter.rest.impl;

import com.test.payment.order.application.input.port.PaymentOrderService;
import com.test.payment.order.infrastructure.input.adapter.rest.bs.PaymentOrdersApi;
import com.test.payment.order.infrastructure.input.adapter.rest.bs.bean.GetPaymentOrderByIdResponse;
import com.test.payment.order.infrastructure.input.adapter.rest.bs.bean.GetStatusPaymentOrderByIdResponse;
import com.test.payment.order.infrastructure.input.adapter.rest.bs.bean.PostInitiatePaymentOrderRequest;
import com.test.payment.order.infrastructure.input.adapter.rest.bs.bean.PostInitiatePaymentOrderResponse;
import com.test.payment.order.infrastructure.input.adapter.rest.mapper.PaymentOrderMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentOrderController implements PaymentOrdersApi {

    PaymentOrderService paymentOrderService;
    PaymentOrderMapper paymentOrderMapper;

    @Override
    public Mono<ResponseEntity<GetStatusPaymentOrderByIdResponse>> getStatusPaymentOrderById(
            String paymentOrderId,
            final ServerWebExchange exchange
    ) {
        log.info("|--> PaymentOrderController - getStatusPaymentOrderById init with paymentOrderId: {}",paymentOrderId);
        return paymentOrderService.getOrderStatusById(paymentOrderId)
                .map(paymentOrderMapper::toGetStatusPaymentOrderByIdResponse)
                .map(response -> ResponseEntity.ok().body(response))
                .doOnSuccess(response -> log
                        .info("<--| PaymentOrderController - getStatusPaymentOrderById finished successfully"))
                .doOnError(error -> log.error(
                        "<--| PaymentOrderController - getStatusPaymentOrderById finished with error: {}",
                        error.getMessage()));
    }

    @Override
    public Mono<ResponseEntity<GetPaymentOrderByIdResponse>> getPaymentOrderById(
            String paymentOrderId,
            final ServerWebExchange exchange
    ) {
        log.info("|--> PaymentOrderController - getPaymentOrderById init with paymentOrderId: {}",paymentOrderId);
        return paymentOrderService.getPaymentOrderById(paymentOrderId)
                .map(paymentOrderMapper::toGetPaymentOrderByIdResponse)
                .map(response -> ResponseEntity.ok().body(response))
                .doOnSuccess(response -> log
                        .info("<--| PaymentOrderController - getPaymentOrderById finished successfully"))
                .doOnError(error -> log.error(
                        "<--| PaymentOrderController - getPaymentOrderById finished with error: {}",
                        error.getMessage()));
    }

    @Override
    public Mono<ResponseEntity<PostInitiatePaymentOrderResponse>> postInitiatePaymentOrder(
            Mono<PostInitiatePaymentOrderRequest> postInitiatePaymentOrderRequest,
            final ServerWebExchange exchange
    ) {
        log.info("|--> PaymentOrderController - postInitiatePaymentOrder init");
        return postInitiatePaymentOrderRequest.flatMap(request -> {
                    log.debug(
                            "|--> PaymentOrderController - postInitiatePaymentOrder Request in Json: [request:{}]",
                            request);
                    return paymentOrderService.initiatePaymentOrder(paymentOrderMapper.toPaymentOrder(request));
                })
                .map(paymentOrderMapper::toPostInitiatePaymentOrderResponse)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response))
                .doOnSuccess(response -> log.info(
                        "<--| PaymentOrderController - postInitiatePaymentOrder finished successfully"))
                .doOnError(error -> log.error(
                        "<--| PaymentOrderController - postInitiatePaymentOrder finished with error: {}",
                        error.getMessage()));
    }
}
