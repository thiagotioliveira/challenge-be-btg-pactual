package dev.thiagooliveira.order.core.models;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class OrderItem {

    private String product;
    private Integer quantity;
    private BigDecimal price;
}
