package be.condorcet.api3projet2_2.repositories;

import be.condorcet.api3projet2_2.entities.Taxi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaxiRepository extends JpaRepository<Taxi, Integer> {

    List<Taxi> findByCarburant(String carburant);

    Taxi findByImmatriculation(String immatriculation);

    //SELECT * FROM API_TAXI WHERE ID_TAXI NOT IN (SELECT ID_TAXI FROM API_FACTURE WHERE ID_LOCATION = 8)
    @Query(value = "select t from Taxi t where t.id not in(select f.taxi.id from Facture f where f.location.id = :idlocation)")
    List<Taxi> findAllByNotInLocation(@Param("idlocation") Integer idlocation);


}
