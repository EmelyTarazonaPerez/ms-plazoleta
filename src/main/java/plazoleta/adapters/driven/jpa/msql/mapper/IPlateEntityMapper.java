package plazoleta.adapters.driven.jpa.msql.mapper;

import org.mapstruct.*;
import plazoleta.adapters.driven.jpa.msql.entity.plate.PlateEntity;
import plazoleta.domain.model.plate.Plate;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IPlateEntityMapper {
    @Mappings(value = {
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "categoryId", target = "categoryId"),
            @Mapping(source = "description", target = "description"),
            @Mapping(source = "price", target = "price"),
            @Mapping(source = "restaurantId", target = "restaurantId"),
            @Mapping(source = "imageUrl", target = "imageUrl"),
            @Mapping(source = "active", target = "active"),
    })
    PlateEntity toPlateEntity(Plate plate);

    @InheritInverseConfiguration
    Plate toPlate (PlateEntity plateEntity);
}
