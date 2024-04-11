package plazoleta.adapters.driven.jpa.msql.adapter;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import plazoleta.adapters.driven.jpa.msql.entity.plate.PlateEntity;
import plazoleta.adapters.driven.jpa.msql.exception.ErrorBaseDatos;
import plazoleta.adapters.driven.jpa.msql.exception.ProductNotFount;
import plazoleta.adapters.driven.jpa.msql.mapper.IPlateEntityMapper;
import plazoleta.adapters.driven.jpa.msql.repository.IPlateRepositoryJPA;
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
    public Plate update(Plate plate, int id) {
        try {
            Optional<PlateEntity> plateEntityUpdateOptional = plateRepositoryJPA.filteByID(id);
            PlateEntity plateEntityUpdate = plateEntityUpdateOptional.get();
            plateEntityUpdate.setPrice(plate.getPrice());
            plateEntityUpdate.setDescription(plate.getDescription());
            return plateEntityMapper.toPlate(plateRepositoryJPA.save(plateEntityUpdate));
        } catch (Exception e) {
            throw new ProductNotFount("Producto no encontrado en la base de datos");
        }
    }

}
