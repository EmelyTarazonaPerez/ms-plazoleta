package plazoleta.adapters.driven.jpa.msql.mapper;

import org.mapstruct.*;
import plazoleta.adapters.driven.jpa.msql.entity.order.OrderEntity;
import plazoleta.domain.model.pedido.Order;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderEntityMapper {

    @Mappings(value = {
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "userId", target = "userId"),
            @Mapping(source = "date", target = "date"),
            @Mapping(source = "state", target = "state"),
            @Mapping(source = "chefId", target = "chefId"),
            @Mapping(source = "restaurantId", target = "restaurantId"),
            @Mapping(source = "plateEntityList", target = "plates")
    })
    Order toOrder (OrderEntity order);
    List<Order> toOrderList(List<OrderEntity> byState);
    @InheritInverseConfiguration
    OrderEntity toOrderEntity(Order order);


}
