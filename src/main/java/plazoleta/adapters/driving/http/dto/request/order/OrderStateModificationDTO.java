package plazoleta.adapters.driving.http.dto.request.order;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderStateModificationDTO {
    private String pin;
    private String productId;
    private String action;
}
