package plazoleta.adapters.driving.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import plazoleta.domain.model.restaurant.User;

@Getter
@AllArgsConstructor
public class RestaurantResponse {
    private int id;
    private String name;
    private String address;
    private User ownerId;
    private String phone;
    private String urlLogo;
}
