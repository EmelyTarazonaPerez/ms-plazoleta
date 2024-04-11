package plazoleta.domain.api.useCase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import plazoleta.domain.model.plate.Category;
import plazoleta.domain.model.plate.Plate;
import plazoleta.domain.model.restaurant.Restaurant;
import plazoleta.domain.model.restaurant.User;
import plazoleta.domain.spi.IPlatePersistencePort;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class PlateCaseUseTest {

    @Mock
    IPlatePersistencePort platePersistencePort;

    @InjectMocks
    PlateCaseUse plateCaseUse;

    @Test
    void create() {
        Restaurant restaurant = new Restaurant(1, "rest", "any",
                new User(), "+573104922805", "logo", "ni"
        );
        Plate plate = new Plate(1, "Hamburguesa",
                new Category(), "Deliciosa hamburguesa",
                1500, restaurant, "https://ejemplo.com/hamburguesa.jpg");

        when(platePersistencePort.save(plate)).thenReturn(plate);

        Plate createdPlate = plateCaseUse.create(plate);

        verify(platePersistencePort).save(plate);
        assertEquals(plate, createdPlate);
    }
}