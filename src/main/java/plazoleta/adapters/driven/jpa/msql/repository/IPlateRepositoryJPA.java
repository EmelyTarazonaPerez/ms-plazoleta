package plazoleta.adapters.driven.jpa.msql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import plazoleta.adapters.driven.jpa.msql.entity.plate.PlateEntity;
import plazoleta.adapters.driven.jpa.msql.entity.restaurant.RestaurantEntity;

import java.util.Optional;

@Repository
public interface IPlateRepositoryJPA extends JpaRepository<PlateEntity, Integer> {
    @Query(value = "SELECT * FROM plate WHERE id_plate = :id", nativeQuery = true)
    Optional<PlateEntity> filteByID(int id);
}
