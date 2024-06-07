package plazoleta.adapters.driving.http.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plazoleta.adapters.driving.http.dto.request.order.OrderStateModificationDTO;
import plazoleta.adapters.driving.http.dto.request.order.AddOrderRequest;
import plazoleta.adapters.driving.http.dto.response.OrderDto;
import plazoleta.adapters.driving.http.dto.response.UserDto;
import plazoleta.adapters.driving.http.exception.ErrorAccess;
import plazoleta.adapters.driving.http.mapper.IOrderRequestMapper;
import plazoleta.adapters.driving.http.mapper.IUserRequestMapper;
import plazoleta.domain.api.IExternalApiServicePort;
import plazoleta.domain.api.IOrderServicePort;
import plazoleta.domain.api.ITokenValidatorServicePort;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderRestController {
    private final IOrderRequestMapper orderRequestMapper;
    private final IOrderServicePort orderServicePort;
    private final ITokenValidatorServicePort tokenValidator;
    private final IExternalApiServicePort consumerUser;
    private final IUserRequestMapper userRequestMapper;

    @PostMapping("/create")
    public ResponseEntity<OrderDto> save(@RequestBody AddOrderRequest orderRequest,
                                         @RequestHeader("Authorization") String token) {
        String auth = token.substring(7);
        try {
            int idAuthenticated = tokenValidator.getUserIdFromToken(auth);
            UserDto infoChef = userRequestMapper.user(consumerUser.getRolByIdUser(orderRequest.getChefId(), auth));
            OrderDto order = orderRequestMapper.toOrderDto(orderServicePort.create(
                    orderRequestMapper.toOrder(orderRequest), idAuthenticated, infoChef, auth));

             return  new ResponseEntity<>(order, HttpStatus.OK);
        } catch (Exception e) {
            throw new ErrorAccess("Error al crear orden" + e);
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<OrderDto>> get(@RequestParam(defaultValue = "pendiente") String state,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "2") int size,
                                           @RequestHeader("Authorization") String token) {
        String auth = token.substring(7);
        int idAuthenticated = tokenValidator.getUserIdFromToken(auth);

        return new ResponseEntity<>(orderRequestMapper.toOrderDto(
                orderServicePort.getOrderByState(state, page, size, idAuthenticated)), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<OrderDto> takeOrder(@PathVariable int id,
                                           @RequestBody AddOrderRequest orderRequest,
                                           @RequestHeader("Authorization") String token) {
        String auth = token.substring(7);
        int idAuthenticated = tokenValidator.getUserIdFromToken(auth);
        return new ResponseEntity<>(orderRequestMapper.toOrderDto(
                orderServicePort.takeOrder(idAuthenticated, id, orderRequest)), HttpStatus.OK);
    }

    @PutMapping("/{id}/ready-to-delivery")
    public ResponseEntity<String> readyToDelivery (@PathVariable int id,
                                           @RequestBody AddOrderRequest orderRequest,
                                           @RequestHeader("Authorization") String token) {
        String auth = token.substring(7);
        int idAuthenticated = tokenValidator.getUserIdFromToken(auth);
        return new ResponseEntity<>(orderServicePort.readyToDelivery(idAuthenticated, id, orderRequest, auth), HttpStatus.OK);
    }

    @PutMapping("/{id}/deliver-order")
    public ResponseEntity<String> deliverOrder (@PathVariable int id,
                                                @RequestBody OrderStateModificationDTO orderRequest,
                                                @RequestHeader("Authorization") String token) {
        String auth = token.substring(7);
        int idAuthenticated = tokenValidator.getUserIdFromToken(auth);
        return new ResponseEntity<>(orderServicePort.deliveryOrder(idAuthenticated, id, orderRequest, auth), HttpStatus.OK);
    }

    @PutMapping("/{id}/cancel-order")
    public ResponseEntity<String> cancelOrder (@PathVariable int id,
                                                @RequestHeader("Authorization") String token) {
        String auth = token.substring(7);
        int idAuthenticated = tokenValidator.getUserIdFromToken(auth);
        return new ResponseEntity<>(orderServicePort.cancelOrder(idAuthenticated, id), HttpStatus.OK);
    }
}
