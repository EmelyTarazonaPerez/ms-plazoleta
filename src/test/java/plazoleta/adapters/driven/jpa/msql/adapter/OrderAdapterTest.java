package plazoleta.adapters.driven.jpa.msql.adapter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import plazoleta.adapters.driven.jpa.msql.entity.order.OrderEntity;
import plazoleta.adapters.driven.jpa.msql.exception.ErrorAccessModifi;
import plazoleta.adapters.driven.jpa.msql.exception.ErrorBaseDatos;
import plazoleta.adapters.driven.jpa.msql.mapper.IOrderEntityMapper;
import plazoleta.adapters.driven.jpa.msql.repository.IOrderRepositoryJPA;
import plazoleta.domain.model.pedido.Order;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderAdapterTest {

    @Mock
    private IOrderRepositoryJPA orderRepositoryJPA;
    @Mock
    private IOrderEntityMapper orderEntityMapper;
    @InjectMocks
    private OrderAdapter orderAdapter;
    @BeforeEach
    void setUp() {
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
        Order order = new Order( 1, 33, LocalDate.now(), "pendiente", 26, 6, null);
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
}