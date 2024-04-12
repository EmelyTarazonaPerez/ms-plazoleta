package plazoleta.domain.api.useCase;


import plazoleta.domain.api.IRestaurantServicePort;
import plazoleta.domain.model.restaurant.Restaurant;
import plazoleta.domain.spi.IRestaurantPersistencePort;

import java.util.List;

public class RestaurantCase implements IRestaurantServicePort {
    private final IRestaurantPersistencePort restaurantPersistencePort;
    public RestaurantCase(IRestaurantPersistencePort restaurantPersistencePort) {
        this.restaurantPersistencePort = restaurantPersistencePort;
    }
    @Override
    public Restaurant createRestaurant(Restaurant restaurant) {
        return restaurantPersistencePort.save(restaurant);
    }

    @Override
    public List<Restaurant> getAll(int page, int size, boolean sort) {
        return restaurantPersistencePort.getAll(page, size, sort);
    }
}
