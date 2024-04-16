package plazoleta.domain.api;

import plazoleta.adapters.driven.jpa.msql.entity.restaurant.UserEntity;
import plazoleta.domain.model.pedido.Order;

import java.util.List;

public interface IOrderServicePort {
    Order create (Order order, int idAuthUser, UserEntity infoChef);
    List<Order> getOrderByState(String state, int page, int size, int idAuthenticated);
}