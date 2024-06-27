package dev.thiagooliveira.order.messaging.listeners;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import dev.thiagooliveira.order.core.models.Order;
import dev.thiagooliveira.order.core.services.OrderService;
import dev.thiagooliveira.order.messaging.listeners.dto.OrderCreatedEvent;
import dev.thiagooliveira.order.messaging.listeners.dto.OrderItemEvent;
import dev.thiagooliveira.order.messaging.mappers.OrderEventMapper;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.support.GenericMessage;

@ExtendWith(MockitoExtension.class)
class OrderCreatedListenerTest {

    @Mock
    private OrderService service;

    private OrderCreatedListener orderCreatedListener;

    @BeforeEach
    void setUp() {
        this.orderCreatedListener = new OrderCreatedListener(new OrderEventMapper(), service);
    }

    @Test
    void shouldListen() {
        var itemEvent = new OrderItemEvent();
        itemEvent.setPrice(BigDecimal.TEN);
        itemEvent.setQuantity(1);
        itemEvent.setProduct("product-mock");

        var event = new OrderCreatedEvent();
        event.setId(1);
        event.setCustomerId(1);
        event.setItems(List.of(itemEvent));

        orderCreatedListener.listen(new GenericMessage<>(event));
        verify(service, times(1)).save(any(Order.class));
    }
}
