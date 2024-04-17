package plazoleta.adapters.driven.jpa.msql.adapter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import plazoleta.adapters.driven.jpa.msql.entity.order.OrderEntity;
import plazoleta.adapters.driven.jpa.msql.entity.restaurant.RestaurantEntity;
import plazoleta.adapters.driven.jpa.msql.entity.restaurant.UserEntity;
import plazoleta.adapters.driven.jpa.msql.exception.ErrorAccessModified;
import plazoleta.adapters.driven.jpa.msql.exception.ProductNotFount;
import plazoleta.adapters.driven.jpa.msql.mapper.IOrderEntityMapper;
import plazoleta.adapters.driven.jpa.msql.repository.IOrderRepositoryJPA;
import plazoleta.adapters.driven.jpa.msql.repository.IRestaurantRepositoryJPA;
import plazoleta.adapters.driven.jpa.msql.utils.consumer.ExternalApiConsumption;
import plazoleta.adapters.driving.http.dto.request.order.AddOrderRequest;
import plazoleta.adapters.driving.http.dto.request.order.OrderStateModificationDTO;
import plazoleta.domain.model.pedido.Order;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static plazoleta.adapters.driven.jpa.msql.utils.constants.ConstanstUtils.STAGE_PRODUCT_NOT_PREPARING;

@ExtendWith(MockitoExtension.class)
class OrderAdapterTest {

    @Mock
    private IOrderRepositoryJPA orderRepositoryJPA;
    @Mock
    private IOrderEntityMapper orderEntityMapper;
    @Mock
    private ExternalApiConsumption externalApiConsumption;
    @Mock
    private IRestaurantRepositoryJPA restaurantRepositoryJPA;
    @Mock
    private OrderAdapter orderAdapterMock;
    @InjectMocks
    private OrderAdapter orderAdapter;

    private Order order;
    @BeforeEach
    void setUp() {
         order = new Order( 1, 33, LocalDate.now(), "pendiente", 26, 6, null);

    }

    @Test
    void save() {
        Order order = new Order( 1, 33, LocalDate.now(), "pendiente", 26, 6, null);
        OrderEntity orderEntity = new OrderEntity();

        when(orderEntityMapper.toOrderEntity(order)).thenReturn(orderEntity);
        when(orderRepositoryJPA.findByUserId(33)).thenReturn(Optional.empty());
        when(orderRepositoryJPA.save(orderEntity)).thenReturn(orderEntity);
        when(orderEntityMapper.toOrder(orderEntity)).thenReturn(order);

        final Order result = orderAdapter.save(order);

        Assertions.assertNotNull(result);


    }

    @Test
    void save_OrderEntityPendiente() {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setState("pendiente");
        when(orderRepositoryJPA.findByUserId(33)).thenReturn(Optional.of(orderEntity));
        assertThrows(ErrorAccessModified.class, () -> orderAdapter.save(order));
    }


    @Test
    void validOrderUser() {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setState("pendiente");

        when(orderRepositoryJPA.findByUserId(33)).thenReturn(Optional.of(orderEntity));

        final boolean  result = orderAdapter.validOrderUser(33);

        Assertions.assertNotNull(result);

    }

    @Test
    void getOrderByState() {
        // Datos de prueba
        String state = "pendiente";
        int page = 0;
        int size = 10;
        int idAuthenticated = 26; // Supongamos que el ID de usuario autenticado es 123
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        List<OrderEntity> orderEntities = new ArrayList<>();
        // Supongamos que la lista contiene algunas órdenes con chefId igual a idAuthenticated
        orderEntities.add(new OrderEntity());
        orderEntities.add(new OrderEntity());

        when(restaurantRepositoryJPA.findByOwnerId(26)).thenReturn(Optional.of(restaurantEntity));
        when(orderRepositoryJPA.findByState(eq(state), any())).thenReturn(orderEntities);

        // Simular mapeo de entidades a objetos de orden
        List<Order> orders = new ArrayList<>();
        // Supongamos que el mapeo es exitoso y devuelve una lista de órdenes
        orders.add(order);
        orders.add(order);

        when(orderEntityMapper.toOrderList(orderEntities)).thenReturn(orders);

        // Ejecutar el método bajo prueba
        List<Order> resultOrders = orderAdapter.getOrderByState(state, page, size, idAuthenticated);
        // Verificar que el resultado contiene solo órdenes con chefId igual a idAuthenticated
        for (Order order : resultOrders) {
            assertEquals(idAuthenticated, order.getChefId());
        }
    }

