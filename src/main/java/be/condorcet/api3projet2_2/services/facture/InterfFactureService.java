package be.condorcet.api3projet2_2.services.facture;

import be.condorcet.api3projet2_2.entities.Facture;
import be.condorcet.api3projet2_2.entities.FactureKey;
import be.condorcet.api3projet2_2.entities.Location;
import be.condorcet.api3projet2_2.entities.Taxi;
import be.condorcet.api3projet2_2.services.InterfService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface InterfFactureService extends InterfService<Facture> {

    List<Facture> read(Taxi taxi) throws  Exception;

    List<Facture> read(Location loc) throws  Exception;

    Facture read(FactureKey id) throws Exception;

    Page<Facture> allp(Pageable pageable, Location loc) throws Exception;

    BigDecimal sumCoutByLocation(Location location);


}
