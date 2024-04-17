package plazoleta.domain.model.pedido;

import plazoleta.domain.model.plate.Plate;

import java.util.List;

public class OrderPlate {
    private int id;
    private Order order;
    private Plate plate;
    private int amount;

    public OrderPlate(int id, Plate plate, int amount) {
        this.id = id;
        this.plate = plate;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Plate getPlate() {
        return plate;
    }

    public void setPlate(Plate plate) {
        this.plate = plate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
