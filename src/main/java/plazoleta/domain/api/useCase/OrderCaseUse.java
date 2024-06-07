package plazoleta.domain.api.useCase;

import org.aspectj.weaver.ast.Or;
import plazoleta.adapters.driven.jpa.msql.entity.OrderEntity;
import plazoleta.adapters.driven.jpa.msql.entity.UserEntity;
import plazoleta.adapters.driven.jpa.msql.exception.ErrorAccessModified;
import plazoleta.adapters.driven.jpa.msql.exception.ProductNotFount;
import plazoleta.adapters.driving.http.dto.request.order.AddOrderRequest;
import plazoleta.adapters.driving.http.dto.request.order.OrderStateModificationDTO;
import plazoleta.adapters.driving.http.dto.response.UserDto;
import plazoleta.domain.api.IOrderServicePort;
import plazoleta.domain.exception.ExceptionValid;
import plazoleta.domain.model.pedido.Order;
import plazoleta.domain.model.pedido.OrderPlate;
import plazoleta.domain.model.restaurant.Restaurant;
import plazoleta.domain.model.restaurant.User;
import plazoleta.domain.spi.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static plazoleta.adapters.driven.utils.constants.ConstanstUtils.*;
import static plazoleta.config.Constants.MESSAGE_ORDER_PROCESS;
import static plazoleta.domain.utils.ConstantsDomain.*;

public class OrderCaseUse implements IOrderServicePort {
    private final IOrderPersistencePort orderPersistencePort;
    private final IExternalPersistenceApi externalApiConsumption;
    private final ISecurityPersistencePort securityPersistencePort;
    private final ITwilioPersistencePort twilioPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IOrderPlatePersistencePort orderPlatePersistencePort;

    public OrderCaseUse(IOrderPersistencePort orderPersistencePort,
                        IExternalPersistenceApi externalApiConsumption,
                        ISecurityPersistencePort securityPersistencePort,
                        ITwilioPersistencePort twilioPersistencePort,
                        IRestaurantPersistencePort restaurantPersistencePort,
                        IOrderPlatePersistencePort orderPlatePersistencePort
    ) {
        this.twilioPersistencePort = twilioPersistencePort;
        this.orderPersistencePort = orderPersistencePort;
        this.externalApiConsumption = externalApiConsumption;
        this.securityPersistencePort = securityPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.orderPlatePersistencePort = orderPlatePersistencePort;
    }

    @Override
    public Order create(Order order, int idAuthUser, User infoChef, String auth) {

        if (idAuthUser != order.getUserId()) throw new ExceptionValid(USER_IS_DIFFERENT_THAN_LOGEDIN);
        order.setChefId(0);
        isSameRestaurant(order);
        Order result = orderPersistencePort.save(order);
        orderPlatePersistencePort.saveOrderPlate(result);
        return result;
    }

    @Override
    public List<Order> getOrderByState(String state, int page, int size, int idAuthenticated) {
        Restaurant restaurant = restaurantPersistencePort.getRestaurantByEmployee(idAuthenticated);

        List<Order> getAllOrder = orderPersistencePort.getOrderByState(state, page, size);
        return getAllOrder.stream().filter(order -> order.getRestaurantId() == restaurant.getId()).toList();

    }

    @Override
    public Order takeOrder(int idAuthenticated, int idOrder, AddOrderRequest orderRequest) {
        Order order = orderPersistencePort.getOrderById(idOrder);
        order.setChefId(idAuthenticated);
        updateOrderState(order, orderRequest.getState());
        return orderPersistencePort.takeOrder(order);
    }

    @Override
    public String readyToDelivery(int idAuthenticated, int id, AddOrderRequest orderRequest, String auth) {
        Order order = orderPersistencePort.getOrderById(id);

        if (!isOrderInProcess(order, "preparando")) return STAGE_PRODUCT_NOT_PREPARING;

        updateOrderState(order, orderRequest.getState());

        if (Objects.equals(order.getState(), "listo")) {
            User clientDetails = getClientDetails(order, auth);
            String pin = securityPersistencePort.generateSecurityPin();
            order.setPin(pin);
            sendMessageClient(clientDetails, pin);
            return orderPersistencePort.readyToDelivery(order);

        } else  {
            throw new ExceptionValid("Error");
        }

    }

    @Override
    public String deliveryOrder(int idAuthenticated, int id, OrderStateModificationDTO orderRequest, String auth) {
        Order order = orderPersistencePort.getOrderById(id);
        if (!isOrderInProcess(order, "listo")) return STAGE_PRODUCT_NOT_READY;

        String pinBD = order.getPin();
        String customerPin = orderRequest.getPin();

        isValidPin(pinBD, customerPin);
        updateOrderState(order, "entregado");

         return orderPersistencePort.deliveryOrder(order);
    }

    @Override
    public String cancelOrder(int idAuthenticated, int id) {
        Order order = orderPersistencePort.getOrderById(id);

        if (order.getUserId() != idAuthenticated) throw new ProductNotFount(MESSAGE_ORDER_PROCESS);

        if (!isOrderInProcess(order, "pendiente")) return PRODUCT_NOT_CANCEL;

        return orderPersistencePort.cancelOrder(order);
    }

    //METODOS

    private boolean isOrderInProcess(Order order, String process) {
        return Objects.equals(order.getState(), process);
    }

    private User getClientDetails(Order order, String auth) {
        return externalApiConsumption.getRolByIdUser(order.getUserId(), auth);
    }

    private void updateOrderState(Order order, String newState) {
        order.setState(newState);
    }

    private void sendMessageClient(User clientDetails, String pin) {
        String phoneClient = clientDetails.getPhone();
        twilioPersistencePort.sendMSM(phoneClient, pin);
    }

    private void isValidPin(String pinBD, String customerPin) {
        if (!Objects.equals(pinBD, customerPin)) {
            throw new ErrorAccessModified(PIN_INCORRECT);
        }
    }

    private void isSameRestaurant (Order order) {
        int idRestaurant = order.getRestaurantId();
        List<OrderPlate> listaPlatos = order.getListPlates();

        boolean mismoIdRestaurante = true;
        for (OrderPlate plato : listaPlatos) {
            if (plato.getPlate().getRestaurantId() != idRestaurant) {
                mismoIdRestaurante = false;
                break;
            }
        }

        if (!mismoIdRestaurante) throw new ExceptionValid(PLATE_NOT_SAME_RESTAURANT);
    }

}
