package plazoleta.domain.spi;


import plazoleta.adapters.driven.jpa.msql.entity.restaurant.RestaurantEntity;
import plazoleta.domain.model.restaurant.Restaurant;

import java.util.List;
import java.util.Optional;

public interface IRestaurantPersistencePort {
    Restaurant save(Restaurant user);
    Optional<RestaurantEntity> getRestaurant(int id);
    List<Restaurant> getAll(int page, int size, boolean sort);
}
