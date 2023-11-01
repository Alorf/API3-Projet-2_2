package be.condorcet.api3projet2_2.services.adresse;

import be.condorcet.api3projet2_2.entities.Adresse;
import be.condorcet.api3projet2_2.entities.Client;
import be.condorcet.api3projet2_2.services.InterfService;

import java.util.List;

public interface InterfAdresseService extends InterfService<Adresse> {

    List<Adresse> read(String localite) throws Exception;

}
