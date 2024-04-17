package plazoleta.adapters.driving.http.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plazoleta.adapters.driven.jpa.msql.entity.UserEntity;
import plazoleta.adapters.driven.jpa.msql.utils.consumer.ExternalApiConsumption;
import plazoleta.adapters.driving.http.dto.request.AddRestaurantRequest;
import plazoleta.adapters.driving.http.dto.response.RestaurantResponse;
import plazoleta.adapters.driving.http.exception.ErrorAccess;
import plazoleta.adapters.driving.http.mapper.IRestaurantRequestMapper;
import plazoleta.adapters.driving.http.mapper.IRestaurantResponseMapper;
import plazoleta.domain.api.IRestaurantServicePort;
import plazoleta.domain.model.restaurant.Restaurant;

import java.util.List;


@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantRestController {
    private final IRestaurantServicePort restaurantServicePort;
    private final IRestaurantRequestMapper restaurantRequestMapper;
    private final IRestaurantResponseMapper restaurantResponseMapper;
    private final ExternalApiConsumption consumerUser;
    @PostMapping("/create")
    public ResponseEntity<Restaurant> save(@Valid @RequestBody AddRestaurantRequest request,@RequestHeader("Authorization") String token) {
        String auth = token.substring(7);
        UserEntity entity = consumerUser.getRolByIdUser(request.getOwnerId(), auth);
        if (entity.getIdRol().getIdRol() != 2){
            throw new ErrorAccess("El usuario no es propietario");
        }
        Restaurant restaurant = restaurantRequestMapper.toRestaurant(request);
        return new ResponseEntity<>(restaurantServicePort.createRestaurant(restaurant), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<RestaurantResponse>> getAll (
            @RequestParam (defaultValue = "0") int page,
            @RequestParam (defaultValue = "10") int size,
            @RequestParam (defaultValue = "false") boolean sort){
        return new ResponseEntity<>( restaurantResponseMapper.toResponseList(restaurantServicePort.getAll(page, size, sort)), HttpStatus.OK);
    }

}
