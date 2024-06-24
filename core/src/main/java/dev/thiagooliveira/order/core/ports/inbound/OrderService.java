package dev.thiagooliveira.order.core.ports.inbound;

import dev.thiagooliveira.order.core.models.Order;

import java.util.List;

public interface OrderService {

    List<Order> getByCustomer(String customerId);

    int quantityByCustomer(String customerId);

    Order save(Order order);
}