    @Test
    void takeOrder() {
        int idAuthenticated = 26;
        int idOrder = 26;
        AddOrderRequest addOrderRequest = new AddOrderRequest(1,LocalDate.now(), "preparado", 26, 6, null);
        OrderEntity orderEntity = new OrderEntity();

        when(orderRepositoryJPA.findById(idOrder)).thenReturn(Optional.of(orderEntity));
        when(orderRepositoryJPA.save(orderEntity)).thenReturn(orderEntity);
        when(orderEntityMapper.toOrder(orderEntity)).thenReturn(order);

        final Order result = orderAdapter.takeOrder(idAuthenticated, idOrder, addOrderRequest);

        Assertions.assertNotNull(result);

    }

    @Test
    void readyToDelivery() {
        int idAuthenticated = 26;
        int idOrder = 26;
        String auth = "authToken";
        AddOrderRequest addOrderRequest = new AddOrderRequest();
        Optional<OrderEntity> orderEntity = Optional.of(new OrderEntity());
        UserEntity userEntity = new UserEntity();
        userEntity.setIdUser(33);

        when(orderRepositoryJPA.findById(26)).thenReturn(orderEntity);

        final String result = orderAdapter.readyToDelivery(idAuthenticated, idOrder, addOrderRequest, auth);

        Assertions.assertEquals("Esta orden no ha pasado por la etapa de preparacion", result);
    }

    @Test
    void readyToDelivery_StateCheck() {
        int idAuthenticated = 26;
        int idOrder = 26;
        String auth = "authToken";
        AddOrderRequest addOrderRequest = new AddOrderRequest();
        Optional<OrderEntity> orderEntity = Optional.of(new OrderEntity());
        orderEntity.get().setState("preparando");
        UserEntity userEntity = new UserEntity();
        userEntity.setIdUser(33);

        when(orderRepositoryJPA.findById(26)).thenReturn(orderEntity);

        final String result = orderAdapter.readyToDelivery(idAuthenticated, idOrder, addOrderRequest, auth);

        Assertions.assertEquals("El pedido debe estar en estado listo", result);
    }

    @Test
    void readyToDelivery_Order() {
        // Arrange
        int id = 1;
        AddOrderRequest orderRequest = new AddOrderRequest();
        orderRequest.setState("listo");
        String auth = "authToken";

        Optional<OrderEntity> orderEntity = Optional.of(new OrderEntity());
        orderEntity.get().setState("preparando"); // Simulate order is in preparing state
        orderEntity.get().setUserId(33); // Set user ID for order
        when(orderRepositoryJPA.findById(id)).thenReturn(orderEntity);

        // Simulate that order is ready
        String expectedPin = "1234"; // Simulated PIN
        orderEntity.get().setPin(expectedPin);

        UserEntity client = new UserEntity();
        client.setPhone("31049225805"); // Simulated client phone number
        when(externalApiConsumption.getRolByIdUser(33, auth)).thenReturn(client);

        // Act
        String result = orderAdapter.readyToDelivery(1, id, orderRequest, auth);

        // Assert
        Assertions.assertNotNull(result);
    }

