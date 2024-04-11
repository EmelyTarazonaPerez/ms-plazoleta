package plazoleta.domain.api;

import plazoleta.domain.model.plate.Plate;

public interface IPlateServicePort {
    Plate create (Plate plate);
}
