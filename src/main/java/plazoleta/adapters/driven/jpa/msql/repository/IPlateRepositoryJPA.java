package plazoleta.adapters.driven.jpa.msql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import plazoleta.adapters.driven.jpa.msql.entity.plate.PlateEntity;
import plazoleta.adapters.driven.jpa.msql.entity.restaurant.RestaurantEntity;

@Repository
public interface IPlateRepositoryJPA extends JpaRepository<PlateEntity, Integer> {


}
