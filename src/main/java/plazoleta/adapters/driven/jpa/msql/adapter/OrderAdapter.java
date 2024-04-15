package plazoleta.adapters.driven.jpa.msql.adapter;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import plazoleta.adapters.driven.jpa.msql.entity.order.OrderEntity;
import plazoleta.adapters.driven.jpa.msql.exception.ErrorAccessModifi;
import plazoleta.adapters.driven.jpa.msql.mapper.IOrderEntityMapper;
import plazoleta.adapters.driven.jpa.msql.repository.IOrderRepositoryJPA;
import plazoleta.domain.model.pedido.Order;
import plazoleta.domain.spi.IOrderPersistencePort;

import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderAdapter implements IOrderPersistencePort {
    private final IOrderRepositoryJPA orderRepositoryJPA;
    private final IOrderEntityMapper orderEntityMapper;
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

}
