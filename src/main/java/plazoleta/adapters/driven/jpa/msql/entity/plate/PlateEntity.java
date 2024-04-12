package plazoleta.adapters.driven.jpa.msql.entity.plate;

import jakarta.persistence.*;
import lombok.*;
import plazoleta.adapters.driven.jpa.msql.entity.restaurant.RestaurantEntity;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "plate")
public class PlateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plate")
    private int id;
    @Column(name = "name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity categoryId;
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
}
