package plazoleta.adapters.driven.jpa.msql.adapter;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import plazoleta.adapters.driven.jpa.msql.entity.OrderEntity;
import plazoleta.adapters.driven.jpa.msql.entity.OrderPlateEntity;
import plazoleta.adapters.driven.jpa.msql.entity.RestaurantEntity;
import plazoleta.adapters.driven.jpa.msql.entity.UserEntity;
import plazoleta.adapters.driven.jpa.msql.exception.ErrorAccessModified;
import plazoleta.adapters.driven.jpa.msql.exception.ProductNotFount;
import plazoleta.adapters.driven.jpa.msql.mapper.IOrderEntityMapper;
import plazoleta.adapters.driven.jpa.msql.repository.IOrderPlateRepositoryJPA;
import plazoleta.adapters.driven.jpa.msql.repository.IOrderRepositoryJPA;
import plazoleta.adapters.driven.jpa.msql.repository.IRestaurantRepositoryJPA;
import plazoleta.adapters.driven.jpa.msql.utils.consumer.ExternalApiConsumption;
import plazoleta.adapters.driving.http.dto.request.order.AddOrderRequest;
import plazoleta.adapters.driving.http.dto.request.order.OrderStateModificationDTO;
import plazoleta.domain.model.pedido.Order;
import plazoleta.domain.spi.IOrderPersistencePort;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static plazoleta.adapters.driven.jpa.msql.utils.DataOrdering.getOrdering;
import static plazoleta.adapters.driven.jpa.msql.utils.constants.ConstanstUtils.*;
import static plazoleta.adapters.driven.jpa.msql.utils.security.SecurityUtilsImp.generateSecurityPin;
import static plazoleta.adapters.driven.jpa.msql.utils.twilio.TwilioServiceImpl.sendMSM;

@Service
@AllArgsConstructor
public class OrderAdapter implements IOrderPersistencePort {
    private final IOrderRepositoryJPA orderRepositoryJPA;
    private final IOrderEntityMapper orderEntityMapper;

    private final ExternalApiConsumption externalApiConsumption;
    private final IOrderPlateRepositoryJPA orderPlateRepositoryJPA;
    private final IRestaurantRepositoryJPA restaurantRepositoryJPA;

    public void saveOrderPlate(OrderEntity order) {
        List<OrderPlateEntity> listOrderPlates = order.getOrderPlateList();
        for (OrderPlateEntity item : listOrderPlates) {
            item.setOrder(order);
            orderPlateRepositoryJPA.save(item);
        }

    }

    @Override
    public Order save(Order order, UserEntity infoChef, String token) {
        order.setState("pendiente");
        if (validOrderUser(order.getUserId())) {
            throw new ErrorAccessModified(MESSAGE_ORDER_PROCESS);
        }
        OrderEntity orderEntityNew = orderEntityMapper.toOrderEntity(order);
        OrderEntity result = orderRepositoryJPA.save(orderEntityNew);

        saveOrderPlate(result);
        return orderEntityMapper.toOrder(result);
    }

    public boolean validOrderUser(int id) {
        Optional<OrderEntity> orderByUser = orderRepositoryJPA.findByUserId(id);
        return orderByUser.isPresent() && !Objects.equals(orderByUser.get().getState(), "entregado");
    }

    @Override
    public List<Order> getOrderByState(String state, int page, int size, int idAuthenticated) {
        Pageable pageable = getOrdering(page, size, true, "date");
        Optional<RestaurantEntity> restaurant = restaurantRepositoryJPA.findByOwnerId(idAuthenticated);
        if (restaurant.isEmpty()) {
            throw new ProductNotFount(NO_FOUNT_RESTAURANT);
        }
        List<Order> getAllOrder = orderEntityMapper.toOrderList(orderRepositoryJPA.findByState(state, pageable));
        return getAllOrder.stream().filter(order -> order.getRestaurantId() == restaurant.get().getId()).toList();
    }

