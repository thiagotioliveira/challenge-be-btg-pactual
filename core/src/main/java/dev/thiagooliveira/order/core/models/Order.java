package dev.thiagooliveira.order.core.models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Order {

    private int id;
    private int customerId;
    private BigDecimal total;
    private List<OrderItem> items;

    public static Order create(int id, int customerId, OrderItem... items){
        checkArgument(id > 0, "id should be a valid value.");
        checkArgument(customerId > 0, "customerId should be a valid value.");
        checkNotNull(items, "items should be valid value.");
        checkArgument(items.length > 0, "items should be not empty.");
        return new Order(id, customerId, Arrays.stream(items)
                .map(OrderItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add), List.of(items));
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(this.items);
    }
}
