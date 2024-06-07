package plazoleta.adapters.driving.http.mapper;

import org.mapstruct.*;
import plazoleta.adapters.driving.http.dto.request.AddRestaurantRequest;
import plazoleta.adapters.driving.http.dto.response.RestaurantDto;
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

    @InheritInverseConfiguration
    RestaurantDto toRestaurantDto(Restaurant restaurant);
}
