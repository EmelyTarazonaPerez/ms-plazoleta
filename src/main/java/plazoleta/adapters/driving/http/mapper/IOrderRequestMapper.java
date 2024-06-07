package plazoleta.adapters.driving.http.mapper;

import org.mapstruct.*;
import plazoleta.adapters.driving.http.dto.request.order.AddOrderRequest;
import plazoleta.adapters.driving.http.dto.response.OrderDto;
import plazoleta.domain.model.pedido.Order;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IOrderRequestMapper {

    @Mappings(value = {
            @Mapping(target = "id", ignore = true),
            @Mapping(source = "plates", target = "listPlates"),
    })
    Order toOrder(AddOrderRequest orderRequest);

    @InheritInverseConfiguration
    OrderDto toOrderDto(Order order);
    List<OrderDto> toOrderDto(List<Order> order);
}
