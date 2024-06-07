package plazoleta.adapters.driving.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlateDto {
    private int id;
    private String name;
    private int categoryId;
    private String description;
    private int price;
    private int restaurantId;
    private String imageUrl;
    private boolean active = true;
}
