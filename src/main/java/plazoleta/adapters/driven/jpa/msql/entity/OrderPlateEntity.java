package plazoleta.adapters.driven.jpa.msql.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "pedido_plate")
@AllArgsConstructor
@RequiredArgsConstructor
public class OrderPlateEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
/*
    @Id
    private Long idOrder;

    @Id
    private Long idPlate; */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido")
    private OrderEntity order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_plate")
    private PlateEntity plate;

    private int amount;
}
