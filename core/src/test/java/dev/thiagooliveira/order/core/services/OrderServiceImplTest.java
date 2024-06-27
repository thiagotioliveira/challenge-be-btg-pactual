package dev.thiagooliveira.order.core.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import dev.thiagooliveira.order.core.models.Order;
import dev.thiagooliveira.order.core.models.OrderItem;
import dev.thiagooliveira.order.core.models.Page;
import dev.thiagooliveira.order.core.models.PageRequest;
import dev.thiagooliveira.order.core.repositories.OrderRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    private static final int CUSTOMER_ID = 1;
    private static final String PRODUCT_NAME = "product-mock-name";

    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final int DEFAULT_PAGE_SIZE = 10;

    @Mock
    private OrderRepository orderRepository;

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderServiceImpl(orderRepository);
    }

    @Test
    void shouldReturnOrdersByCustomer() {
        var orderItem = new OrderItem();
        orderItem.setPrice(BigDecimal.TEN);
        orderItem.setQuantity(1);
        orderItem.setProduct(PRODUCT_NAME);

        var order = new Order();
        order.setCustomerId(CUSTOMER_ID);
        order.setItems(List.of(orderItem));
        order.setId(1);

        PageRequest pageRequest = new PageRequest(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE);
        when(orderRepository.findAllByCustomer(CUSTOMER_ID, pageRequest))
                .thenReturn(new Page<Order>(List.of(order), DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE, 1, 1));
        var page = orderService.findAllByCustomer(CUSTOMER_ID, pageRequest);

        assertEquals(DEFAULT_PAGE_NUMBER, page.getPageNumber());
        assertEquals(1, page.getTotalPages());
        assertEquals(DEFAULT_PAGE_SIZE, page.getPageSize());
        assertEquals(1, page.getTotalElements());

        List<Order> orders = page.getContent();
        assertEquals(1, orders.size());
        orders.get(0).getItems().forEach(o -> {
            assertEquals(orderItem.getProduct(), o.getProduct());
            assertEquals(orderItem.getPrice(), o.getPrice());
            assertEquals(orderItem.getQuantity(), o.getQuantity());
        });
    }

    @Test
    void shouldSaveOrder() {
        when(orderRepository.save(any(Order.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());

        var orderItem = new OrderItem();
        orderItem.setPrice(BigDecimal.TEN);
        orderItem.setQuantity(1);
        orderItem.setProduct(PRODUCT_NAME);

        var order = new Order();
        order.setCustomerId(CUSTOMER_ID);
        order.setItems(List.of(orderItem));
        order.setId(1);

        var orderSaved = orderService.save(order);
        verify(orderRepository, times(1)).save(any(Order.class));
        assertEquals(order, orderSaved);
    }

    @Test
    void shouldGetErrorWhenTryToSaveInvalidOrder() {
        var orderItem = new OrderItem();
        orderItem.setPrice(BigDecimal.TEN);
        orderItem.setQuantity(1);
        orderItem.setProduct(PRODUCT_NAME);

        var order = new Order();
        order.setCustomerId(CUSTOMER_ID);
        order.setItems(List.of(orderItem));
        order.setId(1);

        order.setId(0);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> orderService.save(order));
        assertEquals("id should be a valid value.", ex.getMessage());
        order.setId(1);
        order.setCustomerId(0);
        ex = assertThrows(IllegalArgumentException.class, () -> orderService.save(order));
        assertEquals("customerId should be a valid value.", ex.getMessage());
        order.setCustomerId(1);
        order.setItems(new ArrayList<>());
        ex = assertThrows(IllegalArgumentException.class, () -> orderService.save(order));
        assertEquals("items should be not empty.", ex.getMessage());
        order.setItems(null);
        ex = assertThrows(NullPointerException.class, () -> orderService.save(order));
        assertEquals("items should be valid value.", ex.getMessage());
    }
}
