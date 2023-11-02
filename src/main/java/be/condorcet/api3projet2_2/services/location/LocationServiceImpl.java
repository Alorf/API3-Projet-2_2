package be.condorcet.api3projet2_2.services.location;


import be.condorcet.api3projet2_2.entities.Client;
import be.condorcet.api3projet2_2.entities.Location;
import be.condorcet.api3projet2_2.repositories.ClientRepository;
import be.condorcet.api3projet2_2.repositories.LocationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(rollbackOn = Exception.class)
public class LocationServiceImpl implements InterfLocationService {
    @Autowired
    private LocationRepository locationRepository;

    @Override
    public Location create(Location location) throws Exception {
        locationRepository.save(location);
        return location;
    }

    @Override
    public Location read(Integer id) throws Exception {
        return locationRepository.findById(id).get();
    }

    @Override
    public Location update(Location location) throws Exception {
        read(location.getId());
        locationRepository.save(location);
        return location;
    }

    @Override
    public void delete(Location location) throws Exception {
        locationRepository.deleteById(location.getId());
    }

    @Override
    public List<Location> all() throws Exception {
        return locationRepository.findAll();
    }

    @Override
    public List<Location> read(Client cl) throws Exception{
        List<Location> lcf = locationRepository.findLocationByClient(cl);
        return lcf;
    }

    @Override
    public List<Location> read(LocalDate date) throws Exception{
        return locationRepository.findLocationByDateloc(date);
    }

    @Override
    public List<Location> locationEntreDeuxDates(Client client, LocalDate d1, LocalDate d2) {
        return locationRepository.findLocationsByClientAndDatelocBetween(client, d1, d2);
    }

}
