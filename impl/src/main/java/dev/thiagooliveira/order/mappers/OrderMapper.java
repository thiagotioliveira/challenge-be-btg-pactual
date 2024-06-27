package dev.thiagooliveira.order.mappers;

import dev.thiagooliveira.order.spec.dto.GetOrdersByCustomerPage;
import org.mapstruct.Mapper;

@Mapper(implementationName = "OrderApiMapper")
public interface OrderMapper {

    GetOrdersByCustomerPage toGetOrdersByCustomerPage(
            org.springframework.data.domain.Page<dev.thiagooliveira.order.core.models.Order> page);
}
