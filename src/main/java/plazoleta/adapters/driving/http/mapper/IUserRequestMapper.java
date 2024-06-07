package plazoleta.adapters.driving.http.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import plazoleta.adapters.driven.jpa.msql.entity.RolEntity;
import plazoleta.adapters.driving.http.dto.response.UserDto;
import plazoleta.domain.model.restaurant.User;

import java.time.LocalDate;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IUserRequestMapper {
    @Mappings(value = {
            @Mapping(source = "idUser", target = "idUser"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "lastName", target = "lastName"),
            @Mapping(source = "identificationDocument", target = "identificationDocument"),
            @Mapping(source = "gmail", target = "gmail"),
            @Mapping(source = "password", target = "password"),
            @Mapping(source = "idRol", target = "idRol")
    })
    UserDto user (User user);
}
