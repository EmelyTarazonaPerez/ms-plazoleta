package plazoleta.adapters.driving.http.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plazoleta.adapters.driven.jpa.msql.entity.UserEntity;
import plazoleta.adapters.driving.http.ConsumerUser;
import plazoleta.adapters.driving.http.dto.AddRestaurantRequest;
import plazoleta.adapters.driving.http.exception.ErrorAccess;
import plazoleta.adapters.driving.http.mapper.IRestaurantRequestMapper;
import plazoleta.domain.api.IRestaurantServicePort;
import plazoleta.domain.model.Restaurant;

import java.math.BigInteger;


@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantRestController {
    private final IRestaurantServicePort restaurantServicePort;
    private final IRestaurantRequestMapper restaurantRequestMapper;
    private final ConsumerUser consumerUser;
    @PostMapping("/create")
    public ResponseEntity<Restaurant> save(@Valid @RequestBody AddRestaurantRequest request) {
        UserEntity entity = consumerUser.getRolByIdUser(request.getOwnerId().getIdUser());
        if (entity.getIdRol().getIdRol() != 2){
            throw new ErrorAccess("El usuario no es propietario");
        }
        Restaurant restaurant = restaurantRequestMapper.toRestaurant(request);
        return new ResponseEntity<>(restaurantServicePort.createRestaurant(restaurant), HttpStatus.OK);
    }

}
