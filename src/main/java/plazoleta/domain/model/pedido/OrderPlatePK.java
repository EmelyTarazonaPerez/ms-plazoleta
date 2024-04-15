package plazoleta.domain.model.pedido;

import jakarta.persistence.Column;

public class OrderPlatePK {
    private Integer idOrder;
    private Integer idPlate;

    public OrderPlatePK(Integer idOrder, Integer idPlate) {
        this.idOrder = idOrder;
        this.idPlate = idPlate;
    }

    public Integer getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Integer idOrder) {
        this.idOrder = idOrder;
    }

    public Integer getIdPlate() {
        return idPlate;
    }

    public void setIdPlate(Integer idPlate) {
        this.idPlate = idPlate;
    }
}
