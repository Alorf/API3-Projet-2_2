package be.condorcet.api3projet2_2.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "API_FACTURE", schema = "ORA6", catalog = "OCRL.CONDORCET.BE")
public class Facture {

    //https://www.baeldung.com/jpa-many-to-many#many-to-many-using-a-composite-key

    @EmbeddedId
    @NonNull
    private FactureKey id;

    @ManyToOne
    @NonNull
    @MapsId("idLocation")
    @JoinColumn(name = "ID_LOCATION")
    private Location location;

    @ManyToOne
    @NonNull
    @MapsId("idTaxi")
    @JoinColumn(name = "ID_TAXI")
    private Taxi taxi;

    //Sans les joinColumn erreur de mapping

    private BigDecimal cout;

    public Facture(@NonNull Location location, @NonNull Taxi taxi, BigDecimal cout) {
        this.id = new FactureKey(location.getId(), taxi.getId());
        this.location = location;
        this.taxi = taxi;
        this.cout = cout;
    }
}
