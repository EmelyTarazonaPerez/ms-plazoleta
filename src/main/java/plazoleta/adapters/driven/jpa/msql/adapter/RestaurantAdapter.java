package plazoleta.adapters.driven.jpa.msql.adapter;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import plazoleta.adapters.driven.jpa.msql.mapper.IRestaurantEntityMapper;
import plazoleta.adapters.driven.jpa.msql.repository.IRestaurantRepositoryJPA;
import plazoleta.domain.model.Restaurant;
import plazoleta.domain.spi.IRestaurantPersistencePort;

@Service
@AllArgsConstructor
public class RestaurantAdapter implements IRestaurantPersistencePort {

    private IRestaurantRepositoryJPA restaurantRepositoryJPA;
    private IRestaurantEntityMapper restaurantEntityMapper;
    @Override
    public Restaurant save(Restaurant restaurant) {
        return restaurantEntityMapper.toRestaurant(restaurantRepositoryJPA
                .save(restaurantEntityMapper.toRestaurantEntity(restaurant)));
    }



}
