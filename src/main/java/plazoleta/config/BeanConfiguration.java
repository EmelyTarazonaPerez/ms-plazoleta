package plazoleta.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import plazoleta.adapters.driven.jpa.msql.adapter.OrderAdapter;
import plazoleta.adapters.driven.jpa.msql.adapter.PlateAdapter;
import plazoleta.adapters.driven.jpa.msql.adapter.RestaurantAdapter;
import plazoleta.adapters.driven.jpa.msql.mapper.IOrderEntityMapper;
import plazoleta.adapters.driven.jpa.msql.mapper.IPlateEntityMapper;
import plazoleta.adapters.driven.jpa.msql.mapper.IRestaurantEntityMapper;
import plazoleta.adapters.driven.jpa.msql.repository.IOrderRepositoryJPA;
import plazoleta.adapters.driven.jpa.msql.repository.IPlateRepositoryJPA;
import plazoleta.adapters.driven.jpa.msql.repository.IRestaurantRepositoryJPA;
import plazoleta.domain.api.IOrderServicePort;
import plazoleta.domain.api.IPlateServicePort;
import plazoleta.domain.api.IRestaurantServicePort;
import plazoleta.domain.api.useCase.OrderCaseUse;
import plazoleta.domain.api.useCase.PlateCaseUse;
import plazoleta.domain.api.useCase.RestaurantCase;
import plazoleta.domain.spi.IOrderPersistencePort;
import plazoleta.domain.spi.IPlatePersistencePort;
import plazoleta.domain.spi.IRestaurantPersistencePort;


@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IRestaurantRepositoryJPA restaurantRepositoryJPA;
    private final IRestaurantEntityMapper restaurantEntityMapper;

    private final IPlateEntityMapper plateEntityMapper;
    private final IPlateRepositoryJPA plateRepositoryJPA;

    private final IOrderRepositoryJPA orderRepositoryJPA;
    private final IOrderEntityMapper orderEntityMapper;

    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort(){
        return new RestaurantAdapter(restaurantRepositoryJPA, restaurantEntityMapper);
    }
    @Bean
    public IRestaurantServicePort userServicePort(){ return new RestaurantCase(restaurantPersistencePort());
    }

    @Bean
    public IPlatePersistencePort platePersistencePort(){
        return new PlateAdapter(plateEntityMapper, plateRepositoryJPA, restaurantRepositoryJPA );
    }
    @Bean
    public IPlateServicePort plateServicePort(){ return new PlateCaseUse(platePersistencePort());
    }
    @Bean
    public IOrderPersistencePort persistencePort () {
        return new OrderAdapter(orderRepositoryJPA, orderEntityMapper);
    }
    @Bean
    public IOrderServicePort orderServicePort () {
        return new OrderCaseUse(persistencePort());
    }
}
