package plazoleta.adapters.driven.jpa.msql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import plazoleta.adapters.driven.jpa.msql.entity.order.OrderEntity;

import java.util.Optional;

@Repository
public interface IOrderRepositoryJPA extends JpaRepository<OrderEntity, Integer> {
    Optional<OrderEntity> findByUserId(int id);
}
