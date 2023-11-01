package be.condorcet.api3projet2_2.repositories;

import be.condorcet.api3projet2_2.entities.Adresse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdresseRepository extends JpaRepository<Adresse, Integer> {

    List<Adresse> findByLocalite(String localite);

}
