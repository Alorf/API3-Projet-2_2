package be.condorcet.api3projet2_2.repositories;

import be.condorcet.api3projet2_2.entities.Adresse;
import be.condorcet.api3projet2_2.entities.Client;
import be.condorcet.api3projet2_2.entities.Location;
import be.condorcet.api3projet2_2.entities.Taxi;
import jakarta.persistence.SqlResultSetMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    Client findByNomAndPrenomAndTel(String nom, String prenom, String tel);

    @Query(value = "SELECT DISTINCT t\n" +
            "FROM Taxi t\n" +
            "JOIN Facture f ON t.id = f.taxi.id\n" +
            "JOIN Location l ON l.id = f.location.id\n" +
            "JOIN Client c ON c.id = l.client.id\n" +
            "WHERE c.id = :idclient\n" +
            "ORDER BY t.immatriculation ASC")
    List<Taxi> taxiUtiliseSansDoublon(@Param("idclient") Integer idClient);


    @Query(value = "SELECT DISTINCT a\n" +
            "FROM Adresse a\n" +
            "         JOIN Location l on l.adrDepart.id = a.id\n" +
            "WHERE l.client.id = :idclient")
    List<Adresse> adresseLocationSansDoublon(@Param("idclient") Integer idClient);

}
