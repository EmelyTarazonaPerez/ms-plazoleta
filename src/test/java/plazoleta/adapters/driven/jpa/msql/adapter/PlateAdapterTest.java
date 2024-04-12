package plazoleta.adapters.driven.jpa.msql.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import plazoleta.adapters.driven.jpa.msql.entity.plate.CategoryEntity;
import plazoleta.adapters.driven.jpa.msql.entity.plate.PlateEntity;
import plazoleta.adapters.driven.jpa.msql.entity.restaurant.RestaurantEntity;
import plazoleta.adapters.driven.jpa.msql.exception.ProductNotFount;
import plazoleta.adapters.driven.jpa.msql.mapper.IPlateEntityMapper;
import plazoleta.adapters.driven.jpa.msql.repository.IPlateRepositoryJPA;
import plazoleta.adapters.driven.jpa.msql.repository.IRestaurantRepositoryJPA;
import plazoleta.domain.model.plate.Category;
import plazoleta.domain.model.plate.Plate;

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
    @Mock
    private IRestaurantRepositoryJPA restaurantRepositoryJPA;

    @Mock
    private PlateAdapter plateAdapterM;
    @InjectMocks
    private PlateAdapter plateAdapter;

    private Plate plateInput;

    @BeforeEach
    void setUp() {
        plateInput =
                new Plate(30, "Hamburguesa",
                        new Category(), "Deliciosa hamburguesa",
                        1500, 6, "https://ejemplo.com/hamburguesa.jpg");
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
        int id = 30;
        int idAuthenticate = 25;
        PlateEntity plateEntity = new PlateEntity(30, "Hamburguesa",
                new CategoryEntity(), "Deliciosa hamburguesa",
                1500, 6, "https://ejemplo.com/hamburguesa.jpg", true);

        RestaurantEntity restaurantEntity = new RestaurantEntity(6,"rest", "any",
                25, "+573104922805", "logo", "ni"
        );

        when(restaurantRepositoryJPA.findById(6)).thenReturn(Optional.of(restaurantEntity));
        when(plateRepositoryJPA.findById(id)).thenReturn(Optional.of(plateEntity));
        when(plateRepositoryJPA.save(any(PlateEntity.class))).thenReturn(plateEntity);
        when(plateEntityMapper.toPlate(plateEntity)).thenReturn(plateInput);

        // Act
        Plate updatedPlate = plateAdapter.update(plateInput, id, idAuthenticate);

        // Assert
        assertNotNull(updatedPlate);
        // Add more assertions as needed
    }

    @Test
    void testUpdatePlate_ProductNotFound() {
        // Arrange
        int id = 50000;
        // Act & Assert
        assertThrows(ProductNotFount.class, () -> plateAdapter.update(plateInput, id, 1));
    }

    @Test
    void accessModifi() {
        RestaurantEntity restaurantEntity = new RestaurantEntity(6,"rest", "any",
                25, "+573104922805", "logo", "ni"
        );
        when(restaurantRepositoryJPA.findById(6)).thenReturn(Optional.of(restaurantEntity));
        // Act
        Boolean updatedPlate = plateAdapter.accessModifi(plateInput, 25);
        assertNotNull(updatedPlate);
    }

    @Test
    void accessModify_Error() {
        when(restaurantRepositoryJPA.findById(6)).thenReturn(Optional.empty());
        // Act
        assertThrows(ProductNotFount.class, () -> plateAdapter.update(plateInput, 30, 1));
    }


}