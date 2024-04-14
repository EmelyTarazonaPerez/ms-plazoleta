package plazoleta.domain.api.useCase;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class PlateCaseUseTest {

    @Mock
    IPlatePersistencePort platePersistencePort;

    @InjectMocks
    PlateCaseUse plateCaseUse;

    private Plate plate;

    @BeforeEach
    void setUp() {
        plate = new Plate(1, "Hamburguesa",
               1, "Deliciosa hamburguesa",
                1500, 1, "https://ejemplo.com/hamburguesa.jpg");
    }

    @Test
    void create() {
        when(platePersistencePort.save(plate)).thenReturn(plate);

        Plate createdPlate = plateCaseUse.create(plate);

        verify(platePersistencePort).save(plate);
        assertEquals(plate, createdPlate);
    }

    @Test
    void update() {
        when(platePersistencePort.update(plate, 15,1)).thenReturn(plate);
        Plate updatePlate = plateCaseUse.update(plate,15, 1);
        assertEquals(plate, updatePlate);
    }

    @Test
    void get() {
        List<Plate> plateList = new ArrayList<>();

        when(platePersistencePort.get(0, 5,0, 6)).thenReturn(plateList);
        List<Plate> getPlates = plateCaseUse.get(0, 5,0, 6);
        Assertions.assertNotNull(getPlates);
    }
}