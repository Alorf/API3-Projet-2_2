package be.condorcet.api3projet2_2.model;

import lombok.Data;

@Data
public class Facturation {

    private double cout;

    private Taxi vehicule;

    public Facturation(double cout, Taxi vehicule) {
        this.cout = cout;
        this.vehicule = vehicule;
    }

    @Override
    public String toString() {
        return "Facturation{" +
                "cout=" + cout +
                ", vehicule=" + vehicule +
                '}';
    }
}
