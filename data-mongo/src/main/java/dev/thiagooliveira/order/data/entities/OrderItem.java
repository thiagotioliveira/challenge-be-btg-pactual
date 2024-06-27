package dev.thiagooliveira.order.data.entities;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderItem {

    private String product;
    private Integer quantity;
    private BigDecimal price;
}
