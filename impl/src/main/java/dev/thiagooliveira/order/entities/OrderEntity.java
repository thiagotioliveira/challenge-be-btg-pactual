package dev.thiagooliveira.order.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.math.BigDecimal;

@Document(collection = "orders")
@Getter
@Setter
@NoArgsConstructor
public class OrderEntity {

    @MongoId
    private int id;

    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal total;

    @Indexed(name = "customer_id_index")
    private int customerId;
}
