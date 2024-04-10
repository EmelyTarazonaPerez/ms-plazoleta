package plazoleta.domain.spi;


import plazoleta.domain.model.Restaurant;

public interface IRestaurantPersistencePort {
    Restaurant save (Restaurant user);
}
