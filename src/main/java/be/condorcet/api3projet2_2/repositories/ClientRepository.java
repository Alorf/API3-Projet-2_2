package be.condorcet.api3projet2_2.repositories;

import be.condorcet.api3projet2_2.entities.Adresse;
import be.condorcet.api3projet2_2.entities.Client;
import be.condorcet.api3projet2_2.entities.Location;
import be.condorcet.api3projet2_2.entities.Taxi;
import jakarta.persistence.SqlResultSetMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    List<Client> findByNomLike(String nom);

    //Opérations spéciales

    /*
    @Query(value = "SELECT t.id, t.immatriculation, t.carburant, t.prixKm\n" +
            "FROM Client c\n" +
            "         JOIN Location l ON l.id = c.id\n" +
            "         JOIN Facture f ON f.location.id = l.id\n" +
            "         JOIN Taxi t ON t.id = f.taxi.id\n" +
            "WHERE c.id=:idclient")
    List<Taxi> taxiUtiliseSansDoublon(@Param("idclient") Integer idClient);
    //Failed to convert from type [java.lang.Object[]] to type [be.condorcet.api3projet2_2.entities.Taxi] for value [{...}]

    @Query(value = "SELECT DISTINCT API_ADRESSE.ID_ADRESSE, CP, LOCALITE, RUE, NUM FROM API_ADRESSE JOIN API_LOCATION AL on API_ADRESSE.ID_ADRESSE = AL.ID_ADRESSE WHERE ID_CLIENT = :idclient", nativeQuery = true)
    List<Adresse> adresseLocationSansDoublon(@Param("idclient") Integer idClient);
    //Failed to convert from type [java.lang.Object[]] to type [be.condorcet.api3projet2_2.entities.Adresse] for value [{...}]
    */
}
