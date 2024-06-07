package plazoleta.adapters.driven.jpa.msql.adapter;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import plazoleta.adapters.driven.jpa.msql.entity.OrderEntity;
import plazoleta.adapters.driven.jpa.msql.entity.OrderPlateEntity;
import plazoleta.adapters.driven.jpa.msql.mapper.IOrderEntityMapper;
import plazoleta.adapters.driven.jpa.msql.repository.IOrderPlateRepositoryJPA;
import plazoleta.domain.model.pedido.Order;
import plazoleta.domain.spi.IOrderPlatePersistencePort;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderPlateAdapter implements IOrderPlatePersistencePort {

    private final IOrderPlateRepositoryJPA orderPlateRepositoryJPA;
    private final IOrderEntityMapper orderEntityMapper;
    @Override
    public void saveOrderPlate(Order order) {
        OrderEntity orderEntity = orderEntityMapper.toOrderEntity(order);

        List<OrderPlateEntity> listOrderPlates = orderEntity.getOrderPlateList();
        for (OrderPlateEntity item : listOrderPlates) {
            item.setOrder(orderEntity);
            orderPlateRepositoryJPA.save(item);
        }
    }
}
