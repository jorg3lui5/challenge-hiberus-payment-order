package com.test.payment.order.infrastructure.input.adapter.rest.mapper;

import com.test.payment.order.domain.PaymentOrder;
import com.test.payment.order.infrastructure.input.adapter.rest.bs.bean.GetPaymentOrderByIdResponse;
import com.test.payment.order.infrastructure.input.adapter.rest.bs.bean.GetStatusPaymentOrderByIdResponse;
import com.test.payment.order.infrastructure.input.adapter.rest.bs.bean.PostInitiatePaymentOrderRequest;
import com.test.payment.order.infrastructure.input.adapter.rest.bs.bean.PostInitiatePaymentOrderResponse;
import org.mapstruct.*;

@Mapper(
  componentModel = "spring",
  nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
  builder = @Builder(disableBuilder = true)
)
public interface PaymentOrderMapper {

  @Mapping(target = "paymentOrderId", ignore = true)
  @Mapping(target = "externalReference", source = "externalReference")
  @Mapping(target = "debtorAccount.iban", source = "debtorAccount.iban")
  @Mapping(target = "creditorAccount.iban", source = "creditorAccount.iban")
  @Mapping(target = "instructedAmount.amount", source = "instructedAmount.amount")
  @Mapping(target = "instructedAmount.currency", source = "instructedAmount.currency")
  @Mapping(target = "remittanceInformation", source = "remittanceInformation")
  @Mapping(target = "requestedExecutionDate", source = "requestedExecutionDate")
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "lastUpdate", ignore = true)
  PaymentOrder toPaymentOrder(PostInitiatePaymentOrderRequest source);

  @Mapping(target = "paymentOrderId", source = "paymentOrderId")
  @Mapping(target = "status", source = "status")
  PostInitiatePaymentOrderResponse toPostInitiatePaymentOrderResponse(PaymentOrder source);

  @Mapping(target = "paymentOrderId", source = "paymentOrderId")
  @Mapping(target = "externalReference", source = "externalReference")
  @Mapping(target = "debtorAccount.iban", source = "debtorAccount.iban")
  @Mapping(target = "creditorAccount.iban", source = "creditorAccount.iban")
  @Mapping(target = "instructedAmount.amount", source = "instructedAmount.amount")
  @Mapping(target = "instructedAmount.currency", source = "instructedAmount.currency")
  @Mapping(target = "remittanceInformation", source = "remittanceInformation")
  @Mapping(target = "requestedExecutionDate", source = "requestedExecutionDate")
  @Mapping(target = "status", source = "status")
  GetPaymentOrderByIdResponse toGetPaymentOrderByIdResponse(PaymentOrder source);

  @Mapping(target = "paymentOrderId", source = "paymentOrderId")
  @Mapping(target = "status", source = "status")
  @Mapping(target = "lastUpdate", source = "lastUpdate")
  GetStatusPaymentOrderByIdResponse toGetStatusPaymentOrderByIdResponse(PaymentOrder source);

}
