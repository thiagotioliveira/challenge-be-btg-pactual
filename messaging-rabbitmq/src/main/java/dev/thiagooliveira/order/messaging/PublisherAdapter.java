package dev.thiagooliveira.order.messaging;

import dev.thiagooliveira.order.core.messaging.Publisher;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PublisherAdapter implements Publisher {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void send(String routingKey, Object object) {
        this.rabbitTemplate.convertAndSend(routingKey, object);
    }
}
