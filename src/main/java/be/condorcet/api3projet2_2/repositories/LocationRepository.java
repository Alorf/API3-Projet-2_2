package be.condorcet.api3projet2_2.repositories;

import be.condorcet.api3projet2_2.entities.Adresse;
import be.condorcet.api3projet2_2.entities.Client;
import be.condorcet.api3projet2_2.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {
    List<Location> findLocationByClient(Client client);

    List<Location> findLocationByDateloc(LocalDate date);

    List<Location> findLocationsByClientAndDatelocBetween(Client client, LocalDate d1, LocalDate d2);

}
