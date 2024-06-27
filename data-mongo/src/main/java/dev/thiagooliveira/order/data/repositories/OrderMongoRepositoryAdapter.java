package dev.thiagooliveira.order.data.repositories;

import dev.thiagooliveira.order.core.models.Order;
import dev.thiagooliveira.order.core.models.Page;
import dev.thiagooliveira.order.core.models.PageRequest;
import dev.thiagooliveira.order.core.repositories.OrderRepository;
import dev.thiagooliveira.order.data.entities.OrderEntity;
import dev.thiagooliveira.order.data.mappers.OrderMapper;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMongoRepositoryAdapter implements OrderRepository {

    private final OrderMapper orderMapper;
    private final OrderMongoRepository repository;

    @Override
    public Page<Order> findAllByCustomer(Integer customerId, PageRequest pageRequest) {
        org.springframework.data.domain.Page<OrderEntity> page = repository.findAllByCustomerId(
                customerId,
                org.springframework.data.domain.PageRequest.of(pageRequest.getPageNumber(), pageRequest.getPageSize()));

        return new Page<Order>(
                page.getContent().stream().map(orderMapper::toModel).collect(Collectors.toList()),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages());
    }

    @Override
    public Order save(Order order) {
        return orderMapper.toModel(repository.save(orderMapper.toEntity(order)));
    }
}
