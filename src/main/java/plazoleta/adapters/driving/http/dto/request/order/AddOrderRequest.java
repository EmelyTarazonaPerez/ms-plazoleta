package plazoleta.adapters.driving.http.dto.request.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import plazoleta.domain.model.plate.Plate;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddOrderRequest {
    private int userId;
    private LocalDate date;
    private String state;
    private int chefId;
    private int restaurantId;
    private List<AssociatedEntity> plates;

}
