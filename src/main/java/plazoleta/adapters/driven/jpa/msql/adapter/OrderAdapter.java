package plazoleta.adapters.driven.jpa.msql.adapter;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import plazoleta.adapters.driven.jpa.msql.entity.order.OrderEntity;
import plazoleta.adapters.driven.jpa.msql.entity.restaurant.RestaurantEntity;
import plazoleta.adapters.driven.jpa.msql.exception.ErrorAccessModifi;
import plazoleta.adapters.driven.jpa.msql.exception.ProductNotFount;
import plazoleta.adapters.driven.jpa.msql.mapper.IOrderEntityMapper;
import plazoleta.adapters.driven.jpa.msql.repository.IOrderRepositoryJPA;
import plazoleta.adapters.driven.jpa.msql.repository.IRestaurantRepositoryJPA;
import plazoleta.domain.model.pedido.Order;
import plazoleta.domain.spi.IOrderPersistencePort;
import plazoleta.domain.spi.IRestaurantPersistencePort;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static plazoleta.adapters.driven.jpa.msql.utils.DataOrdering.getOrdering;

@Service
@AllArgsConstructor
public class OrderAdapter implements IOrderPersistencePort {
    private final IOrderRepositoryJPA orderRepositoryJPA;
    private final IOrderEntityMapper orderEntityMapper;
    private final IRestaurantRepositoryJPA restaurantRepositoryJPA;
    @Override
    public Order save(Order order) {
        order.setState("pendiente");
        if(validOrderUser(order.getUserId())) {
            throw new ErrorAccessModifi("El usuario ya tiene un pedido con estado proceso");
        }
        return orderEntityMapper.toOrder(orderRepositoryJPA.save(orderEntityMapper.toOrderEntity(order)));
    }

    public boolean validOrderUser (int id) {
        Optional<OrderEntity> orderByUser = orderRepositoryJPA.findByUserId(id);
        return orderByUser.isPresent() && !Objects.equals(orderByUser.get().getState(), "entregado");
    }

    @Override
    public List<Order> getOrderByState(String state, int page, int size , int idAuthenticated) {
        Pageable pageable = getOrdering(page, size, true, "date");
        Optional<RestaurantEntity> restaurant = restaurantRepositoryJPA.findByOwnerId(idAuthenticated);
        if (restaurant.isEmpty()) {
            throw new ProductNotFount("No se encontró ningún restaurante para el usuario autenticado.");
        }
        List<Order> getAllOrder = orderEntityMapper.toOrderList(orderRepositoryJPA.findByState(state, pageable));
        return getAllOrder.stream().filter(order -> order.getRestaurantId() == restaurant.get().getId()).collect(Collectors.toList());
    }

}
