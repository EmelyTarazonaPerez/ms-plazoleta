package plazoleta.adapters.driving.http.dto.request.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStateModificationDTO {
    private String pin;
    private String productId;
    private String action;
}
