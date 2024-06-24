package dev.thiagooliveira.order.core.ports.inbound;

import dev.thiagooliveira.order.core.models.Order;
import dev.thiagooliveira.order.core.models.OrderItem;
import dev.thiagooliveira.order.core.ports.outbound.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    private static final String CUSTOMER_ID = "customer-1";
    private static final String PRODUCT_ID = "product-1";

    @Mock
    private OrderRepository orderRepository;

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderServiceImpl(orderRepository);
    }

    @Test
    void shouldReturnOrdersByCustomer(){
        when(orderRepository.getByCustomer(CUSTOMER_ID))
                .thenReturn(List.of(Order.create(1, 1,
                        OrderItem.create(PRODUCT_ID, BigDecimal.TEN, 1))));
        var orders = orderService.getByCustomer(CUSTOMER_ID);
        assertEquals(1, orders.size());
        Order order = orders.getFirst();
        assertEquals(1, order.getId());
        assertEquals(1, order.getCustomerId());
        assertEquals(BigDecimal.TEN, order.getTotal());
        order.getItems().forEach(i -> {
            assertEquals(PRODUCT_ID, i.getProduct());
            assertEquals(BigDecimal.TEN, i.getPrice());
            assertEquals(1, i.getQuantity());
        });
    }

    @Test
    void shouldReturnQuantityByCustomer(){
        when(orderRepository.quantityByCustomer(CUSTOMER_ID))
                .thenReturn(1);
        int quantityByCustomer = orderService.quantityByCustomer(CUSTOMER_ID);
        assertEquals(1, quantityByCustomer);
    }

    @Test
    void shouldSaveOrder(){
        when(orderRepository.save(any(Order.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());
        var order = Order.create(1, 1,
                OrderItem.create(PRODUCT_ID, BigDecimal.TEN, 1));
        var orderSaved = orderService.save(order);
        verify(orderRepository, times(1)).save(any(Order.class));
        assertEquals(order, orderSaved);
    }

    @Test
    void shouldGetErrorWhenTryToCreateInvalidOrder(){
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                Order.create(0, 1, new OrderItem[]{OrderItem.create(PRODUCT_ID, BigDecimal.TEN, 1)}));
        assertEquals("id should be a valid value.", ex.getMessage());

        ex = assertThrows(IllegalArgumentException.class, () ->
                Order.create(1, 0, new OrderItem[]{OrderItem.create(PRODUCT_ID, BigDecimal.TEN, 1)}));
        assertEquals("customerId should be a valid value.", ex.getMessage());

        ex = assertThrows(IllegalArgumentException.class, () ->
                Order.create(1, 1, new OrderItem[]{}));
        assertEquals("items should be not empty.", ex.getMessage());

        ex = assertThrows(NullPointerException.class, () ->
                Order.create(1, 1, null));
        assertEquals("items should be valid value.", ex.getMessage());
    }
}