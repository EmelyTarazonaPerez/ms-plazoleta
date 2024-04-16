package plazoleta.adapters.driven.jpa.msql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import plazoleta.adapters.driven.jpa.msql.entity.restaurant.RestaurantEntity;

import java.util.Optional;

@Repository
public interface IRestaurantRepositoryJPA extends JpaRepository<RestaurantEntity, Integer> {
Optional<RestaurantEntity> findById(int id);

Optional<RestaurantEntity> findByOwnerId(int id);

}
