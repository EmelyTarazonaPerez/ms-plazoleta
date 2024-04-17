package plazoleta.domain.model.pedido;
import plazoleta.adapters.driven.jpa.msql.entity.OrderPlateEntity;
import plazoleta.adapters.driving.http.dto.request.order.AssociatedEntity;
import plazoleta.domain.model.plate.Plate;

import java.time.LocalDate;
import java.util.List;

public class Order {
    private int id;
    private int userId;
    private LocalDate date;
    private String state;
    private int chefId;
    private int restaurantId;
    private List<OrderPlate> listPlates;

    public Order(int id, int userId, LocalDate date, String state, int chefId, int restaurantId, List<OrderPlate> listPlates) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.state = state;
        this.chefId = chefId;
        this.restaurantId = restaurantId;
        this.listPlates = listPlates;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getChefId() {
        return chefId;
    }

    public void setChefId(int chefId) {
        this.chefId = chefId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public List<OrderPlate> getListPlates() {
        return listPlates;
    }

    public void setListPlates(List<OrderPlate> listPlates) {
        this.listPlates = listPlates;
    }
}
