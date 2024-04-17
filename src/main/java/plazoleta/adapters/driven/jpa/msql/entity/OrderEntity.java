package plazoleta.adapters.driven.jpa.msql.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "pedido")
@AllArgsConstructor
@RequiredArgsConstructor
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private int id;

    @Column(name = "id_usuario")
    private int userId;

    @Column(name = "fecha")
    private LocalDate date;

    @Column(name = "estado")
    private String state;

    @Column(name = "id_chef")
    private Integer chefId;

    @Column(name = "id_restaurant")
    private int restaurantId;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order")
    private List<OrderPlateEntity> orderPlateList= new ArrayList<>();

    private String pin;

    public void setChefId(Integer chefId) {
        if (chefId == null) {
            this.chefId = 0;
        } else {
            this.chefId = chefId;

        }
    }
}
