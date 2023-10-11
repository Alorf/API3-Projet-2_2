package be.condorcet.api3projet2_2.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.*;

@Data
public class Client {

    private int id;
    private String mail;
    private String nom;
    private String prenom;
    private String tel;
    private List<Location> locations = new ArrayList<>();

    public Client(int id, String mail, String nom, String prenom, String tel) {
        this.id = id;
        this.mail = mail;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(mail, client.mail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mail);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", mail='" + mail + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }

    public Set<Taxi> taxiUtiliseSansDoublon(){

        Set<Taxi> lt = new HashSet<>();
        for (Location loc : locations){
            for (Facturation fac : loc.getFacturations()){
                //if (!lt.contains(fac.getVehicule())){
                    lt.add(fac.getVehicule());
                //}
            }
        }

        return lt;
    }

    public List<Location> locationEntreDeuxDates(LocalDate d1, LocalDate d2){
        List<Location> ll = new ArrayList<>();

        LocalDate dmax = d1.isAfter(d2) ? d1 : d2;
        LocalDate dmin = d1.isBefore(d2) ? d1 : d2;

        for(Location loc : locations){
            if (loc.getDate().isAfter(dmin.minusDays(1)) && loc.getDate().isBefore(dmax.plusDays(1))){
                ll.add(loc);
            }
        }

        return ll;
    }

    public Set<Adresse> adresseLocationSansDoublon(){
        Set<Adresse> la = new HashSet<>();
        for (Location loc : locations){
            //if (!la.contains(loc.getAdrDepart())){
                la.add(loc.getAdrDepart());
            //}
        }

        return la;
    }
}
