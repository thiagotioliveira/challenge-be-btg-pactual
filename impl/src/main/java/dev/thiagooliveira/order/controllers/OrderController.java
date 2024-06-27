package dev.thiagooliveira.order.controllers;

import dev.thiagooliveira.order.core.models.Order;
import dev.thiagooliveira.order.core.models.Page;
import dev.thiagooliveira.order.core.models.PageRequest;
import dev.thiagooliveira.order.core.services.OrderService;
import dev.thiagooliveira.order.mappers.OrderMapper;
import dev.thiagooliveira.order.spec.api.OrderApi;
import dev.thiagooliveira.order.spec.dto.GetOrdersByCustomerPage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController implements OrderApi {

    private final OrderMapper orderMapper;
    private final OrderService orderService;

    @Override
    public ResponseEntity<GetOrdersByCustomerPage> getOrdersByCustomer(Integer customerId, Integer page, Integer size) {
        Page<Order> pageOrder = orderService.findAllByCustomer(customerId, new PageRequest(page, size));
        PageImpl<Order> pageImplOrder = new PageImpl<>(
                pageOrder.getContent(),
                org.springframework.data.domain.PageRequest.of(page, size),
                pageOrder.getTotalElements());
        return ResponseEntity.ok(orderMapper.toGetOrdersByCustomerPage(pageImplOrder));
    }
}
