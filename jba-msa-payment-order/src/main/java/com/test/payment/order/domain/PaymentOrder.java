package com.test.payment.order.domain;

import com.test.payment.order.domain.enums.PaymentOrderStatusEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentOrder {
    String paymentOrderId;
    String externalReference;
    Account debtorAccount;
    Account creditorAccount;
    InstructedAmount instructedAmount;
    String remittanceInformation;
    LocalDate requestedExecutionDate;
    PaymentOrderStatusEnum status;
    OffsetDateTime lastUpdate;
}
