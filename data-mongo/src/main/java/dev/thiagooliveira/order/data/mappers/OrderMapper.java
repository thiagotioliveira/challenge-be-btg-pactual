package dev.thiagooliveira.order.data.mappers;

import dev.thiagooliveira.order.core.models.Order;
import dev.thiagooliveira.order.data.entities.OrderEntity;
import org.mapstruct.Mapper;

@Mapper(implementationName = "OrderEntityMapper")
public interface OrderMapper {

    OrderEntity toEntity(Order order);

    Order toModel(OrderEntity entity);
}
