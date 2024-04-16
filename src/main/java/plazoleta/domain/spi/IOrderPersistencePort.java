package plazoleta.domain.spi;

import plazoleta.adapters.driving.http.dto.request.order.AddOrderRequest;
import plazoleta.domain.model.pedido.Order;

import java.util.List;

public interface IOrderPersistencePort {
    Order save(Order order);
    List<Order> getOrderByState (String state, int page, int size, int idAuthenticated);

    Order takeOrder (int idAuthenticated, int idOrder, AddOrderRequest orderRequest);
    String readyToDelivery(int idAuthenticated, int id, AddOrderRequest orderRequest, String auth);
}
