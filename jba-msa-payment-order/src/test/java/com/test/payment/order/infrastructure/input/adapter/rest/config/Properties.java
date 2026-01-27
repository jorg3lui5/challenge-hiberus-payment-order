package com.test.payment.order.infrastructure.input.adapter.rest.config;

import lombok.Generated;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Generated
@Configuration
@ConfigurationProperties
public class Properties {
  @Value("${test.payment-order.orders.path}")
  private String path;

}
