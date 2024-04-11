package plazoleta.adapters.driven.jpa.msql.entity.restaurant;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "restaurant")
public class RestaurantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="nombre")
    private String name;
    @Column(name="direccion")
    private String address;
    @ManyToOne
    @JoinColumn(name = "id_persona")
    private UserEntity ownerId;
    @Column(name="telefono")
    private String phone;
    @Column(name="url_logo")
    private String urlLogo;
    private String nit;
}
