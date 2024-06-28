package dev.thiagooliveira.order;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import dev.thiagooliveira.order.core.messaging.Publisher;
import dev.thiagooliveira.order.core.messaging.QueueLabel;
import dev.thiagooliveira.order.core.messaging.dto.OrderCreatedEvent;
import dev.thiagooliveira.order.core.messaging.dto.OrderItemEvent;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@AutoConfigureMockMvc
class OrderServiceApplicationTests {

    private static final int CUSTOMER_ID = 1;
    private static final String PRODUCT_NAME = "product-mock-name";

    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final int DEFAULT_PAGE_SIZE = 10;

    @Autowired
    private Publisher publisher;

    @Autowired
    private MockMvc mockMvc;

    private CountDownLatch latch = new CountDownLatch(1);

    @Test
    void shouldGetOrdersByCustomer() throws Exception {
        OrderItemEvent itemEvent = new OrderItemEvent();
        itemEvent.setProduct(PRODUCT_NAME);
        itemEvent.setQuantity(1);
        itemEvent.setPrice(BigDecimal.TEN);

        OrderCreatedEvent event = new OrderCreatedEvent();
        event.setCustomerId(CUSTOMER_ID);
        event.setItems(List.of(itemEvent));
        event.setId(1);

        publisher.send(QueueLabel.ORDER_CREATED_QUEUE, event);

        latch.await(5l, TimeUnit.SECONDS);

        mockMvc.perform(get("/v1/customers/{customerId}/orders", CUSTOMER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.number").value(DEFAULT_PAGE_NUMBER))
                .andExpect(jsonPath("$.size").value(DEFAULT_PAGE_SIZE))
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].customerId").value(CUSTOMER_ID))
                .andExpect(jsonPath("$.content[0].total").value(DEFAULT_PAGE_SIZE))
                .andExpect(jsonPath("$.content[0].items[0].product").value(PRODUCT_NAME))
                .andExpect(jsonPath("$.content[0].items[0].quantity").value(1))
                .andExpect(jsonPath("$.content[0].items[0].price").value(10))
                .andExpect(jsonPath("$.pageable.sort.sorted").value(false))
                .andExpect(jsonPath("$.pageable.sort.unsorted").value(true))
                .andExpect(jsonPath("$.pageable.sort.empty").value(true))
                .andExpect(jsonPath("$.pageable.offset").value(0))
                .andExpect(jsonPath("$.pageable.pageNumber").value(DEFAULT_PAGE_NUMBER))
                .andExpect(jsonPath("$.pageable.pageSize").value(DEFAULT_PAGE_SIZE))
                .andExpect(jsonPath("$.pageable.unpaged").value(false))
                .andExpect(jsonPath("$.pageable.paged").value(true))
                .andExpect(jsonPath("$.sort.sorted").value(false))
                .andExpect(jsonPath("$.sort.unsorted").value(true))
                .andExpect(jsonPath("$.sort.empty").value(true))
                .andExpect(jsonPath("$.last").value(true))
                .andExpect(jsonPath("$.first").value(true))
                .andExpect(jsonPath("$.numberOfElements").value(1));
    }
}
