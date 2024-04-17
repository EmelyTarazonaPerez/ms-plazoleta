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
            @Mapping(source = "plates", target = "listPlates"),
    })
    Order toOrder(AddOrderRequest orderRequest);
}
