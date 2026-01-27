package com.test.payment.order.infrastructure.input.adapter.rest.impl;


import com.test.payment.order.application.input.port.PaymentOrderService;
import com.test.payment.order.infrastructure.exception.ErrorResolverHandler;
import com.test.payment.order.infrastructure.input.adapter.rest.bs.bean.*;
import com.test.payment.order.infrastructure.input.adapter.rest.mapper.PaymentOrderMapper;
import com.test.payment.order.infrastructure.input.adapter.rest.mapper.PaymentOrderMapperImpl;
import com.test.payment.order.util.MockDataUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import static com.test.payment.order.util.MockDataUtils.PAYMENT_ORDER_ID_STRING;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest(PaymentOrderController.class)
@ImportAutoConfiguration({
        ErrorResolverHandler.class,
        PaymentOrderMapperImpl.class,
})
class PaymentOrderControllerTest {

    @MockitoSpyBean
    private PaymentOrderMapper orderMapper;
    @MockitoBean
    private PaymentOrderService paymentOrderService;
    @Autowired
    private WebTestClient webTestClient;

    private void getServiceByFilterThenExpectStatusAndBodyClass(
            @NonNull final String uri,
            @NonNull final HttpStatus expectedHttpStatus,
            @NonNull final Class<?> expectedBodyClass
    ) {

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path(uri).build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isEqualTo(expectedHttpStatus)
                .expectBody(expectedBodyClass);
    }

    @Test
    void getPaymentOrderByIdThenExpectResponse() {
        String uri = String.format("/payment-orders/%s", PAYMENT_ORDER_ID_STRING);
        when(paymentOrderService.getPaymentOrderById( any()))
                .thenReturn(Mono.just(MockDataUtils.getPaymentOrder()));
        getServiceByFilterThenExpectStatusAndBodyClass(
                uri,
                HttpStatus.OK,
                GetPaymentOrderByIdResponse.class
        );
    }

    @Test
    void getStatusPaymentOrderByIdThenExpectResponse() {
        String uri = String.format("/payment-orders/%s/status", PAYMENT_ORDER_ID_STRING);
        when(paymentOrderService.getOrderStatusById(any()))
                .thenReturn(Mono.just(MockDataUtils.getPaymentOrder()));
        getServiceByFilterThenExpectStatusAndBodyClass(
                uri,
                HttpStatus.OK,
                GetStatusPaymentOrderByIdResponse.class
        );
    }

    @Test
    void postInitiatePaymentOrderThenExpectResponse() {
        String uri = "/payment-orders/initiate";
        when(paymentOrderService.initiatePaymentOrder(any()))
                .thenReturn(Mono.just(MockDataUtils.getPaymentOrder()));
        webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path(uri).build())
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(MockDataUtils.getPostInitiatePaymentOrderRequest()), PostInitiatePaymentOrderRequest.class)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(PostInitiatePaymentOrderResponse.class);
    }

    @Test
    void getPaymentOrderById_whenServiceFails_thenInternalServerError() {
        String uri = String.format("/payment-orders/%s", PAYMENT_ORDER_ID_STRING);

        when(paymentOrderService.getPaymentOrderById(any()))
                .thenReturn(Mono.error(new RuntimeException("service error")));

        webTestClient.get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .is5xxServerError();
    }

    @Test
    void getStatusPaymentOrderById_whenServiceFails_thenInternalServerError() {
        String uri = String.format("/payment-orders/%s/status", PAYMENT_ORDER_ID_STRING);

        when(paymentOrderService.getOrderStatusById(any()))
                .thenReturn(Mono.error(new IllegalStateException("status error")));

        webTestClient.get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .is5xxServerError();
    }

    @Test
    void postInitiatePaymentOrder_whenServiceFails_thenInternalServerError() {
        String uri = "/payment-orders/initiate";

        when(paymentOrderService.initiatePaymentOrder(any()))
                .thenReturn(Mono.error(new RuntimeException("init error")));

        webTestClient.post()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(MockDataUtils.getPostInitiatePaymentOrderRequest())
                .exchange()
                .expectStatus()
                .is5xxServerError();
    }

    @Test
    void whenUnexpectedException_thenHandledByUnexpectedResolver() {

        when(paymentOrderService.getPaymentOrderById(any()))
                .thenReturn(Mono.error(new RuntimeException("boom")));

        webTestClient.get()
                .uri("/payment-orders/123")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is5xxServerError()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ErrorModel.class)
                .value(error -> assertTrue(
                        error.getTitle().contains("UNEXPECTED ERROR")
                ));
    }

    @Test
    void whenIllegalArgumentException_thenGetErrorObject() {

        when(paymentOrderService.getPaymentOrderById(any()))
                .thenReturn(Mono.error(new IllegalArgumentException("order not found")));


                webTestClient.get()
                        .uri("/payment-orders/123")
                        .exchange()
                        .expectStatus().isBadRequest()
                        .expectBody(ErrorModel.class)
                        .value(error -> assertTrue(
                                error.getDetail().contains("he input data is invalid")
                        ));
    }

    @Test
    void whenResponseStatusException_thenRespectStatus() {

        when(paymentOrderService.getPaymentOrderById(any()))
                .thenReturn(Mono.error(
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment order not found")
                ));

        webTestClient.get()
                .uri("/payment-orders/999")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorModel.class)
                .value(error -> assertTrue(
                        error.getDetail().contains("Payment order not found")
                ));
    }

    @Test
    void whenValidationFails_thenBindExceptionHandled() {

        webTestClient.post()
                .uri("/payment-orders/initiate")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{}") // request inválido
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorModel.class)
                .value(error -> assertTrue(
                        error.getTitle().contains("Bad Input")
                ));
    }
}