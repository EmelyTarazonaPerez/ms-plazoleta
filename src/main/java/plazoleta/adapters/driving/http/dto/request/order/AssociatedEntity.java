package plazoleta.adapters.driving.http.dto.request.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import plazoleta.domain.model.plate.Plate;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class AssociatedEntity {
    private int id;
    private Plate plate;
    private int amount;
}
