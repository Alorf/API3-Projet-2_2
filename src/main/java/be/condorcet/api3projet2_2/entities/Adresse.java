package be.condorcet.api3projet2_2.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "API_ADRESSE", schema = "ORA6", catalog = "OCRL.CONDORCET.BE")
public class Adresse {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adresse_generator")
    @SequenceGenerator(name = "adresse_generator", sequenceName = "API_ADRESSE_SEQ", allocationSize = 1)
    @Column(name = "ID_ADRESSE")
    private Integer id;

    private Integer cp;

    private String localite;

    private String rue;

    private String num;
}
