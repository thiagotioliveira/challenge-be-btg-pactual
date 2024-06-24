package dev.thiagooliveira.order.core.models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class OrderItem {

    private String product;
    private int quantity;
    private BigDecimal price;

    public static OrderItem create(String productName, BigDecimal price, int quantity){
        checkNotNull(productName, "product name should be valid name.");
        checkNotNull(price, "price cannot be null");
        checkArgument(price.compareTo(BigDecimal.ZERO) > 0, "price must be greater than 0");
        checkArgument(quantity > 0, "quantity must be greater than 0");
        return new OrderItem(productName, quantity, price);
    }

}
