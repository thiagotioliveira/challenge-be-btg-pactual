package dev.thiagooliveira.order;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import dev.thiagooliveira.order.messaging.config.RabbitMQConfig;
import dev.thiagooliveira.order.messaging.listeners.dto.OrderCreatedEvent;
import dev.thiagooliveira.order.messaging.listeners.dto.OrderItemEvent;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
class OrderServiceApplicationTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MockMvc mockMvc;

    private CountDownLatch latch = new CountDownLatch(1);

    @Test
    void shouldGetOrdersByCustomer() throws Exception {
        OrderItemEvent itemEvent = new OrderItemEvent();
        itemEvent.setProduct("product-mock");
        itemEvent.setQuantity(1);
        itemEvent.setPrice(BigDecimal.TEN);

        OrderCreatedEvent event = new OrderCreatedEvent();
        event.setCustomerId(1);
        event.setItems(List.of(itemEvent));
        event.setId(1);

        rabbitTemplate.convertAndSend(RabbitMQConfig.ORDER_CREATED_QUEUE, event);

        latch.await(10l, TimeUnit.SECONDS);

        mockMvc.perform(get("/v1/customers/1/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.number").value(0))
                .andExpect(jsonPath("$.size").value(10));
    }
}
