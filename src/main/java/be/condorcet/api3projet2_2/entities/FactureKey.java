package be.condorcet.api3projet2_2.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class FactureKey implements Serializable {

    @Column(name = "ID_LOCATION")
    private Integer idLocation;

    @Column(name = "ID_TAXI")
    private Integer idTaxi;

}
