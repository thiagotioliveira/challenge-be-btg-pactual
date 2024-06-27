package dev.thiagooliveira.order.data.repositories;

import dev.thiagooliveira.order.data.entities.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderMongoRepository extends MongoRepository<OrderEntity, Integer> {

    Page<OrderEntity> findAllByCustomerId(Integer customerId, PageRequest pageRequest);
}
