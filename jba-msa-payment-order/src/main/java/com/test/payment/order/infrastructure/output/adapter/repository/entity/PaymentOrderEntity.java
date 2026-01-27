package com.test.payment.order.infrastructure.output.adapter.repository.entity;

import com.test.payment.order.domain.Account;
import com.test.payment.order.domain.InstructedAmount;
import com.test.payment.order.domain.enums.PaymentOrderStatusEnum;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Table("payment_order")
@ToString
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentOrderEntity {
    @Id
    @Column("payment_order_id")
    private Long paymentOrderId;
    @Column("external_reference")
    String externalReference;
    @Column("iban_debtor_account")
    String ibanDebtorAccount;
    @Column("iban_creditor_account")
    String ibanCreditorAccount;
    @Column("amount")
    BigDecimal amount;
    @Column("currency")
    String currency;
    @Column("remittance_information")
    String remittanceInformation;
    @Column("execution_date")
    LocalDate executionDate;
    @Column("status")
    String status;
    @Column("updated_at")
    LocalDateTime updatedAt;

}
