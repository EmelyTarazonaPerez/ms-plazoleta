package plazoleta.adapters.driven.jpa.msql.adapter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import plazoleta.adapters.driven.jpa.msql.entity.RestaurantEntity;
import plazoleta.adapters.driven.jpa.msql.entity.RolEntity;
import plazoleta.adapters.driven.jpa.msql.entity.UserEntity;
import plazoleta.adapters.driven.jpa.msql.mapper.IRestaurantEntityMapper;
import plazoleta.adapters.driven.jpa.msql.repository.IRestaurantRepositoryJPA;
import plazoleta.domain.model.Restaurant;
import plazoleta.domain.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantAdapterTest {

    @Mock
    IRestaurantRepositoryJPA restaurantRepositoryJPA;
    @Mock
    private IRestaurantEntityMapper restaurantEntityMapper;
    @InjectMocks
    private RestaurantAdapter restaurantAdapter;

    @BeforeEach
    void setUp() {
    }

    @Test
    void save() {
        Restaurant restaurant = new Restaurant(1,"rest", "any",
                new User(), "+573104922805", "logo", "ni"
        );
        RestaurantEntity restaurantEntity = new RestaurantEntity();

        when(restaurantEntityMapper.toRestaurantEntity(restaurant)).thenReturn(restaurantEntity);
        when(restaurantRepositoryJPA.save(restaurantEntity)).thenReturn(restaurantEntity);
        when(restaurantEntityMapper.toRestaurant(restaurantEntity)).thenReturn(restaurant);

        final Restaurant result = restaurantAdapter.save(restaurant);

        Assertions.assertEquals(result,restaurant);
    }
}