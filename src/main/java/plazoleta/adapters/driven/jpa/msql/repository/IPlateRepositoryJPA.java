package plazoleta.adapters.driven.jpa.msql.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import plazoleta.adapters.driven.jpa.msql.entity.PlateEntity;

import java.util.List;

@Repository
public interface IPlateRepositoryJPA extends JpaRepository<PlateEntity, Integer> {
    List<PlateEntity> findByRestaurantId(int restaurant, Pageable pageable);
    List<PlateEntity> findByRestaurantId(int restaurant);

}
