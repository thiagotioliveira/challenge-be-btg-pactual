package dev.thiagooliveira.order.core.messaging.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class OrderCreatedEvent {

    @JsonProperty("codigoPedido")
    private Integer id;

    @JsonProperty("codigoCliente")
    private Integer customerId;

    @JsonProperty("itens")
    private List<OrderItemEvent> items;
}
