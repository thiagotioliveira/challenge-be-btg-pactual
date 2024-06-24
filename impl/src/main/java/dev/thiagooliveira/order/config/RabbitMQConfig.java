package dev.thiagooliveira.order.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String ORDER_CREATED_QUEUE = "order-create-queue";
}
