package plazoleta.domain.model;

public class Plate {
        private int id;
        private String name;
        private int categoryId;
        private String description;
        private int price;
        private int restaurantId;
        private String imageUrl;
        private boolean active;

        // Constructor
        public Plate(int id, String name, int categoryId, String description, int price, int restaurantId, String imageUrl, boolean active) {
            this.id = id;
            this.name = name;
            this.categoryId = categoryId;
            this.description = description;
            this.price = price;
            this.restaurantId = restaurantId;
            this.imageUrl = imageUrl;
            this.active = active;
        }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
