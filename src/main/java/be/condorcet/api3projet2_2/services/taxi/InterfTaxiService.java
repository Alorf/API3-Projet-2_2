package be.condorcet.api3projet2_2.services.taxi;

import be.condorcet.api3projet2_2.entities.Client;
import be.condorcet.api3projet2_2.entities.Location;
import be.condorcet.api3projet2_2.entities.Taxi;
import be.condorcet.api3projet2_2.services.InterfService;

import java.util.List;

public interface InterfTaxiService extends InterfService<Taxi> {

    List<Taxi> read(String carburant) throws Exception;

    Taxi readImmatriculation(String immatriculation);

    List<Taxi> allNotInLoc(Location loc);
}
