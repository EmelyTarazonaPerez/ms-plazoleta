package plazoleta.domain.api.useCase;

import plazoleta.adapters.driven.jpa.msql.entity.UserEntity;
import plazoleta.domain.api.IExternalApiServicePort;
import plazoleta.domain.model.restaurant.User;
import plazoleta.domain.spi.IExternalPersistenceApi;

import java.time.LocalDate;

public class ExternalCaseUse implements IExternalApiServicePort {

    private final IExternalPersistenceApi externalPersistenceApi;

    public ExternalCaseUse (IExternalPersistenceApi externalPersistenceApi) {
        this.externalPersistenceApi = externalPersistenceApi;
    }


    @Override
    public User getRolByIdUser(int id, String token) {
        return externalPersistenceApi.getRolByIdUser(id, token);
    }

    @Override
    public void sendTraceability(User client, String previousState, String stateNew, User employeer, LocalDate date, int idOrder) {
        externalPersistenceApi.sendTraceability(
                client,
                previousState,
                stateNew,
                employeer,
                date,
                idOrder);
    }
}
