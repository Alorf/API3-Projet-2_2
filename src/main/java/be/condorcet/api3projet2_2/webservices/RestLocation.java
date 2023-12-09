package be.condorcet.api3projet2_2.webservices;

import be.condorcet.api3projet2_2.entities.Client;
import be.condorcet.api3projet2_2.entities.Location;
import be.condorcet.api3projet2_2.services.client.InterfClientService;
import be.condorcet.api3projet2_2.services.location.InterfLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*", allowedHeaders = "*",exposedHeaders = "*") @RestController
@RequestMapping("/locations")
public class RestLocation {

    @Autowired
    private InterfLocationService locationServiceImpl;
    @Autowired
    private InterfClientService clientServiceImpl;

    //-------------------Retrouver la location correspondant à un n° donné--------------------------------------------------------
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Location> getLocation(@PathVariable(value = "id") int id) throws Exception {
        System.out.println("recherche de la location n° " + id);
        Location loc = locationServiceImpl.read(id);
        return new ResponseEntity<>(loc, HttpStatus.OK);
    }

    //-------------------Retrouver la location correspondant à un n° donné--------------------------------------------------------
    @RequestMapping(value = "/idclient={id}", method = RequestMethod.GET)
    public ResponseEntity<List<Location>> getLocationClient(@PathVariable(value = "id") int id) throws Exception {
        System.out.println("recherche des locations du client d'id " + id);
        Client cl = clientServiceImpl.read(id);
        List<Location> lloc = locationServiceImpl.read(cl);
        return new ResponseEntity<>(lloc, HttpStatus.OK);
    }

    //-------------------Créer une location--------------------------------------------------------
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Location> createLocation(@RequestBody Location loc) throws Exception {
        System.out.println("Création de la location du client " + loc.getClient());
        System.out.println(loc);
        locationServiceImpl.create(loc);
        return new ResponseEntity<>(loc, HttpStatus.OK);
    }

    //-------------------Mettre à jour une location d'un n° donné--------------------------------------------------------
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Location> majClient(@PathVariable(value = "id") int id, @RequestBody Location nouvloc) throws Exception {
        System.out.println("maj de la location n° " + id);
        nouvloc.setId(id);
        Location locact = locationServiceImpl.update(nouvloc);
        return new ResponseEntity<>(locact, HttpStatus.OK);
    }

    //-------------------Effacer une location d'un id donné--------------------------------------------------------
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteLocation(@PathVariable(value = "id") int id) throws Exception {
        System.out.println("effacement de la location n°" + id);
        Location loc = locationServiceImpl.read(id);
        locationServiceImpl.delete(loc);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //-------------------Retrouver toutes les locations --------------------------------------------------------
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Location>> listLocation() throws Exception {
        System.out.println("recherche de toutes les locations");
        return new ResponseEntity<>(locationServiceImpl.all(), HttpStatus.OK);
    }

    //-------------------Retrouver toutes les locations paginées------------------------------------------------
    @RequestMapping(value = "/allp",method = RequestMethod.GET)
    public ResponseEntity<Page<Location>> listLocation(Pageable pageable) throws Exception{
        System.out.println("recherche de toutes les locations");
        return new ResponseEntity<>(locationServiceImpl.allp(pageable), HttpStatus.OK);
    }


    //-------------------Gérer les erreurs--------------------------------------------------------
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Void> handleIOException(Exception ex) {
        System.out.println("erreur : " + ex.getMessage());
        return ResponseEntity.notFound().header("error", ex.getMessage()).build();
    }
}
