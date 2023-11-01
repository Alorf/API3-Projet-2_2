package be.condorcet.api3projet2_2.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class FactureKey implements Serializable {

    @Column(name = "ID_LOCATION")
    private Long idLocation;

    @Column(name = "ID_TAXI")
    private Long idTaxi;
}
