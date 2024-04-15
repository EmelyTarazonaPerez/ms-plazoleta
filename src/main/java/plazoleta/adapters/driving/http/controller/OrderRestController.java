package plazoleta.adapters.driving.http.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plazoleta.adapters.driven.jpa.msql.entity.restaurant.UserEntity;
import plazoleta.adapters.driving.http.ConsumerUser;
import plazoleta.adapters.driving.http.JwtService.JwtTokenValidator;
import plazoleta.adapters.driving.http.dto.request.order.AddOrderRequest;
import plazoleta.adapters.driving.http.exception.ErrorAccess;
import plazoleta.adapters.driving.http.mapper.IOrderRequestMapper;
import plazoleta.domain.api.IOrderServicePort;
import plazoleta.domain.model.pedido.Order;

import java.util.Optional;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderRestController {
    private final IOrderRequestMapper orderRequestMapper;
    private final IOrderServicePort orderServicePort;
    private final JwtTokenValidator jwtTokenValidator;

    private final ConsumerUser consumerUser;

    @PostMapping("/create")
    public ResponseEntity<Order> save(@RequestBody AddOrderRequest orderRequest, @RequestHeader("Authorization") String token) {
        String auth = token.substring(7);
        try {
            int idAuthenticated = jwtTokenValidator.getUserIdFromToken(auth);
            UserEntity infoChef = consumerUser.getRolByIdUser(orderRequest.getChefId(), auth);

            return new ResponseEntity<>(orderServicePort.create(
                    orderRequestMapper.toOrder(orderRequest),
                    idAuthenticated,
                    infoChef),
                    HttpStatus.OK);

        } catch (Exception e) {
            throw new ErrorAccess("Error al crear orden" + e);
        }
    }
}
