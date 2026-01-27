package com.test.payment.order.infrastructure.output.adapter.repository.mapper;

import com.test.payment.order.domain.PaymentOrder;
import com.test.payment.order.infrastructure.output.adapter.repository.entity.PaymentOrderEntity;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static com.test.payment.order.util.Constants.PREFIX_PAYMENT_ORDER_ID;

@Mapper(
        componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface PaymentOrderEntityMapper {

    @Mapping(target = "paymentOrderId", source = "paymentOrderId", qualifiedByName = "buildPaymentOrderId")
    @Mapping(target = "externalReference", source = "externalReference")
    @Mapping(target = "debtorAccount.iban", source = "ibanDebtorAccount")
    @Mapping(target = "creditorAccount.iban", source = "ibanCreditorAccount")
    @Mapping(target = "instructedAmount.amount", source = "amount")
    @Mapping(target = "instructedAmount.currency", source = "currency")
    @Mapping(target = "remittanceInformation", source = "remittanceInformation")
    @Mapping(target = "requestedExecutionDate", source = "executionDate")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "lastUpdate", source = "updatedAt", qualifiedByName = "toOffsetDateTime")
    PaymentOrder entityToPaymentOrder(PaymentOrderEntity source);

    @Named("toOffsetDateTime")
    static OffsetDateTime toOffsetDateTime(LocalDateTime value) {
        return value == null ? null : value.atOffset(ZoneOffset.UTC);
    }

    @Named("buildPaymentOrderId")
    default String buildPaymentOrderId(String paymentOrderId) {
        return PREFIX_PAYMENT_ORDER_ID + paymentOrderId;
    }

    @Mapping(target = "paymentOrderId", ignore = true)
    @Mapping(target = "externalReference", source = "externalReference")
    @Mapping(target = "ibanDebtorAccount", source = "debtorAccount.iban")
    @Mapping(target = "ibanCreditorAccount", source = "creditorAccount.iban")
    @Mapping(target = "amount", source = "instructedAmount.amount")
    @Mapping(target = "currency", source = "instructedAmount.currency")
    @Mapping(target = "remittanceInformation", source = "remittanceInformation")
    @Mapping(target = "executionDate", source = "requestedExecutionDate")
    @Mapping(target = "status", constant = "INITIATED")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    PaymentOrderEntity initPaymentOrderToEntity(PaymentOrder source);
}
