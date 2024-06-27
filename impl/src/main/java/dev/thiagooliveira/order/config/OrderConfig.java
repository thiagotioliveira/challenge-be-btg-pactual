package dev.thiagooliveira.order.config;

import dev.thiagooliveira.order.core.repositories.OrderRepository;
import dev.thiagooliveira.order.core.services.OrderService;
import dev.thiagooliveira.order.core.services.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfig {

    @Bean
    public OrderService orderService(OrderRepository orderRepository) {
        return new OrderServiceImpl(orderRepository);
    }
}
