package plazoleta.adapters.driven.jpa.msql.adapter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators;
import plazoleta.adapters.driven.jpa.msql.entity.plate.PlateEntity;
import plazoleta.adapters.driven.jpa.msql.entity.restaurant.RestaurantEntity;
import plazoleta.adapters.driven.jpa.msql.entity.restaurant.UserEntity;
import plazoleta.adapters.driven.jpa.msql.exception.ProductNotFount;
import plazoleta.adapters.driven.jpa.msql.mapper.IPlateEntityMapper;
import plazoleta.adapters.driven.jpa.msql.repository.IPlateRepositoryJPA;
import plazoleta.domain.model.plate.Category;
import plazoleta.domain.model.plate.Plate;
import plazoleta.domain.model.restaurant.Restaurant;
import plazoleta.domain.model.restaurant.User;

import java.util.Optional;

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

    private Plate plateInput;

    @BeforeEach
    void setUp() {
        plateInput =
                new Plate(1, "Hamburguesa",
                        new Category(), "Deliciosa hamburguesa",
                        1500, 1, "https://ejemplo.com/hamburguesa.jpg");
    }

    @Test
    void save() {

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

    @Test
    void testUpdatePlate_Success() {
        // Arrange
        int id = 1;
        plateInput.setPrice(10);
        plateInput.setDescription("New description");

        PlateEntity plateEntity = new PlateEntity();
        plateEntity.setId(id);

        Optional<PlateEntity> plateEntityOptional = Optional.of(plateEntity);

        when(plateRepositoryJPA.filteByID(id)).thenReturn(plateEntityOptional);
        when(plateRepositoryJPA.save(any(PlateEntity.class))).thenReturn(plateEntity);
        when(plateEntityMapper.toPlate(any(PlateEntity.class))).thenReturn(plateInput);

        // Act
        Plate result = plateAdapter.update(plateInput, id);

        // Assert
        assertNotNull(result);
        assertEquals(plateInput.getPrice(), result.getPrice());
        assertEquals(plateInput.getDescription(), result.getDescription());
    }

    @Test
    void testUpdatePlate_ProductNotFound() {
        // Arrange
        int id = 50000;
        Optional<PlateEntity> plateEntityOptional = Optional.empty();
        when(plateRepositoryJPA.filteByID(id)).thenReturn(plateEntityOptional);

        // Act & Assert
        assertThrows(ProductNotFount.class, () -> plateAdapter.update(plateInput, id));
    }
}