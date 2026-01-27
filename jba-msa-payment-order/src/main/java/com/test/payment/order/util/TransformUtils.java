package com.test.payment.order.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;

@UtilityClass
@Slf4j
public class TransformUtils {

    @NonNull
    public Long deletePrefixPaymentOrderId(
            final String paymentOrderId
    ) {
        String idWithoutPrefix = paymentOrderId.startsWith("PO-")  ? paymentOrderId.substring(3) : paymentOrderId;
        return Long.valueOf(idWithoutPrefix);
    }

   
}
