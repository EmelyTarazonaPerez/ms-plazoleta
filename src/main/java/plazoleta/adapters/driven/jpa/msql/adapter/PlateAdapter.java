package plazoleta.adapters.driven.jpa.msql.adapter;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import plazoleta.adapters.driven.jpa.msql.entity.plate.PlateEntity;
import plazoleta.adapters.driven.jpa.msql.entity.restaurant.RestaurantEntity;
import plazoleta.adapters.driven.jpa.msql.exception.ErrorAccessModified;
import plazoleta.adapters.driven.jpa.msql.exception.ErrorBaseDatos;
import plazoleta.adapters.driven.jpa.msql.exception.ProductNotFount;
import plazoleta.adapters.driven.jpa.msql.mapper.IPlateEntityMapper;
import plazoleta.adapters.driven.jpa.msql.repository.IPlateRepositoryJPA;
import plazoleta.adapters.driven.jpa.msql.repository.IRestaurantRepositoryJPA;
import plazoleta.domain.model.plate.Plate;
import plazoleta.domain.spi.IPlatePersistencePort;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static plazoleta.adapters.driven.jpa.msql.utils.DataOrdering.getOrdering;

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
        if (accessModified(plate, idAutenticado)) {
            throw new ErrorAccessModified("Solo el propietario del restaurante puede modificar");
        }
        try {
            PlateEntity plateEntity = plateRepositoryJPA.findById(id).get();

            plateEntity.setPrice(plate.getPrice());
            plateEntity.setDescription(plate.getDescription());
            plateEntity.setActive(plate.isActive());

            return plateEntityMapper.toPlate(plateRepositoryJPA.save(plateEntity));
        } catch (Exception e) {
            throw new ProductNotFount("Producto no encontrado en la base de datos");
        }
    }

    @Override
    public List<Plate> get(int page, int size, int category, int restaurant) {
       if (category == 0) { Pageable pageable = getOrdering(page, size,false, "name");
          return plateEntityMapper.toPlateList(plateRepositoryJPA.findByRestaurantId(restaurant, pageable));
       }
       return plateEntityMapper.toPlateList(plateRepositoryJPA
                       .findByRestaurantId(restaurant)
                       .stream().filter(plate -> plate.getCategoryId() == category)
                       .collect(Collectors.toList()));
    }

    public boolean accessModified(Plate plate, int idAuthenticated) {
        Optional<RestaurantEntity> restaurantOptional = restaurantRepositoryJPA.findById(plate.getRestaurantId());
        if (restaurantOptional.isPresent()) {
            RestaurantEntity restaurant = restaurantOptional.get();
            return restaurant.getOwnerId() != idAuthenticated;
        } else {
            throw new ErrorBaseDatos("El restaurante " + plate.getRestaurantId() + " no está registrado en la base de datos");
        }
    }
}
