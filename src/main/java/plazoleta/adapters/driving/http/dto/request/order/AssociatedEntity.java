package plazoleta.adapters.driving.http.dto.request.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import plazoleta.domain.model.pedido.Order;
import plazoleta.domain.model.plate.Plate;

import java.util.List;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class AssociatedEntity {
    private  Plate plate;
    private int amount;
}
