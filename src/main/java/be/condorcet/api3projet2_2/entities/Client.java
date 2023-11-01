package be.condorcet.api3projet2_2.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "API_TCLIENT", schema = "ORA6", catalog = "OCRL.CONDORCET.BE")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_generator")
    @SequenceGenerator(name = "client_generator", sequenceName = "API_TCLIENT_SEQ", allocationSize = 1)
    @Column(name = "ID_CLIENT")
    private Integer id;

    private String mail;

    private String nom;

    private String prenom;

    private String tel;

    @OneToMany(mappedBy = "client")
    @ToString.Exclude
    private List<Location> locations = new ArrayList<>();

    public List<Taxi> taxiUtiliseSansDoublon(){

        List<Taxi> lt = new ArrayList<>();
        for (Location loc : locations){
            for (Facture fac : loc.getFacturations()){
                if (!lt.contains(fac.getTaxi())){
                    lt.add(fac.getTaxi());
                }
            }
        }

        return lt;
    }

    public List<Location> locationEntreDeuxDates(LocalDate d1, LocalDate d2){
        List<Location> ll = new ArrayList<>();

        LocalDate dmax = d1.isAfter(d2) ? d1 : d2;
        LocalDate dmin = d1.isBefore(d2) ? d1 : d2;

        for(Location loc : locations){
            if (loc.getDateloc().isAfter(dmin.minusDays(1)) && loc.getDateloc().isBefore(dmax.plusDays(1))){
                ll.add(loc);
            }
        }

        return ll;
    }

    public List<Adresse> adresseLocationSansDoublon(){
        List<Adresse> la = new ArrayList<>();
        for (Location loc : locations){
            if (!la.contains(loc.getAdrDepart())) {
                la.add(loc.getAdrDepart());
            }
        }

        return la;
    }
}
