package plazoleta.domain.spi;

import plazoleta.domain.model.plate.Plate;

import java.util.List;

public interface IPlatePersistencePort {
    Plate save (Plate plate);
    Plate update (Plate plate, int id);
    List<Plate> get(int page, int size, int category, int restaurant);
}
