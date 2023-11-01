package be.condorcet.api3projet2_2.repositories;

import be.condorcet.api3projet2_2.entities.Adresse;
import be.condorcet.api3projet2_2.entities.Client;
import be.condorcet.api3projet2_2.entities.Location;
import be.condorcet.api3projet2_2.entities.Taxi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    List<Client> findByNomLike(String nom);

    //Opérations spéciales
    /*
    @Query(value = "SELECT ID_TAXI, IMMATRICULATION, CARBURANT, PRIXKM FROM API_TAXIPARCLIENT TAXPARCLI JOIN API_TAXI TAX ON TAXPARCLI.TAXI = TAX.IMMATRICULATION WHERE ID_CLIENT=:idclient", nativeQuery = true)
    List<Taxi> taxiUtiliseSansDoublon(@Param("idclient") Integer idClient);

    @Query(value = "SELECT DISTINCT API_ADRESSE.ID_ADRESSE, CP, LOCALITE, RUE, NUM FROM API_ADRESSE JOIN API_LOCATION AL on API_ADRESSE.ID_ADRESSE = AL.ID_ADRESSE WHERE ID_CLIENT = :idclient", nativeQuery = true)
    List<Adresse> adresseLocationSansDoublon(@Param("idclient") Integer idClient);
    */
}
