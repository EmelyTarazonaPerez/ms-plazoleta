package plazoleta.domain.api;

import plazoleta.adapters.driven.jpa.msql.entity.UserEntity;
import plazoleta.adapters.driving.http.dto.request.order.AddOrderRequest;
import plazoleta.adapters.driving.http.dto.request.order.OrderStateModificationDTO;
import plazoleta.domain.model.pedido.Order;

import java.util.List;

public interface IOrderServicePort {
    Order create (Order order, int idAuthUser, UserEntity infoChef, String auth);
    List<Order> getOrderByState(String state, int page, int size, int idAuthenticated);
    Order takeOrder(int idAuthenticated, int idOrder, AddOrderRequest orderRequest);
    String readyToDelivery(int idAuthenticated, int id, AddOrderRequest orderRequest, String auth);
    String deliveryOrder(int idAuthenticated, int id, OrderStateModificationDTO orderRequest, String auth);
    String cancelOrder(int idAuthenticated, int id);
}
