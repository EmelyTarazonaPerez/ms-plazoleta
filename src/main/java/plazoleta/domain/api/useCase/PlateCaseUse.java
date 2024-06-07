package plazoleta.domain.api.useCase;

import plazoleta.adapters.driven.jpa.msql.entity.RestaurantEntity;
import plazoleta.adapters.driven.jpa.msql.exception.ErrorAccessModified;
import plazoleta.adapters.driven.jpa.msql.exception.ErrorBaseDatos;
import plazoleta.domain.api.IPlateServicePort;
import plazoleta.domain.model.plate.Plate;
import plazoleta.domain.spi.IPlatePersistencePort;
import plazoleta.domain.spi.IRestaurantPersistencePort;

import java.util.List;
import java.util.Optional;

public class PlateCaseUse implements IPlateServicePort {

    private final IPlatePersistencePort platePersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;

    public PlateCaseUse(IPlatePersistencePort platePersistencePort, IRestaurantPersistencePort restaurantPersistencePort) {
        this.platePersistencePort = platePersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
    }
    @Override
    public Plate create(Plate plate) {
        return platePersistencePort.save(plate);
    }

    @Override
    public Plate update(Plate plate, int id, int idAuthenticate) {
        if (accessModified(plate, idAuthenticate)) {
            throw new ErrorAccessModified("Solo el propietario del restaurante puede modificar");
        }
        return platePersistencePort.update(plate, id);
    }
    @Override
    public List<Plate> get(int page, int size, int category, int restaurant) {
        return platePersistencePort.get(page, size, category, restaurant);
    }

    public boolean accessModified(Plate plate, int idAuthenticated) {
        Optional<RestaurantEntity> restaurantOptional = restaurantPersistencePort.getRestaurant(plate.getRestaurantId());
        if (restaurantOptional.isPresent()) {
            RestaurantEntity restaurant = restaurantOptional.get();
            return restaurant.getOwnerId() != idAuthenticated;
        } else {
            throw new ErrorBaseDatos("El restaurante " + plate.getRestaurantId() + " no está registrado en la base de datos");
        }
    }
}
