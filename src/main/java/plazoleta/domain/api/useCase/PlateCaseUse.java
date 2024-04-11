package plazoleta.domain.api.useCase;

import plazoleta.domain.api.IPlateServicePort;
import plazoleta.domain.model.plate.Plate;
import plazoleta.domain.spi.IPlatePersistencePort;

public class PlateCaseUse implements IPlateServicePort {

    private final IPlatePersistencePort platePersistencePort;

    public PlateCaseUse(IPlatePersistencePort platePersistencePort) {
        this.platePersistencePort = platePersistencePort;
    }

    @Override
    public Plate create(Plate plate) {
        return platePersistencePort.save(plate);
    }
}
