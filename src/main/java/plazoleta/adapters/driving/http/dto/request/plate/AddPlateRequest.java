package plazoleta.adapters.driving.http.dto.request.plate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import plazoleta.domain.model.plate.Category;
import plazoleta.domain.model.restaurant.Restaurant;

@Getter
@AllArgsConstructor
public class AddPlateRequest {
    @NotNull
    private String name;
    @NotNull
    private int categoryId;
    @NotNull
    private String description;
    @NotNull
    @Min(value = 2, message="El precio no puede ser negativo ni cero")
    private int price;
    @NotNull
    private int restaurantId;
    @NotNull
    private String imageUrl;
    private boolean active;
}
