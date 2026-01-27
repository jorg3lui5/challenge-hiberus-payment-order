package com.test.payment.order.util;

import com.test.payment.order.domain.Account;
import com.test.payment.order.domain.InstructedAmount;
import com.test.payment.order.domain.PaymentOrder;
import com.test.payment.order.domain.enums.PaymentOrderStatusEnum;
import com.test.payment.order.infrastructure.input.adapter.rest.bs.bean.*;
import com.test.payment.order.infrastructure.output.adapter.repository.entity.PaymentOrderEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class MockDataUtils {

    private MockDataUtils() {
    }

    public static final String URL_TEST = "/payment-orders";

    public static final String PAYMENT_ORDER_ID_STRING = "PO-0001";
    public static final Long PAYMENT_ORDER_ID = Long.valueOf("0001");

    public static final String DEBTOR_ACCOUNT_IBAN =  "EC12DEBTOR";

    public static final String CREDITOR_ACCOUNT_IBAN =  "EC98CREDITOR";

    public static final String EXTERNAL_REFERENCE = "EXT-1";

    public static final BigDecimal AMOUNT = new BigDecimal("150.75");

    public static final String CURRENCY = "USD";

    public static final String REMITTANCE_INFORMATION = "Factura 001-123";

    public static final LocalDate REQUESTED_EXECUTION_DATE = LocalDate.of(2026, 10, 15);
    public static final String APP_NAME = "app-name";
    public static final OffsetDateTime LAST_UPDATE_OFFSET = OffsetDateTime.parse("2024-10-15T14:30:00Z");
    public static final LocalDateTime LAST_UPDATE = LAST_UPDATE_OFFSET.toLocalDateTime();
    public static final Set<String> HTTP_REQUEST_HEADERS_TEST = Collections
            .unmodifiableSet(Set.of(APP_NAME));


    public static PaymentOrder getPaymentOrder() {
        return PaymentOrder.builder()
                .paymentOrderId(PAYMENT_ORDER_ID_STRING)
                .debtorAccount(Account.builder().iban(DEBTOR_ACCOUNT_IBAN).build())
                .creditorAccount(Account.builder().iban(CREDITOR_ACCOUNT_IBAN).build())
                .externalReference(EXTERNAL_REFERENCE)
                .instructedAmount(InstructedAmount.builder()
                        .amount(AMOUNT)
                        .currency(CURRENCY)
                        .build())
                .remittanceInformation(REMITTANCE_INFORMATION)
                .requestedExecutionDate(REQUESTED_EXECUTION_DATE)
                .status(PaymentOrderStatusEnum.INITIATED)
                .lastUpdate(LAST_UPDATE_OFFSET)
                .build();

    }

    public static PaymentOrderEntity getPaymentOrderEntity() {
        return PaymentOrderEntity.builder()
                .paymentOrderId(PAYMENT_ORDER_ID)
                .externalReference(EXTERNAL_REFERENCE)
                .ibanDebtorAccount(DEBTOR_ACCOUNT_IBAN)
                .ibanCreditorAccount(CREDITOR_ACCOUNT_IBAN)
                .amount(AMOUNT)
                .currency(CURRENCY)
                .remittanceInformation(REMITTANCE_INFORMATION)
                .executionDate(REQUESTED_EXECUTION_DATE)
                .status(PaymentOrderStatusEnum.INITIATED.value())
                .updatedAt(LAST_UPDATE)
                .build();
    }

    public static  PostInitiatePaymentOrderRequest getPostInitiatePaymentOrderRequest() {
        return new PostInitiatePaymentOrderRequest()
                .debtorAccount(new com.test.payment.order.infrastructure.input.adapter.rest.bs.bean.Account().iban(DEBTOR_ACCOUNT_IBAN))
                .creditorAccount(new com.test.payment.order.infrastructure.input.adapter.rest.bs.bean.Account().iban(CREDITOR_ACCOUNT_IBAN))
                .externalReference(EXTERNAL_REFERENCE)
                .instructedAmount(new com.test.payment.order.infrastructure.input.adapter.rest.bs.bean.InstructedAmount()
                        .amount(AMOUNT)
                        .currency(CURRENCY)
                )
                .remittanceInformation(REMITTANCE_INFORMATION)
                .requestedExecutionDate(REQUESTED_EXECUTION_DATE);
    }
    public static GetStatusPaymentOrderByIdResponse getGetStatusPaymentOrderByIdResponse() {
        return new GetStatusPaymentOrderByIdResponse()
                .paymentOrderId(PAYMENT_ORDER_ID_STRING)
                .status(PaymentOrderStatus.INITIATED)
                .lastUpdate(LAST_UPDATE_OFFSET);
    }

    public static PostInitiatePaymentOrderResponse getPostInitiatePaymentOrderResponse() {
        return new PostInitiatePaymentOrderResponse()
                .paymentOrderId(PAYMENT_ORDER_ID_STRING)
                .status(PaymentOrderStatus.INITIATED);
    }

    public static GetPaymentOrderByIdResponse getGetPaymentOrderByIdResponse() {
        return new GetPaymentOrderByIdResponse()
                .paymentOrderId(PAYMENT_ORDER_ID_STRING)
                .debtorAccount(new com.test.payment.order.infrastructure.input.adapter.rest.bs.bean.Account().iban(DEBTOR_ACCOUNT_IBAN))
                .creditorAccount(new com.test.payment.order.infrastructure.input.adapter.rest.bs.bean.Account().iban(CREDITOR_ACCOUNT_IBAN))
                .externalReference(EXTERNAL_REFERENCE)
                .instructedAmount(new com.test.payment.order.infrastructure.input.adapter.rest.bs.bean.InstructedAmount()
                        .amount(AMOUNT)
                        .currency(CURRENCY)
                )
                .remittanceInformation(REMITTANCE_INFORMATION)
                .requestedExecutionDate(REQUESTED_EXECUTION_DATE)
                .status(PaymentOrderStatus.INITIATED);
    }

}
