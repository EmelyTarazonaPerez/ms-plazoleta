package plazoleta.domain.api;


import plazoleta.adapters.driving.http.dto.request.AddRestaurantRequest;
import plazoleta.domain.model.restaurant.Restaurant;

import java.util.List;

public interface IRestaurantServicePort {
    Restaurant createRestaurant(Restaurant restaurant);
    List<Restaurant> getAll(int page, int size, boolean sort);
}
