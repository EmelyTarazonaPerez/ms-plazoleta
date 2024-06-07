package plazoleta.adapters.driven.jpa.msql.adapter;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import plazoleta.adapters.driven.jpa.msql.entity.OrderEntity;
import plazoleta.adapters.driven.jpa.msql.exception.ErrorAccessModified;
import plazoleta.adapters.driven.jpa.msql.exception.ErrorBaseDatos;
import plazoleta.adapters.driven.jpa.msql.exception.ProductNotFount;
import plazoleta.adapters.driven.jpa.msql.mapper.IOrderEntityMapper;
import plazoleta.adapters.driven.jpa.msql.repository.IOrderRepositoryJPA;
import plazoleta.domain.model.pedido.Order;
import plazoleta.domain.spi.IOrderPersistencePort;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static plazoleta.adapters.driven.utils.DataOrdering.getOrdering;
import static plazoleta.adapters.driven.utils.constants.ConstanstUtils.*;
import static plazoleta.config.Constants.MESSAGE_ORDER_PROCESS;
import static plazoleta.config.Constants.PRODUCT_NOT_CANCEL;

@Service
@AllArgsConstructor
public class OrderAdapter implements IOrderPersistencePort {
    private final IOrderRepositoryJPA orderRepositoryJPA;
    private final IOrderEntityMapper orderEntityMapper;

    @Override
    public Order save(Order order) {
        order.setState("pendiente");
        if (validOrderUser(order.getUserId())) {
            throw new ErrorAccessModified(MESSAGE_ORDER_PROCESS);
        }
        OrderEntity orderEntityNew = orderEntityMapper.toOrderEntity(order);
        return orderEntityMapper.toOrder(orderRepositoryJPA.save(orderEntityNew));
    }

    public boolean validOrderUser(int id) {
        Optional<OrderEntity> orderByUser = orderRepositoryJPA.findByUserId(id);
        return orderByUser.isPresent() && !Objects.equals(orderByUser.get().getState(), "entregado");
    }

    @Override
    public List<Order> getOrderByState(String state, int page, int size) {
        Pageable pageable = getOrdering(page, size, true, "date");
        return orderEntityMapper.toOrderList(orderRepositoryJPA.findByState(state, pageable));
    }

    @Override
    public String readyToDelivery(Order order) {
        try {
            orderRepositoryJPA.save(orderEntityMapper.toOrderEntity(order));
            return STAGE_PRODUCT_CHANGE_SUCCESSFULLY;
        } catch (ProductNotFount e) {
            throw new ProductNotFount(NO_FOUNT_ORDER);
        }
    }

    @Override
    public Order takeOrder(Order order) {
        return orderEntityMapper.toOrder(orderRepositoryJPA.save(
                orderEntityMapper.toOrderEntity(order)));
    }

    @Override
    public String deliveryOrder(Order order) {
        try {
            orderRepositoryJPA.save(orderEntityMapper.toOrderEntity(order));
            return STAGE_PRODUCT_CHANGE_SUCCESSFULLY;
        } catch (Exception e) {
            throw new ErrorBaseDatos(ERROR_BASE_DATOS);
        }
    }

    @Override
    public String cancelOrder(Order order) {
        try {
            orderRepositoryJPA.delete(orderEntityMapper.toOrderEntity(order));
            return PRODUCT_CANCEL;
        } catch (ErrorBaseDatos e) {
            throw new ErrorBaseDatos(PRODUCT_NOT_CANCEL);
        }
    }

    @Override
    public Order getOrderById(int id) {
        Optional<OrderEntity> orderEntity = orderRepositoryJPA.findById(id);
        if (orderEntity.isEmpty()) {
            throw new ProductNotFount(NO_FOUNT_ORDER);
        }
        return orderEntityMapper.toOrder(orderEntity.get());
    }

}

