package plazoleta.adapters.driving.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;
import plazoleta.domain.model.pedido.OrderPlate;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class OrderDto {
    private int id;
    private int userId;
    private LocalDate date;
    private String state;
    private int chefId;
    private int restaurantId;
    private List<OrderPlate> listPlates;
    private String pin;
}
