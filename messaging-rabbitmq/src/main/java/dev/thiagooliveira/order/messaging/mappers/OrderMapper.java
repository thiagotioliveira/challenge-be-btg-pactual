package dev.thiagooliveira.order.messaging.mappers;

import dev.thiagooliveira.order.core.models.Order;
import dev.thiagooliveira.order.messaging.listeners.dto.OrderCreatedEvent;
import org.mapstruct.Mapper;

@Mapper(implementationName = "OrderEventMapper")
public interface OrderMapper {

    Order toModel(OrderCreatedEvent event);
}
