package plazoleta.domain.api;

import org.springframework.http.HttpStatusCode;
import plazoleta.domain.model.plate.Plate;

import java.util.List;

public interface IPlateServicePort {
    Plate create (Plate plate);
    Plate update (Plate plate, int id, int idAutenticado);
    List<Plate> get(int page, int size, int category, int restaurant);
}
