package plazoleta.adapters.driving.http.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import plazoleta.adapters.driving.http.dto.request.order.AddOrderRequest;
import plazoleta.domain.model.pedido.Order;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IOrderRequestMapper {

    @Mappings(value = {
            @Mapping(target = "id", ignore = true),
            @Mapping(source = "userId", target = "userId"),
            @Mapping(source = "date", target = "date"),
            @Mapping(source = "state", target = "state"),
            @Mapping(source = "chefId", target = "chefId"),
            @Mapping(source = "restaurantId", target = "restaurantId"),
            @Mapping(source = "plates", target = "plates")
    })
    Order toOrder(AddOrderRequest orderRequest);
}
