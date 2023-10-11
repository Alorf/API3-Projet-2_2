package be.condorcet.api3projet2_2.model;

import lombok.Data;

import java.util.Objects;

@Data
public class Taxi {

    private int id;
    private String immatriculation;
    private String carburant;
    private double prixKm;

    public Taxi(int id, String immatriculation, String carburant, double prixKm) {
        this.id = id;
        this.immatriculation = immatriculation;
        this.carburant = carburant;
        this.prixKm = prixKm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Taxi taxi = (Taxi) o;
        return Objects.equals(immatriculation, taxi.immatriculation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(immatriculation);
    }

    @Override
    public String toString() {
        return "Taxi{" +
                "id=" + id +
                ", immatriculation='" + immatriculation + '\'' +
                ", carburant='" + carburant + '\'' +
                ", prixKm=" + prixKm +
                '}';
    }
}
