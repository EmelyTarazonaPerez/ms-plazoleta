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
import plazoleta.adapters.driven.jpa.msql.entity.UserEntity;
import plazoleta.adapters.driven.utils.consumer.ExternalAdapterApi;
import plazoleta.adapters.driving.http.dto.request.order.OrderStateModificationDTO;
import plazoleta.adapters.driven.security.TokenAdapter;
import plazoleta.adapters.driving.http.dto.request.order.AddOrderRequest;
import plazoleta.adapters.driving.http.mapper.IOrderRequestMapper;
import plazoleta.domain.api.IOrderServicePort;
import plazoleta.domain.model.pedido.Order;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class OrderRestControllerTest {

    @Mock
    private IOrderServicePort orderServicePort;
    @Mock
    private IOrderRequestMapper orderRequestMapper;
    @Mock
    private ExternalAdapterApi consumerUser;
    @Mock
    private TokenAdapter jwtValidatorAdapter;
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
        when(jwtValidatorAdapter.getUserIdFromToken(auth)).thenReturn(idAuthenticated);
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

        when(jwtValidatorAdapter.getUserIdFromToken("TOKEN_DE_PRUEBA")).thenReturn(26);
        when(orderServicePort.getOrderByState("pendiente",0,5, 26)).thenReturn(orders);

        mockMcv.perform(MockMvcRequestBuilders.get("/order/get-all")
                        .param("state", "pendiente")
                        .param("page", "0")
                        .param("size", "5")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer TOKEN_DE_PRUEBA")
                )
                .andExpect(status().isOk());
    }

    @Test
    void deliverOrder() throws Exception {
        int id = 1;
        String token = "Bearer mockToken";
        String expected = "El estado de producto ya fue cambiado a entregado";
        OrderStateModificationDTO orderRequest = new OrderStateModificationDTO();

        when(jwtValidatorAdapter.getUserIdFromToken("mockToken")).thenReturn(1);
        when(orderServicePort.deliveryOrder(1, id, orderRequest, "mockToken")).thenReturn(expected);

        // Act
        ResponseEntity<String> response = orderRestController.deliverOrder(id, orderRequest, token);

        // Assert
        verify(jwtValidatorAdapter).getUserIdFromToken("mockToken");
        verify(orderServicePort).deliveryOrder(1, id, orderRequest, "mockToken");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
    }

    @Test
    void cancelOrder_SuccessfulCancellation() {
        // Arrange
        int id = 1;
        String token = "Bearer mockToken";

        when(jwtValidatorAdapter.getUserIdFromToken("mockToken")).thenReturn(1);
        when(orderServicePort.cancelOrder(1, id)).thenReturn("Su orden fue cancelada correctamente");

        // Act
        ResponseEntity<String> response = orderRestController.cancelOrder(id, token);

        // Assert
        verify(jwtValidatorAdapter).getUserIdFromToken("mockToken");
        verify(orderServicePort).cancelOrder(1, id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Su orden fue cancelada correctamente", response.getBody());
    }
}