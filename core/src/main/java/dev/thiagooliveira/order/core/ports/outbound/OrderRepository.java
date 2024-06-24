package dev.thiagooliveira.order.core.ports.outbound;

import dev.thiagooliveira.order.core.models.Order;

import java.util.List;

public interface OrderRepository {

    List<Order> getByCustomer(String customerId);

    int quantityByCustomer(String customerId);

    Order save(Order order);
}
