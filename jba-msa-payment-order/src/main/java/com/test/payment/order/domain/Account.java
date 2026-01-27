package com.test.payment.order.domain;

import com.test.payment.order.infrastructure.input.adapter.rest.bs.bean.InstructedAmount;
import com.test.payment.order.infrastructure.input.adapter.rest.bs.bean.PaymentOrderStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Account {
    String iban;
}
