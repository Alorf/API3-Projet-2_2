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

}
