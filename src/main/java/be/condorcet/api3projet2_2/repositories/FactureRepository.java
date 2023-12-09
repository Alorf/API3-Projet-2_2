package be.condorcet.api3projet2_2.repositories;

import be.condorcet.api3projet2_2.entities.Facture;
import be.condorcet.api3projet2_2.entities.FactureKey;
import be.condorcet.api3projet2_2.entities.Location;
import be.condorcet.api3projet2_2.entities.Taxi;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface FactureRepository extends JpaRepository<Facture, FactureKey> {

    List<Facture> findByTaxi(Taxi taxi);

    List<Facture> findByLocation(Location location);

    Page<Facture> findAllByLocation(Location loc, Pageable pageable);

    Facture findFactureByLocationAndTaxiAndCout(@NonNull Location location, @NonNull Taxi taxi, BigDecimal cout);
}
