package dev.thiagooliveira.order.core.ports.inbound;

import dev.thiagooliveira.order.core.models.Order;
import dev.thiagooliveira.order.core.ports.outbound.OrderRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
@RequiredArgsConstructor
public class OrderServiceImpl implements  OrderService {

    private final OrderRepository orderRepository;

    @Override
    public List<Order> getByCustomer(String customerId) {
        return orderRepository.getByCustomer(customerId);
    }

    @Override
    public int quantityByCustomer(String customerId) {
        return orderRepository.quantityByCustomer(customerId);
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }
}
