package dev.thiagooliveira.order.core.messaging;

public interface Publisher {

    void send(String routingKey, Object object);
}
