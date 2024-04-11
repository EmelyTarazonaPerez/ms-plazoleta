package plazoleta.adapters.driving.http.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddPlateRequest {
    private String name;
    private int categoryId;
    private String description;
    private int price;
    private int restaurantId;
    private String imageUrl;
    private boolean active;
}