    @Override
    public Order takeOrder(int idAuthenticated, int idOrder, AddOrderRequest orderRequest) {
        Optional<OrderEntity> orderEntity = getOrderById(idOrder);
        if (orderEntity.isEmpty()) {
            throw new ProductNotFount(NO_FOUNT_ORDER);
        }
        orderEntity.get().setChefId(idAuthenticated);
        orderEntity.get().setState(orderRequest.getState());


        return orderEntityMapper.toOrder(orderRepositoryJPA.save(orderEntity.get()));
    }

    @Override
    public String readyToDelivery(int idAuthenticated, int id, AddOrderRequest orderRequest, String auth) {
        try {
            Optional<OrderEntity> orderEntity = getOrderById(id);
            if (!isOrderInProcess(orderEntity, "preparando")) {
                return STAGE_PRODUCT_NOT_PREPARING;
            }

            UserEntity client = getClientDetails(orderEntity, auth);
            updateOrderState(orderEntity, orderRequest.getState());

            if (Objects.equals(orderEntity.get().getState(), "listo")) {
                String pin = generateSecurityPin();
                orderEntity.get().setPin(pin);
                orderRepositoryJPA.save(orderEntity.get());
                return sendMSM(client.getPhone(), pin);
            } else {
                return STAGE_PRODUCT_NOT_READY;
            }
        } catch (ProductNotFount e) {
            throw new ProductNotFount(NO_FOUNT_ORDER);
        }
    }

    @Override
    public String deliveryOrder(int idAuthenticated, int id, OrderStateModificationDTO orderRequest, String auth) {
        try {
            Optional<OrderEntity> orderEntity = getOrderById(id);
            if (!isOrderInProcess(orderEntity, "listo")) {
                return STAGE_PRODUCT_NOT_PREPARING;
            }
            String pinBD = orderEntity.get().getPin();
            String customerPin = orderRequest.getPin();

            isValidPin(pinBD, customerPin);
            updateOrderState(orderEntity, "entregado");
            orderRepositoryJPA.save(orderEntity.get());
            return "El estado de producto ya fue cambiado a entregado";
        } catch (Exception e) {
            throw new ProductNotFount("Message" + e);
        }
    }

    @Override
    public String cancelOrder(int idAuthenticated, int id) {
        Optional<OrderEntity> orderEntity = getOrderById(id);
        if (orderEntity.get().getUserId() != idAuthenticated) {
            throw new ProductNotFount("El usuario no tiene una orden con ese id");
        }
        if (!isOrderInProcess(orderEntity, "pendiente")) {
            return "Lo sentimos, tu pedido ya está en preparación y no puede cancelarse";
        }
        orderRepositoryJPA.delete(orderEntity.get());
        return "Su orden fue cancelada correctamente";
    }


    private Optional<OrderEntity> getOrderById(int id) {
        return orderRepositoryJPA.findById(id);
    }

    private boolean isOrderInProcess(Optional<OrderEntity> orderEntity, String process) {
        return orderEntity.isPresent() && Objects.equals(orderEntity.get().getState(), process);
    }

    private UserEntity getClientDetails(Optional<OrderEntity> orderEntity, String auth) {
        return externalApiConsumption.getRolByIdUser(orderEntity.get().getUserId(), auth);
    }

    private void updateOrderState(Optional<OrderEntity> orderEntity, String newState) {
        orderEntity.ifPresent(order -> order.setState(newState));
    }

    private void isValidPin(String pinBD, String customerPin) {
        if (!Objects.equals(pinBD, customerPin)) {
            throw new ErrorAccessModified("El pin proporcionado no es el mismo asociado a la order");
        }
    }

    private void sentTrazabilidad(
            OrderEntity orderEntity,
            String inputPreviousState,
            String inputStateNew,
            String auth,
            UserEntity inputEmployeer
    ) {
        //Client
        UserEntity client = getClientDetails(Optional.of(orderEntity), auth);

        externalApiConsumption.
                sendTraceability(
                        client,
                        inputPreviousState,
                        inputStateNew,
                        inputEmployeer,
                        orderEntity.getDate(),
                        orderEntity.getId());
    }

}
