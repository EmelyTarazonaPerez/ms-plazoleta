package plazoleta.adapters.driven.jpa.msql.adapter;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import plazoleta.adapters.driven.jpa.msql.entity.PlateEntity;
import plazoleta.adapters.driven.jpa.msql.entity.RestaurantEntity;
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

import static plazoleta.adapters.driven.utils.DataOrdering.getOrdering;
import static plazoleta.adapters.driven.utils.constants.ConstanstUtils.ERROR_BASE_DATOS;
import static plazoleta.adapters.driven.utils.constants.ConstanstUtils.PRODUCT_NOT_FOUNT;

@Service
@AllArgsConstructor
public class PlateAdapter implements IPlatePersistencePort {

    private final IPlateEntityMapper plateEntityMapper;
    private final IPlateRepositoryJPA plateRepositoryJPA;

    @Override
    public Plate save(Plate plate) {
        try {
            plate.setActive(true);
            return plateEntityMapper.toPlate(plateRepositoryJPA.save(
                    plateEntityMapper.toPlateEntity(plate)
            ));
        } catch (Exception e) {
            throw new ErrorBaseDatos(ERROR_BASE_DATOS);
        }

    }

    @Override
    public Plate update(Plate plate, int id) {
        try {
            PlateEntity plateEntity = plateRepositoryJPA.findById(id).get();

            plateEntity.setPrice(plate.getPrice());
            plateEntity.setDescription(plate.getDescription());
            plateEntity.setActive(plate.isActive());

            return plateEntityMapper.toPlate(plateRepositoryJPA.save(plateEntity));
        } catch (Exception e) {
            throw new ProductNotFount(PRODUCT_NOT_FOUNT);
        }
    }

    @Override
    public List<Plate> get(int page, int size, int category, int restaurant) {
        if (category == 0) {
            Pageable pageable = getOrdering(page, size, false, "name");
            return plateEntityMapper.toPlateList(plateRepositoryJPA.findByRestaurantId(restaurant, pageable));
        }
        return plateEntityMapper.toPlateList(plateRepositoryJPA
                .findByRestaurantId(restaurant)
                .stream().filter(plate -> plate.getCategoryId() == category)
                .collect(Collectors.toList()));
    }

}
