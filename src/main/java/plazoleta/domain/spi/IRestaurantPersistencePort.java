package plazoleta.domain.spi;


import plazoleta.domain.model.restaurant.Restaurant;

public interface IRestaurantPersistencePort {
    Restaurant save (Restaurant user);
}
