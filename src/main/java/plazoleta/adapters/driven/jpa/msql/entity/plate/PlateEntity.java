package plazoleta.adapters.driven.jpa.msql.entity.plate;

import jakarta.persistence.*;
import lombok.Data;
import plazoleta.adapters.driven.jpa.msql.entity.restaurant.RestaurantEntity;

@Entity
@Table(name = "plate")
@Data
public class PlateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
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
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private RestaurantEntity restaurantId;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "active")
    private boolean active;
}
