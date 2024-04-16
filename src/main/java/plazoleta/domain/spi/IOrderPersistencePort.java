package plazoleta.domain.spi;

import plazoleta.domain.model.pedido.Order;

import java.util.List;

public interface IOrderPersistencePort {
    Order save(Order order);
    List<Order> getOrderByState (String state, int page, int size, int idAuthenticated);
}
