package dev.thiagooliveira.order.core.models;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
public class Order {

    private Integer id;
    private Integer customerId;
    private BigDecimal total;
    private List<OrderItem> items;

    public List<OrderItem> getItems() {
        return this.items != null ? Collections.unmodifiableList(this.items) : null;
    }
}
