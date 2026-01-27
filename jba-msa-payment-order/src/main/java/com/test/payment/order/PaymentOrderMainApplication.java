package com.test.payment.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.test.payment.order",
        "com.test.common"}
)
@Slf4j
public class PaymentOrderMainApplication {

  public static void main(String[] args) {
        SpringApplication.run(com.test.payment.order.PaymentOrderMainApplication.class, args);
    }

}