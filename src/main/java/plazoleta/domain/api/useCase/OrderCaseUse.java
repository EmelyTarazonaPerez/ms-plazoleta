package plazoleta.domain.api.useCase;

import plazoleta.adapters.driven.jpa.msql.entity.UserEntity;
import plazoleta.adapters.driving.http.dto.request.order.AddOrderRequest;
import plazoleta.adapters.driving.http.dto.request.order.OrderStateModificationDTO;
import plazoleta.domain.api.IOrderServicePort;
import plazoleta.domain.exception.ExceptionValid;
import plazoleta.domain.model.pedido.Order;
import plazoleta.domain.model.pedido.OrderPlate;
import plazoleta.domain.spi.IOrderPersistencePort;

import java.util.List;

public class OrderCaseUse implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;

    public OrderCaseUse(IOrderPersistencePort orderPersistencePort) {
        this.orderPersistencePort = orderPersistencePort;
    }

    @Override
    public Order create(Order order, int idAuthUser, UserEntity infoChef, String auth) {

        if (idAuthUser != order.getUserId())  throw new ExceptionValid("El idUser es diferente al usuario logeado");
        if (infoChef.getIdRol().getIdRol() != 3) throw new ExceptionValid("El usuario no es chef");

        int idRestaurant = order.getRestaurantId();
        List<OrderPlate> listaPlatos = order.getListPlates();

        boolean mismoIdRestaurante = true;
        for (OrderPlate plato : listaPlatos) {
            if (plato.getPlate().getRestaurantId() != idRestaurant) {
                mismoIdRestaurante = false;
                break;
            }
        }

        if (!mismoIdRestaurante) throw new ExceptionValid("los platos no son del mismo restaurante");
        return orderPersistencePort.save(order, infoChef, auth);
    }

    @Override
    public List<Order> getOrderByState(String state, int page, int size, int idAuthenticated) {
        return orderPersistencePort.getOrderByState(state, page, size, idAuthenticated);
    }

    @Override
    public Order takeOrder(int idAuthenticated, int idOrder, AddOrderRequest orderRequest) {
        return orderPersistencePort.takeOrder(idAuthenticated, idOrder, orderRequest);
    }

    @Override
    public String readyToDelivery(int idAuthenticated, int id, AddOrderRequest orderRequest, String auth) {
        return orderPersistencePort.readyToDelivery(idAuthenticated, id, orderRequest, auth);
    }

    @Override
    public String deliveryOrder(int idAuthenticated, int id, OrderStateModificationDTO orderRequest, String auth) {
        return orderPersistencePort.deliveryOrder(idAuthenticated, id, orderRequest, auth);
    }

    @Override
    public String cancelOrder(int idAuthenticated, int id) {
        return orderPersistencePort.cancelOrder(idAuthenticated, id);
    }

}
