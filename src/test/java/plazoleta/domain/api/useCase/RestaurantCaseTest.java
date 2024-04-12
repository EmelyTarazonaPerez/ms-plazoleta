package plazoleta.domain.api.useCase;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import plazoleta.domain.model.restaurant.Restaurant;
import plazoleta.domain.model.restaurant.User;
import plazoleta.domain.spi.IRestaurantPersistencePort;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantCaseTest {

    @Mock
    IRestaurantPersistencePort restaurantPersistencePort;
    @InjectMocks
    RestaurantCase restaurantCase;
    @BeforeEach
    void setUp() {
    }

    @Test
    void createRestaurant() {

        Restaurant restaurant = new Restaurant(1,"rest", "any",
                1, "+573104922805", "logo", "ni"
        );
        when(restaurantPersistencePort.save(restaurant)).thenReturn(restaurant);

        final Restaurant result = restaurantCase.createRestaurant(restaurant);
        Assertions.assertEquals(result, restaurant);

    }

    @Test
    void getAll() {
        List<Restaurant> restaurants = new ArrayList<>();

        when(restaurantPersistencePort.getAll(0,5,true)).thenReturn(restaurants);

        final List<Restaurant> result = restaurantCase.getAll(0, 5, true);
        Assertions.assertEquals(result, restaurants);

    }
}