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
import plazoleta.adapters.driven.jpa.msql.exception.ErrorAccessModifi;
import plazoleta.adapters.driven.jpa.msql.exception.ErrorBaseDatos;
import plazoleta.adapters.driven.jpa.msql.mapper.IOrderEntityMapper;
import plazoleta.adapters.driven.jpa.msql.repository.IOrderRepositoryJPA;
import plazoleta.adapters.driven.jpa.msql.repository.IRestaurantRepositoryJPA;
import plazoleta.domain.model.pedido.Order;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderAdapterTest {

    @Mock
    private IOrderRepositoryJPA orderRepositoryJPA;
    @Mock
    private IOrderEntityMapper orderEntityMapper;

    @Mock
    private IRestaurantRepositoryJPA restaurantRepositoryJPA;
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
        assertThrows(ErrorAccessModifi.class, () -> orderAdapter.save(order));
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
}