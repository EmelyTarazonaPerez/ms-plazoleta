package plazoleta.adapters.driven.jpa.msql.adapter;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import plazoleta.adapters.driven.jpa.msql.mapper.IPlateEntityMapper;
import plazoleta.adapters.driven.jpa.msql.repository.IPlateRepositoryJPA;
import plazoleta.domain.model.plate.Plate;
import plazoleta.domain.spi.IPlatePersistencePort;

@Service
@AllArgsConstructor
public class PlateAdapter implements IPlatePersistencePort {

    private final IPlateEntityMapper plateEntityMapper;
    private final IPlateRepositoryJPA plateRepositoryJPA;

        @Override
        public Plate save(Plate plate) {
            plate.setActive(true);
            return plateEntityMapper.toPlate(plateRepositoryJPA.save(
                    plateEntityMapper.toPlateEntity(plate)
            ));
        }
}
