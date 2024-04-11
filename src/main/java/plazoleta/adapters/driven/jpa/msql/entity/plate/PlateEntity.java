package plazoleta.adapters.driven.jpa.msql.entity;

public class PlateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "category_id")
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
}
