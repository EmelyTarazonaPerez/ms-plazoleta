package plazoleta.adapters.driven.jpa.msql.adapter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import plazoleta.adapters.driven.jpa.msql.entity.plate.PlateEntity;
import plazoleta.adapters.driven.jpa.msql.entity.restaurant.RestaurantEntity;
import plazoleta.adapters.driven.jpa.msql.entity.restaurant.UserEntity;
import plazoleta.adapters.driven.jpa.msql.mapper.IPlateEntityMapper;
import plazoleta.adapters.driven.jpa.msql.repository.IPlateRepositoryJPA;
import plazoleta.domain.model.plate.Category;
import plazoleta.domain.model.plate.Plate;
import plazoleta.domain.model.restaurant.Restaurant;
import plazoleta.domain.model.restaurant.User;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlateAdapterTest {

    @Mock
    private IPlateEntityMapper plateEntityMapper;
    @Mock
    private IPlateRepositoryJPA plateRepositoryJPA;
    @InjectMocks
    private PlateAdapter plateAdapter;

    @BeforeEach
    void setUp() {
    }

    @Test
    void save() {
        Restaurant restaurant = new Restaurant(1,"rest", "any",
                new User(), "+573104922805", "logo", "ni"
        );
        Plate plateInput =
                new Plate(1, "Hamburguesa",
                new Category(), "Deliciosa hamburguesa",
                1500, restaurant, "https://ejemplo.com/hamburguesa.jpg");
        PlateEntity plateEntityMapped = new PlateEntity();

        when(plateEntityMapper.toPlateEntity(plateInput)).thenReturn(plateEntityMapped);
        when(plateRepositoryJPA.save(plateEntityMapped)).thenReturn(plateEntityMapped);
        when(plateEntityMapper.toPlate(plateEntityMapped)).thenReturn(plateInput);

        Plate savedPlate = plateAdapter.save(plateInput);

        // Verificar que se llamó correctamente a los métodos de mapeo y al repository
        verify(plateEntityMapper).toPlateEntity(plateInput);
        verify(plateRepositoryJPA).save(plateEntityMapped);
        assertEquals(plateInput, savedPlate);
    }
}