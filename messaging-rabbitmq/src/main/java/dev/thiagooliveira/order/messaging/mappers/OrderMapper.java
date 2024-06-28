package dev.thiagooliveira.order.messaging.mappers;

import dev.thiagooliveira.order.core.messaging.dto.OrderCreatedEvent;
import dev.thiagooliveira.order.core.models.Order;
import org.mapstruct.Mapper;

@Mapper(implementationName = "OrderEventMapper")
public interface OrderMapper {

    Order toModel(OrderCreatedEvent event);
}
