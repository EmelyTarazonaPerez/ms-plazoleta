package plazoleta.adapters.driving.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import plazoleta.domain.model.restaurant.User;

@Getter
@AllArgsConstructor
public class RestaurantResponse {
    private String name;
    private String urlLogo;
}
