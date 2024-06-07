package plazoleta.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import plazoleta.adapters.driven.jpa.msql.adapter.OrderAdapter;
import plazoleta.adapters.driven.jpa.msql.adapter.OrderPlateAdapter;
import plazoleta.adapters.driven.jpa.msql.adapter.PlateAdapter;
import plazoleta.adapters.driven.jpa.msql.adapter.RestaurantAdapter;
import plazoleta.adapters.driven.jpa.msql.mapper.IOrderEntityMapper;
import plazoleta.adapters.driven.jpa.msql.mapper.IPlateEntityMapper;
import plazoleta.adapters.driven.jpa.msql.mapper.IRestaurantEntityMapper;
import plazoleta.adapters.driven.jpa.msql.repository.IOrderPlateRepositoryJPA;
import plazoleta.adapters.driven.jpa.msql.repository.IOrderRepositoryJPA;
import plazoleta.adapters.driven.jpa.msql.repository.IPlateRepositoryJPA;
import plazoleta.adapters.driven.jpa.msql.repository.IRestaurantRepositoryJPA;
import plazoleta.adapters.driven.utils.consumer.ExternalAdapterApi;
import plazoleta.adapters.driven.security.SecurityAdapter;
import plazoleta.adapters.driven.utils.twilio.MessageAdapterImpl;
import plazoleta.domain.api.IExternalApiServicePort;
import plazoleta.domain.api.IOrderServicePort;
import plazoleta.domain.api.IPlateServicePort;
import plazoleta.domain.api.IRestaurantServicePort;
import plazoleta.domain.api.useCase.ExternalCaseUse;
import plazoleta.domain.api.useCase.OrderCaseUse;
import plazoleta.domain.api.useCase.PlateCaseUse;
import plazoleta.domain.api.useCase.RestaurantCase;
import plazoleta.domain.spi.*;


@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IRestaurantRepositoryJPA restaurantRepositoryJPA;
    private final IRestaurantEntityMapper restaurantEntityMapper;

    private final IPlateEntityMapper plateEntityMapper;
    private final IPlateRepositoryJPA plateRepositoryJPA;

    private final IOrderRepositoryJPA orderRepositoryJPA;
    private final IOrderEntityMapper orderEntityMapper;

    private final IOrderPlateRepositoryJPA orderPlateRepositoryJPA;

    private final RestTemplate restTemplate;


    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort(){
        return new RestaurantAdapter(restaurantRepositoryJPA, restaurantEntityMapper);
    }
    @Bean
    public IRestaurantServicePort userServicePort(){ return new RestaurantCase(restaurantPersistencePort());
    }

    @Bean
    public IPlatePersistencePort platePersistencePort(){
        return new PlateAdapter(plateEntityMapper, plateRepositoryJPA);
    }
    @Bean
    public IPlateServicePort plateServicePort(){ return new PlateCaseUse(platePersistencePort(), restaurantPersistencePort());
    }
    @Bean
    public IOrderPersistencePort persistencePort () {
        return new OrderAdapter(
                orderRepositoryJPA, orderEntityMapper);
    }
    @Bean
    public IExternalPersistenceApi externalPersistenceApi() {
        return new ExternalAdapterApi(restTemplate);
    }

    @Bean
    public ISecurityPersistencePort securityPersistencePort() {
        return new SecurityAdapter();
    }

    @Bean
    public IMessagePersistencePort twilioPersistencePort() { return new MessageAdapterImpl(); }

    @Bean
    public IOrderPlatePersistencePort orderPlatePersistencePort() { return new OrderPlateAdapter(orderPlateRepositoryJPA,orderEntityMapper ); }
    @Bean
    public IExternalApiServicePort externalApiServicePort () {
        return new ExternalCaseUse(externalPersistenceApi());
    }
    @Bean
    public IOrderServicePort orderServicePort () {
        return new OrderCaseUse(
                persistencePort(),
                externalPersistenceApi(),
                securityPersistencePort(),
                twilioPersistencePort(),
                restaurantPersistencePort(),
                orderPlatePersistencePort()
                );
    }
}
