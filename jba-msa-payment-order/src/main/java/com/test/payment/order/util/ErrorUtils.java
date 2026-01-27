package com.test.payment.order.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;

@UtilityClass
@Slf4j
public class ErrorUtils {

    @NonNull
    public String buildErrorCode(final int status) {
        return String.format("BS-%d", status);
    }
   
}
