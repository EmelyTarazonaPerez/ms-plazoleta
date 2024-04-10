package plazoleta.domain.api.useCase;


import plazoleta.domain.api.IRestaurantServicePort;
import plazoleta.domain.model.Restaurant;
import plazoleta.domain.spi.IRestaurantPersistencePort;

import java.time.LocalDate;

public class RestaurantCase implements IRestaurantServicePort {
    private final IRestaurantPersistencePort restaurantPersistencePort;
    public RestaurantCase(IRestaurantPersistencePort restaurantPersistencePort) {
        this.restaurantPersistencePort = restaurantPersistencePort;
    }
    @Override
    public Restaurant createRestaurant(Restaurant restaurant) {
        return restaurantPersistencePort.save(restaurant);
    }
}
