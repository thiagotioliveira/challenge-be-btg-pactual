package dev.thiagooliveira.order.controllers;

import dev.thiagooliveira.order.spec.api.OrderApi;
import dev.thiagooliveira.order.spec.model.GetOrdersPage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Optional;

@RestController
public class OrderController implements OrderApi {
    @Override
    public ResponseEntity<GetOrdersPage> getOrders(Optional<@Min(1) Integer> page, Optional<@Min(1) @Max(100) Integer> size) {
        return ResponseEntity.ok().build();
    }
}
