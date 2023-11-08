package be.condorcet.api3projet2_2.services.client;

import be.condorcet.api3projet2_2.entities.Adresse;
import be.condorcet.api3projet2_2.entities.Client;
import be.condorcet.api3projet2_2.entities.Location;
import be.condorcet.api3projet2_2.entities.Taxi;
import be.condorcet.api3projet2_2.services.InterfService;
import org.springframework.data.repository.query.Param;


import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface InterfClientService extends InterfService<Client> {
    List<Client> read(String nom) throws Exception;

    Client read(String nom, String prenom, String tel) throws Exception;

    List<Taxi> taxiUtiliseSansDoublon(Integer idClient) throws Exception;

    List<Adresse> adresseLocationSansDoublon(Integer idClient) throws Exception;
}
