package plazoleta.adapters.driving.http.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import plazoleta.adapters.driven.jpa.msql.entity.restaurant.UserEntity;
import plazoleta.adapters.driving.http.ConsumerUser;
import plazoleta.adapters.driving.http.JwtService.JwtTokenValidator;
import plazoleta.adapters.driving.http.dto.request.order.AddOrderRequest;
import plazoleta.adapters.driving.http.dto.request.order.AssociatedEntity;
import plazoleta.adapters.driving.http.mapper.IOrderRequestMapper;
import plazoleta.domain.api.IOrderServicePort;
import plazoleta.domain.model.pedido.Order;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderRestControllerTest {

    @Mock
    private IOrderServicePort orderServicePort;
    @Mock
    private IOrderRequestMapper orderRequestMapper;
    @Mock
    private ConsumerUser consumerUser;
    @Mock
    private JwtTokenValidator jwtTokenValidator;
    @InjectMocks
    private OrderRestController orderRestController;
    @Test
    void save() {
        AddOrderRequest orderRequest = new AddOrderRequest(33, LocalDate.now(), "pendiente", 24, 6,null);

        String token = "Bearer TOKEN_DE_PRUEBA";
        String auth = "TOKEN_DE_PRUEBA";
        int idAuthenticated = 33; // Supongamos que el ID del usuario autenticado es 456

        UserEntity infoChef = new UserEntity();
        infoChef.setIdUser(24); // Supongamos que la información del chef es válida

        Order order = new Order( 1, 123, LocalDate.now(), "pendiente", 26, 6, null);

        // Configurar el comportamiento simulado
        when(jwtTokenValidator.getUserIdFromToken(auth)).thenReturn(idAuthenticated);
        when(consumerUser.getRolByIdUser(24, auth)).thenReturn(infoChef);
        when(orderRequestMapper.toOrder(orderRequest)).thenReturn(order);

        // Ejecutar el método bajo prueba
        ResponseEntity<Order> response = orderRestController.save(orderRequest, token);

        // Verificar que la respuesta tiene un estado HTTP 200 OK
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}