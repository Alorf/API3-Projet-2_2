package be.condorcet.api3projet2_2.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "API_LOCATION", schema = "ORA6", catalog = "OCRL.CONDORCET.BE")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location_generator")
    @SequenceGenerator(name = "location_generator", sequenceName = "API_LOCATION_SEQ", allocationSize = 1)
    @Column(name = "ID_LOCATION")
    private Integer id;


    @NonNull
    @Column(name = "DATELOC")
    private LocalDate dateloc;


    @NonNull
    @Column(name = "KMTOTAL")
    private Integer kmTotal;


    @NonNull
    @ManyToOne @JoinColumn(name = "ID_CLIENT")
    private Client client;


    @NonNull
    @ManyToOne @JoinColumn(name = "ID_ADRESSE")
    private Adresse adrDepart;

    @ToString.Exclude
    @OneToMany(mappedBy = "location")
    List<Facture> facturations;

    public Location(Integer id) {
        this.id = id;
    }
}
