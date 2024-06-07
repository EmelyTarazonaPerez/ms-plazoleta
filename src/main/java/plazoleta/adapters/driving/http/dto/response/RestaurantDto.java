package plazoleta.adapters.driving.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RestaurantDto {
    private int id;
    private String name;
    private String address;
    private int ownerId;
    private String phone;
    private String urlLogo;
    private String nit;
}
