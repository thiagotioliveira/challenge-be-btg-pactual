package dev.thiagooliveira.order.data.repositories;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import dev.thiagooliveira.order.core.models.Order;
import dev.thiagooliveira.order.core.models.OrderItem;
import dev.thiagooliveira.order.core.models.Page;
import dev.thiagooliveira.order.data.entities.OrderEntity;
import dev.thiagooliveira.order.data.mappers.OrderEntityMapper;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class OrderMongoRepositoryAdapterTest {

    private static final int CUSTOMER_ID = 1;
    private static final String PRODUCT_NAME = "product-mock-name";

    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final int DEFAULT_PAGE_SIZE = 10;

    @Mock
    private OrderMongoRepository orderRepository;

    private OrderMongoRepositoryAdapter orderMongoRepositoryAdapter;

    @BeforeEach
    void setUp() {
        this.orderMongoRepositoryAdapter = new OrderMongoRepositoryAdapter(new OrderEntityMapper(), orderRepository);
    }

    @Test
    void shouldSave() {
        when(orderRepository.save(any(OrderEntity.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());

        var orderItem = new OrderItem();
        orderItem.setPrice(BigDecimal.TEN);
        orderItem.setQuantity(1);
        orderItem.setProduct(PRODUCT_NAME);

        var order = new Order();
        order.setCustomerId(CUSTOMER_ID);
        order.setItems(List.of(orderItem));
        order.setTotal(BigDecimal.TEN);
        order.setId(1);

        Order orderSaved = orderMongoRepositoryAdapter.save(order);
        assertEquals(order.getId(), orderSaved.getId());
        assertEquals(order.getCustomerId(), orderSaved.getCustomerId());
        assertEquals(order.getTotal(), orderSaved.getTotal());
        assertEquals(order.getItems().size(), orderSaved.getItems().size());

        orderSaved.getItems().forEach(i -> {
            assertEquals(orderItem.getPrice(), i.getPrice());
            assertEquals(orderItem.getQuantity(), i.getQuantity());
            assertEquals(orderItem.getProduct(), i.getProduct());
        });
    }

    @Test
    void shouldFindAllByCustomer() {
        var orderItem = new dev.thiagooliveira.order.data.entities.OrderItem();
        orderItem.setPrice(BigDecimal.TEN);
        orderItem.setQuantity(1);
        orderItem.setProduct(PRODUCT_NAME);

        var order = new OrderEntity();
        order.setCustomerId(CUSTOMER_ID);
        order.setItems(List.of(orderItem));
        order.setTotal(BigDecimal.TEN);
        order.setId(1);

        var page = new PageImpl<OrderEntity>(List.of(order), PageRequest.of(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE), 1);
        when(orderRepository.findAllByCustomerId(
                        eq(CUSTOMER_ID), any(org.springframework.data.domain.PageRequest.class)))
                .thenReturn(page);

        Page<Order> pageOrder = orderMongoRepositoryAdapter.findAllByCustomer(
                CUSTOMER_ID,
                new dev.thiagooliveira.order.core.models.PageRequest(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE));

        assertEquals(DEFAULT_PAGE_SIZE, pageOrder.getPageSize());
        assertEquals(1, pageOrder.getTotalPages());
        assertEquals(DEFAULT_PAGE_NUMBER, pageOrder.getPageNumber());
        assertEquals(1, pageOrder.getTotalElements());
        pageOrder.getContent().forEach(o -> {
            assertEquals(order.getId(), o.getId());
            assertEquals(order.getCustomerId(), o.getCustomerId());
            assertEquals(order.getTotal(), o.getTotal());
            assertEquals(order.getItems().size(), o.getItems().size());
        });
    }
}
