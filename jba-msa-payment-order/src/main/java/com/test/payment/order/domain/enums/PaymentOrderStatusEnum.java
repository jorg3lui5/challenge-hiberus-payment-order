package com.test.payment.order.domain.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum PaymentOrderStatusEnum {
    INITIATED("INITIATED"),
    VALIDATED("VALIDATED"),
    PENDINGEXECUTION("PENDINGEXECUTION"),
    EXECUTED("EXECUTED"),
    REJECTED("REJECTED"),
    CANCELLED("CANCELLED");

    final String value;
}
