package plazoleta.domain.spi;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import plazoleta.adapters.driven.jpa.msql.entity.UserEntity;
import plazoleta.adapters.driven.utils.consumer.TraceabilityData;
import plazoleta.domain.model.pedido.Order;
import plazoleta.domain.model.restaurant.User;

import java.time.LocalDate;

public interface IExternalPersistenceApi {
    public User getRolByIdUser (int id, String token);

    public void sendTraceability (
            User client,
            String previousState,
            String stateNew,
            User employeer,
            LocalDate date,
            int idOrder);

}
