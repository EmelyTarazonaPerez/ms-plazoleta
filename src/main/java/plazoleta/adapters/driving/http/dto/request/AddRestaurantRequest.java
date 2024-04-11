package plazoleta.adapters.driving.http.dto;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import plazoleta.domain.model.User;

@Getter
@AllArgsConstructor
public class AddRestaurantRequest {
    private String name;
    private String address;
    private User ownerId;
    @Pattern(regexp = "^[0-9]+$", message = "Solo se permiten números")
    @Size(max=13, message="telefono debe tener maximo 13 caracteres")
    private String phone;
    private String urlLogo;
    @Pattern(regexp = "^[0-9]+$", message = "Solo se permiten números")
    private String nit;
}
