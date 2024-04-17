package plazoleta.adapters.driven.jpa.msql.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import plazoleta.adapters.driven.jpa.msql.entity.OrderEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface IOrderRepositoryJPA extends JpaRepository<OrderEntity, Integer> {
    Optional<OrderEntity> findByUserId(int id);
    List<OrderEntity> findByState (String string, Pageable pageable);
}
