package plazoleta.adapters.driving.http.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import plazoleta.adapters.driving.http.dto.response.RestaurantResponse;
import plazoleta.domain.model.restaurant.Restaurant;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IRestaurantResponseMapper {
    @Mappings(value = {
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "urlLogo", target = "urlLogo"),
    })
    List<RestaurantResponse> toResponseList(List<Restaurant> all);
}
