package plazoleta.adapters.driving.http.dto.request;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddRestaurantRequest {
    @NotNull
    private String name;
    @NotNull
    private String address;
    @NotNull
    private int ownerId;
    @Pattern(regexp = "^[0-9]+$", message = "Solo se permiten números")
    @Size(max=13, message="telefono debe tener maximo 13 caracteres")
    private String phone;
    @NotNull
    private String urlLogo;
    @NotNull
    @Pattern(regexp = "^[0-9]+$", message = "Solo se permiten números")
    private String nit;
}
