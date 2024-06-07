package plazoleta.adapters.driving.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import plazoleta.adapters.driven.jpa.msql.entity.RolEntity;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class UserDto {
    private int idUser;
    private String name;
    private String lastName;
    private String identificationDocument;
    private String phone;
    private LocalDate birthDate;
    private String gmail;
    private String password;
    private RolEntity idRol;
}
