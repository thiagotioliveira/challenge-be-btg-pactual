package dev.thiagooliveira.order.core.messaging;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QueueLabel {
    public static final String ORDER_CREATED_QUEUE = "order-created-queue";
}
