package plazoleta.adapters.driven.jpa.msql.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import plazoleta.adapters.driven.jpa.msql.entity.RestaurantEntity;

@Repository
public interface IRestaurantRepositoryJPA extends JpaRepository<RestaurantEntity, Integer> {


}
