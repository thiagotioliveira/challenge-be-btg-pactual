package dev.thiagooliveira.order.messaging.listeners;

import static dev.thiagooliveira.order.core.messaging.QueueLabel.ORDER_CREATED_QUEUE;

import dev.thiagooliveira.order.core.messaging.dto.OrderCreatedEvent;
import dev.thiagooliveira.order.core.services.OrderService;
import dev.thiagooliveira.order.messaging.mappers.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderCreatedListener {

    private final OrderMapper mapper;
    private final OrderService service;

    @RabbitListener(queues = ORDER_CREATED_QUEUE)
    public void listen(Message<OrderCreatedEvent> message) {
        log.info("Message consumend: {}", message);
        service.save(mapper.toModel(message.getPayload()));
    }
}
