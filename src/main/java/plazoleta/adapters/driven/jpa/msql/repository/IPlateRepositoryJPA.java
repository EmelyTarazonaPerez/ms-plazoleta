package plazoleta.adapters.driven.jpa.msql.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import plazoleta.adapters.driven.jpa.msql.entity.plate.PlateEntity;
import plazoleta.adapters.driven.jpa.msql.entity.restaurant.RestaurantEntity;
import plazoleta.domain.model.plate.Plate;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPlateRepositoryJPA extends JpaRepository<PlateEntity, Integer> {
    List<PlateEntity> findByRestaurantId(int restaurant, Pageable pageable);
    List<PlateEntity> findByRestaurantId(int restaurant);

}
