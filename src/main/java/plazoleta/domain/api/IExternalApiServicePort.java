package plazoleta.domain.api;

import plazoleta.adapters.driven.jpa.msql.entity.UserEntity;
import plazoleta.domain.model.restaurant.User;

import java.time.LocalDate;

public interface IExternalApiServicePort {
    public User getRolByIdUser (int id, String token);

    public void sendTraceability (
            User client,
            String previousState,
            String stateNew,
            User employeer,
            LocalDate date,
            int idOrder);
}
