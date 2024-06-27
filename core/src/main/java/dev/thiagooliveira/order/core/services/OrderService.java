package dev.thiagooliveira.order.core.services;

import dev.thiagooliveira.order.core.models.Order;
import dev.thiagooliveira.order.core.models.Page;
import dev.thiagooliveira.order.core.models.PageRequest;

public interface OrderService {

    Page<Order> findAllByCustomer(Integer customerId, PageRequest pageRequest);

    Order save(Order order);
}
