package plazoleta.adapters.driven.jpa.msql.entity.restaurant;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "restaurant")
public class RestaurantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="nombre")
    private String name;
    @Column(name="direccion")
    private String address;
    @Column (name = "id_usuario")
    private int ownerId;
    @Column(name="telefono")
    private String phone;
    @Column(name="url_logo")
    private String urlLogo;
    private String nit;
}
