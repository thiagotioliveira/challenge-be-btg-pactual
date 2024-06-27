package dev.thiagooliveira.order.core.services;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import dev.thiagooliveira.order.core.models.Order;
import dev.thiagooliveira.order.core.models.Page;
import dev.thiagooliveira.order.core.models.PageRequest;
import dev.thiagooliveira.order.core.repositories.OrderRepository;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public Page<Order> findAllByCustomer(Integer customerId, PageRequest pageRequest) {
        return orderRepository.findAllByCustomer(customerId, pageRequest);
    }

    @Override
    public Order save(Order order) {
        checkArgument(order.getId() > 0, "id should be a valid value.");
        checkArgument(order.getCustomerId() > 0, "customerId should be a valid value.");
        checkNotNull(order.getItems(), "items should be valid value.");
        checkArgument(Boolean.FALSE.equals(order.getItems().isEmpty()), "items should be not empty.");

        order.getItems().forEach(o -> {
            checkNotNull(o.getProduct(), "product name should be valid name.");
            checkNotNull(o.getPrice(), "price cannot be null");
            checkArgument(o.getPrice().compareTo(BigDecimal.ZERO) > 0, "price must be greater than 0");
            checkArgument(o.getQuantity() > 0, "quantity must be greater than 0");
        });

        order.setTotal(order.getItems().stream()
                .map(o -> o.getPrice().multiply(BigDecimal.valueOf(o.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        return orderRepository.save(order);
    }
}
