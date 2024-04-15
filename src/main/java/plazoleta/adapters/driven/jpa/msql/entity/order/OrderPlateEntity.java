package plazoleta.adapters.driven.jpa.msql.entity.order;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import plazoleta.adapters.driven.jpa.msql.entity.plate.PlateEntity;

@Entity
@Getter
@Setter
@Table(name = "pedido_plate")
@AllArgsConstructor
@RequiredArgsConstructor
public class OrderPlateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "id_plate")
    private PlateEntity plate;

    @Column(name = "amount")
    private int amount;
}
