package plazoleta.domain.spi;

import plazoleta.adapters.driven.jpa.msql.entity.OrderEntity;
import plazoleta.domain.model.pedido.Order;

public interface IOrderPlatePersistencePort {
    void saveOrderPlate(Order order);
}
