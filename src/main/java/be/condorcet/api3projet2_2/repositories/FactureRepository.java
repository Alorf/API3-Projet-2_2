package be.condorcet.api3projet2_2.repositories;

import be.condorcet.api3projet2_2.entities.Facture;
import be.condorcet.api3projet2_2.entities.Taxi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FactureRepository extends JpaRepository<Facture, Integer> {

    List<Facture> findByTaxi(Taxi taxi);

}
