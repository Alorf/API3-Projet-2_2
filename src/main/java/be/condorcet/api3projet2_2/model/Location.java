package be.condorcet.api3projet2_2.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class Location {

    private int id;
    private LocalDate date;
    private int kmTotal;
    private Client client;
    private Adresse adrDepart;
    private List<Facturation> facturations = new ArrayList<>();

    public Location(int id, LocalDate date, int kmTotal, Client client, Adresse adrDepart) {
        this.id = id;
        this.date = date;
        this.kmTotal = kmTotal;
        this.client = client;
        this.adrDepart = adrDepart;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", date=" + date +
                ", kmTotal=" + kmTotal +
                ", client=" + client +
                ", adrDepart=" + adrDepart +
                ", facturations=" + facturations +
                '}';
    }

    public void setFacturation(List<Facturation> facs) {
        this.facturations = facs;
    }
}
