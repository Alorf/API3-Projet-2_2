package be.condorcet.api3projet2_2.services.location;

import be.condorcet.api3projet2_2.entities.Client;
import be.condorcet.api3projet2_2.entities.Location;
import be.condorcet.api3projet2_2.services.InterfService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public interface InterfLocationService extends InterfService<Location> {
    List<Location> read(Client cl) throws Exception;

    List<Location> read(LocalDate date) throws Exception;

    List<Location> locationEntreDeuxDates(LocalDate date1, LocalDate date2) throws Exception;
}
