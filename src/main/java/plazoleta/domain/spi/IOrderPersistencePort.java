package plazoleta.domain.spi;

import plazoleta.domain.model.pedido.Order;

public interface IOrderPersistencePort {
    Order save(Order order);
}
