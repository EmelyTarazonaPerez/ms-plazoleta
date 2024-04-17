package plazoleta.adapters.driven.jpa.msql.entity;

import jakarta.persistence.*;
import lombok.*;
import plazoleta.adapters.driven.jpa.msql.entity.OrderPlateEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "plate")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class PlateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plate")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "id_category")
    private int categoryId;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private int price;

    @Column(name = "restaurant_id")
    private int restaurantId;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "active")
    private boolean active;

    @OneToMany(mappedBy = "plate")
    private List<OrderPlateEntity> orderPlateList = new ArrayList<>();
}
