package plazoleta.adapters.driving.http.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import plazoleta.adapters.driving.http.dto.request.AddRestaurantRequest;
import plazoleta.domain.model.restaurant.Restaurant;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IRestaurantRequestMapper {
    @Mappings(value = {
            @Mapping(target = "id", ignore = true),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "address", target = "address"),
            @Mapping(source = "ownerId", target = "ownerId"),
            @Mapping(source = "phone", target = "phone"),
            @Mapping(source = "urlLogo", target = "urlLogo"),
            @Mapping(source = "nit", target = "nit"),
    })
    Restaurant toRestaurant (AddRestaurantRequest addRestaurantRequest);
}
