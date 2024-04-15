package plazoleta.domain.api;

import plazoleta.adapters.driven.jpa.msql.entity.restaurant.UserEntity;
import plazoleta.domain.model.pedido.Order;

public interface IOrderServicePort {
    Order create (Order order, int idAuthUser, UserEntity infoChef);
}
