package plazoleta.domain.api;


import plazoleta.domain.model.restaurant.Restaurant;

public interface IRestaurantServicePort {
     Restaurant createRestaurant(Restaurant restaurant);
}
