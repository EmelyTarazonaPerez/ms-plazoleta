package plazoleta.domain.api.useCase;

import plazoleta.domain.api.IPlateServicePort;
import plazoleta.domain.model.Plate;
import plazoleta.domain.spi.IPlatePersistencePort;

public class IPlateCaseUse implements IPlateServicePort {

    private final IPlatePersistencePort platePersistencePort;

    public IPlateCaseUse(IPlatePersistencePort platePersistencePort) {
        this.platePersistencePort = platePersistencePort;
    }

    @Override
    public Plate create(Plate plate) {
        return null;
    }
}
