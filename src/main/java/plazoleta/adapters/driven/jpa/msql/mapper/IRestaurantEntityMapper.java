package plazoleta.adapters.driven.jpa.msql.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;
import plazoleta.adapters.driven.jpa.msql.entity.restaurant.RestaurantEntity;
import plazoleta.domain.model.restaurant.Restaurant;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IRestaurantEntityMapper {
    @Mappings(value = {
            @Mapping(target = "id", ignore = true),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "address", target = "address"),
            @Mapping(source = "ownerId", target = "ownerId"),
            @Mapping(source = "phone", target = "phone"),
            @Mapping(source = "urlLogo", target = "urlLogo"),
            @Mapping(source = "nit", target = "nit"),
    })
    Restaurant toRestaurant(RestaurantEntity restaurant);

    @InheritInverseConfiguration
    RestaurantEntity toRestaurantEntity(Restaurant user);
    List<Restaurant> toRestaurantList(Page<RestaurantEntity> all);
}
