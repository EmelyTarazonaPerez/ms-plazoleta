package plazoleta.adapters.driving.http.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import plazoleta.adapters.driven.jpa.msql.entity.restaurant.UserEntity;
import plazoleta.adapters.driving.http.ConsumerUser;
import plazoleta.adapters.driving.http.JwtService.JwtTokenValidator;
import plazoleta.adapters.driving.http.dto.request.order.AddOrderRequest;
import plazoleta.adapters.driving.http.dto.request.order.AssociatedEntity;
import plazoleta.adapters.driving.http.dto.response.RestaurantResponse;
import plazoleta.adapters.driving.http.mapper.IOrderRequestMapper;
import plazoleta.domain.api.IOrderServicePort;
import plazoleta.domain.model.pedido.Order;
import plazoleta.domain.model.plate.Plate;
import plazoleta.domain.model.restaurant.Restaurant;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    private MockMvc mockMcv;

    @BeforeEach
    void setUp() {
        mockMcv = MockMvcBuilders.standaloneSetup(orderRestController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();

    }
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

    @Test
    void get() throws Exception {
        List<Order> orders = new ArrayList<>();

        when(jwtTokenValidator.getUserIdFromToken("TOKEN_DE_PRUEBA")).thenReturn(26);
        when(orderServicePort.getOrderByState("pendiente",0,5, 26)).thenReturn(orders);

        mockMcv.perform(MockMvcRequestBuilders.get("/order/get-all")
                        .param("state", "pendiente")
                        .param("page", "0")
                        .param("size", "5")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer TOKEN_DE_PRUEBA")
                )
                .andExpect(status().isOk());
    }
}