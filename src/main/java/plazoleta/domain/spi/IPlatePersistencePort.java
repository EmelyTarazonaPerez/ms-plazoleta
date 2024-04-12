package plazoleta.domain.spi;

import plazoleta.domain.model.plate.Plate;

public interface IPlatePersistencePort {
    Plate save (Plate plate);
    Plate update (Plate plate, int id, int idAutenticado);
}
