package plazoleta.adapters.driven.jpa.msql.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import plazoleta.adapters.driven.jpa.msql.entity.OrderPlateEntity;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderPlateEntityMapper {


}
