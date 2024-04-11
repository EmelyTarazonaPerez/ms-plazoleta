package plazoleta.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import plazoleta.adapters.driven.jpa.msql.adapter.PlateAdapter;
import plazoleta.adapters.driven.jpa.msql.adapter.RestaurantAdapter;
import plazoleta.adapters.driven.jpa.msql.mapper.IPlateEntityMapper;
import plazoleta.adapters.driven.jpa.msql.mapper.IRestaurantEntityMapper;
import plazoleta.adapters.driven.jpa.msql.repository.IPlateRepositoryJPA;
import plazoleta.adapters.driven.jpa.msql.repository.IRestaurantRepositoryJPA;
import plazoleta.domain.api.IPlateServicePort;
import plazoleta.domain.api.IRestaurantServicePort;
import plazoleta.domain.api.useCase.PlateCaseUse;
import plazoleta.domain.api.useCase.RestaurantCase;
import plazoleta.domain.spi.IPlatePersistencePort;
import plazoleta.domain.spi.IRestaurantPersistencePort;


@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IRestaurantRepositoryJPA restaurantRepositoryJPA;
    private final IRestaurantEntityMapper restaurantEntityMapper;

    private final IPlateEntityMapper plateEntityMapper;
    private final IPlateRepositoryJPA plateRepositoryJPA;

    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort(){
        return new RestaurantAdapter(restaurantRepositoryJPA, restaurantEntityMapper);
    }
    @Bean
    public IRestaurantServicePort userServicePort(){ return new RestaurantCase(restaurantPersistencePort());
    }

    @Bean
    public IPlatePersistencePort platePersistencePort(){
        return new PlateAdapter(plateEntityMapper,plateRepositoryJPA );
    }
    @Bean
    public IPlateServicePort plateServicePort(){ return new PlateCaseUse(platePersistencePort());
    }

}
