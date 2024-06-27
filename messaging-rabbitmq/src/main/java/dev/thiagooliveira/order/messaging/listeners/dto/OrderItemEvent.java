package dev.thiagooliveira.order.messaging.listeners.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class OrderItemEvent {

    @JsonProperty("produto")
    private String product;

    @JsonProperty("quantidade")
    private Integer quantity;

    @JsonProperty("preco")
    private BigDecimal price;
}
