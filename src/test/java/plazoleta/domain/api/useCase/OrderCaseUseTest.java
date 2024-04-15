package plazoleta.domain.api.useCase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import plazoleta.adapters.driven.jpa.msql.entity.restaurant.RolEntity;
import plazoleta.adapters.driven.jpa.msql.entity.restaurant.UserEntity;
import plazoleta.domain.model.pedido.Order;
import plazoleta.domain.model.pedido.OrderPlate;
import plazoleta.domain.model.plate.Plate;
import plazoleta.domain.spi.IOrderPersistencePort;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderCaseUseTest {

    @Mock
    private IOrderPersistencePort orderPersistencePort;

    @InjectMocks
    private OrderCaseUse orderCaseUse;

    @Test
    void create() {
        // Datos de prueba
        Plate plate = new Plate(1, "any", 1,"any", 1000,6,null);
        OrderPlate orderPlate = new OrderPlate(1, plate, 5);

        List<OrderPlate> list = new ArrayList<>();
        list.add(orderPlate);
        Order order = new Order( 1, 33, LocalDate.now(), "pendiente", 26, 6, list);
        order.setUserId(33);
        order.setRestaurantId(6);


        int idAuthUser = 33;

        UserEntity infoChef = new UserEntity();
        infoChef.setIdRol(new RolEntity(3,"empleado", "any"));

        when(orderPersistencePort.save(order)).thenReturn(order);

        Order createdOrder = orderCaseUse.create(order, idAuthUser, infoChef);

        assertEquals(order, createdOrder);
    }
}