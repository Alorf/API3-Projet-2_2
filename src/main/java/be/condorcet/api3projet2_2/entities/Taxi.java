package be.condorcet.api3projet2_2.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "API_TAXI", schema = "ORA6", catalog = "OCRL.CONDORCET.BE")
public class Taxi {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "taxi_generator")
    @SequenceGenerator(name = "taxi_generator", sequenceName = "API_TAXI_SEQ", allocationSize = 1)
    @Column(name = "ID_TAXI")
    private Integer id;
    private String immatriculation;
    private String carburant;

    @Column(name = "PRIXKM")
    private Double prixKm;

    public Taxi(Integer id) {
        this.id = id;
    }
}
