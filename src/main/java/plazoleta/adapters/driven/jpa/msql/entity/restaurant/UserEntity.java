package plazoleta.adapters.driven.jpa.msql.entity.restaurant;

import jakarta.persistence.*;
import lombok.*;
import plazoleta.adapters.driven.jpa.msql.entity.restaurant.RolEntity;

import java.time.LocalDate;

@Entity
@Table(name = "persona")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class UserEntity {

    @Id
    @Column(name="id_persona")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUser;
    private String name;
    @Column(name="last_name")
    private String lastName;
    @Column(name="identification_document")
    private String identificationDocument;
    private String phone;
    @Column(name="birthdate")
    private LocalDate birthDate;
    private String gmail;
    private String password;
    @ManyToOne
    @JoinColumn(name = "id_rol")
    private RolEntity idRol;

}
