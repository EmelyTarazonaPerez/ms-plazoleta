package plazoleta.adapters.driven.jpa.msql.utils.consumer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import plazoleta.adapters.driven.jpa.msql.entity.UserEntity;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class TraceabilityData {
    private UserEntity user;
    private String previousState;
    private String stateNew;
    private UserEntity employeer;
    private LocalDate date;
    private int idOrder;
}
