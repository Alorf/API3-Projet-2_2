package be.condorcet.api3projet2_2.services.facture;

import be.condorcet.api3projet2_2.entities.Facture;
import be.condorcet.api3projet2_2.entities.FactureKey;
import be.condorcet.api3projet2_2.entities.Taxi;
import be.condorcet.api3projet2_2.services.InterfService;

import java.util.List;

public interface InterfFactureService extends InterfService<Facture> {

    List<Facture> read(Taxi taxi) throws  Exception;

    Facture read(FactureKey id) throws Exception;

}
