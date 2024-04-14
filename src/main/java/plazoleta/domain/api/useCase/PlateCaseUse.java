package plazoleta.domain.api.useCase;

import plazoleta.domain.api.IPlateServicePort;
import plazoleta.domain.model.plate.Plate;
import plazoleta.domain.spi.IPlatePersistencePort;

import java.util.List;

public class PlateCaseUse implements IPlateServicePort {

    private final IPlatePersistencePort platePersistencePort;

    public PlateCaseUse(IPlatePersistencePort platePersistencePort) {
        this.platePersistencePort = platePersistencePort;
    }
    @Override
    public Plate create(Plate plate) {
        return platePersistencePort.save(plate);
    }

    @Override
    public Plate update(Plate plate, int id, int idAuthenticate) {
        return platePersistencePort.update(plate, id, idAuthenticate);
    }
    @Override
    public List<Plate> get(int page, int size, int category, int restaurant) {
        return platePersistencePort.get(page, size, category, restaurant);
    }
}