    @Test
    void deliveryOrder_SuccessfulDelivery() {
        // Arrange
        int idAuthenticated = 1;
        int id = 2;
        String auth = "authToken";
        OrderStateModificationDTO orderRequest = new OrderStateModificationDTO();
        orderRequest.setPin("1234");

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setPin("1234");
        orderEntity.setState("listo");

        when(orderRepositoryJPA.findById(id)).thenReturn(Optional.of(orderEntity));

        // Act
        String result = orderAdapter.deliveryOrder(idAuthenticated, id, orderRequest, auth);

        // Assert
        verify(orderRepositoryJPA).findById(id);
        verify(orderRepositoryJPA).save(orderEntity);
        assertEquals("El estado de producto ya fue cambiado a entregado", result);
        assertEquals("entregado", orderEntity.getState());
    }

    @Test
    void deliveryOrder_PinNotValid() {
        // Arrange
        int idAuthenticated = 1;
        int id = 2;
        String auth = "authToken";
        OrderStateModificationDTO orderRequest = new OrderStateModificationDTO();
        orderRequest.setPin("0000"); // PIN incorrecto

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setPin("1234");
        orderEntity.setState("listo");

        when(orderRepositoryJPA.findById(id)).thenReturn(Optional.of(orderEntity));

        // Act & Assert
        assertThrows(ProductNotFount.class, () -> {
            orderAdapter.deliveryOrder(idAuthenticated, id, orderRequest, auth);
        });
        verify(orderRepositoryJPA).findById(id);
        verify(orderRepositoryJPA, never()).save(orderEntity);
    }

    @Test
    void deliveryOrder_OrderNotInReadyState() {
        // Arrange
        int idAuthenticated = 1;
        int id = 2;
        String auth = "authToken";
        OrderStateModificationDTO orderRequest = new OrderStateModificationDTO();
        orderRequest.setPin("1234");

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setPin("1234");
        orderEntity.setState("preparando"); // La orden no está lista

        when(orderRepositoryJPA.findById(id)).thenReturn(Optional.of(orderEntity));

        // Act
        String result = orderAdapter.deliveryOrder(idAuthenticated, id, orderRequest, auth);

        // Assert
        verify(orderRepositoryJPA).findById(id);
        verify(orderRepositoryJPA, never()).save(orderEntity);
        assertEquals(STAGE_PRODUCT_NOT_PREPARING, result);
    }

    @Test
    void cancelOrder_SuccessfulCancellation() {
        // Arrange
        int idAuthenticated = 1;
        int id = 2;

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUserId(idAuthenticated);
        orderEntity.setState("pendiente");

        when(orderRepositoryJPA.findById(id)).thenReturn(Optional.of(orderEntity));

        // Act
        String result = orderAdapter.cancelOrder(idAuthenticated, id);

        // Assert
        verify(orderRepositoryJPA).findById(id);
        verify(orderRepositoryJPA).delete(orderEntity);
        assertEquals("Su orden fue cancelada correctamente", result);
    }


    @Test
    void cancelOrder_InvalidUser() {
        // Arrange
        int idAuthenticated = 1;
        int id = 2;

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUserId(3); // Usuario diferente al autenticado
        orderEntity.setState("pendiente");

        when(orderRepositoryJPA.findById(id)).thenReturn(Optional.of(orderEntity));

        // Act & Assert
        assertThrows(ProductNotFount.class, () -> {
            orderAdapter.cancelOrder(idAuthenticated, id);
        });
        verify(orderRepositoryJPA).findById(id);
        verify(orderRepositoryJPA, never()).delete(orderEntity);
    }

    @Test
    void cancelOrder_OrderInProcess() {
        // Arrange
        int idAuthenticated = 1;
        int id = 2;

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUserId(idAuthenticated);
        orderEntity.setState("preparando"); // La orden no está en estado "pendiente"

        when(orderRepositoryJPA.findById(id)).thenReturn(Optional.of(orderEntity));

        // Act
        String result = orderAdapter.cancelOrder(idAuthenticated, id);

        // Assert
        verify(orderRepositoryJPA).findById(id);
        verify(orderRepositoryJPA, never()).delete(orderEntity);
        assertEquals("Lo sentimos, tu pedido ya está en preparación y no puede cancelarse", result);
    }
}