package plazoleta.adapters.driven.jpa.msql.adapter;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import plazoleta.adapters.driven.jpa.msql.entity.plate.PlateEntity;
import plazoleta.adapters.driven.jpa.msql.entity.restaurant.RestaurantEntity;
import plazoleta.adapters.driven.jpa.msql.exception.ErrorAccessModifi;
import plazoleta.adapters.driven.jpa.msql.exception.ErrorBaseDatos;
import plazoleta.adapters.driven.jpa.msql.exception.ProductNotFount;
import plazoleta.adapters.driven.jpa.msql.mapper.IPlateEntityMapper;
import plazoleta.adapters.driven.jpa.msql.repository.IPlateRepositoryJPA;
import plazoleta.adapters.driven.jpa.msql.repository.IRestaurantRepositoryJPA;
import plazoleta.domain.model.plate.Plate;
import plazoleta.domain.spi.IPlatePersistencePort;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PlateAdapter implements IPlatePersistencePort {

    private final IPlateEntityMapper plateEntityMapper;
    private final IPlateRepositoryJPA plateRepositoryJPA;
    private final IRestaurantRepositoryJPA restaurantRepositoryJPA;

    @Override
    public Plate save(Plate plate) {
        try {
            plate.setActive(true);
            return plateEntityMapper.toPlate(plateRepositoryJPA.save(
                    plateEntityMapper.toPlateEntity(plate)
            ));
        } catch (Exception e) {
            throw new ErrorBaseDatos("No se pudo crear producto verifique informacion");
        }

    }

    @Override
    public Plate update(Plate plate, int id, int idAutenticado) {
        try {
            if (accessModifi(plate, idAutenticado)) {
                throw new ErrorAccessModifi("Solo el propietario del restaurante puede modificar");
            }

            PlateEntity plateEntity = plateRepositoryJPA.findById(id).get();

            plateEntity.setPrice(plate.getPrice());
            plateEntity.setDescription(plate.getDescription());
            plateEntity.setActive(plate.isActive());

            return plateEntityMapper.toPlate(plateRepositoryJPA.save(plateEntity));
        } catch (Exception e) {
            throw new ProductNotFount("Producto no encontrado en la base de datos");
        }
    }

    public boolean accessModifi(Plate plate, int idAutenticado) {
        Optional<RestaurantEntity> restaurantOptional = restaurantRepositoryJPA.findById(plate.getRestaurantId());
        if (restaurantOptional.isPresent()) {
            RestaurantEntity restaurant = restaurantOptional.get();
            return restaurant.getOwnerId() != idAutenticado;
        } else {
            throw new ErrorBaseDatos("El restaurante " + plate.getRestaurantId() + " no está registrado en la base de datos");
        }
    }
}
