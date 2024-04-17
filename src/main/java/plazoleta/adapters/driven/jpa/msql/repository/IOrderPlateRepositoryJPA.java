package plazoleta.adapters.driven.jpa.msql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import plazoleta.adapters.driven.jpa.msql.entity.OrderPlateEntity;

public interface IOrderPlateRepositoryJPA extends JpaRepository<OrderPlateEntity, Integer> {

}
