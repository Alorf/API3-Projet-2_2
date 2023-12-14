package be.condorcet.api3projet2_2.webservices;

import be.condorcet.api3projet2_2.entities.Facture;
import be.condorcet.api3projet2_2.entities.Location;
import be.condorcet.api3projet2_2.entities.Taxi;
import be.condorcet.api3projet2_2.services.location.InterfLocationService;
import be.condorcet.api3projet2_2.services.taxi.InterfTaxiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "*")
@RestController
@RequestMapping("/taxis")
public class RestTaxi {
    @Autowired
    private InterfTaxiService taxiServiceImpl;

    @Autowired
    private InterfLocationService locationServiceImpl;


    //-------------------Retrouver le taxi correspondant à un id donné--------------------------------------------------------
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Taxi> getTaxi(@PathVariable(value = "id") int id) throws Exception {
        System.out.println("recherche de le taxi avec l'id " + id);
        Taxi taxi = taxiServiceImpl.read(id);
        return new ResponseEntity<>(taxi, HttpStatus.OK);
    }

    //-------------------Retrouver les taxis portant un carburant donnée--------------------------------------------------------
    @RequestMapping(value = "/carburant={carburant}", method = RequestMethod.GET)
    public ResponseEntity<List<Taxi>> listTaxisCarburant(@PathVariable(value = "carburant") String carburant) throws Exception {
        System.out.println("recherche des taxi par le carburant " + carburant);
        List<Taxi> taxis;
        taxis = taxiServiceImpl.read(carburant);
        return new ResponseEntity<>(taxis, HttpStatus.OK);
    }

    //-------------------Retrouver le taxi portant une plaque donnée--------------------------------------------------------
    @RequestMapping(value = "/immatriculation={immatriculation}", method = RequestMethod.GET)
    public ResponseEntity<Taxi> getTaxiImmatriculation(@PathVariable(value = "immatriculation") String immatriculation) throws Exception {
        System.out.println("recherche du taxi par l'immatriculation " + immatriculation);
        Taxi taxi;
        taxi = taxiServiceImpl.readImmatriculation(immatriculation);
        return new ResponseEntity<>(taxi, HttpStatus.OK);
    }

    //-------------------Créer un taxi--------------------------------------------------------
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Taxi> createTaxi(@RequestBody Taxi taxi) throws Exception {
        System.out.println("Création de le taxi " + taxi);
        taxiServiceImpl.create(taxi);
        return new ResponseEntity<>(taxi, HttpStatus.OK);
    }

    //-------------------Mettre à jour un taxi avec un id donné--------------------------------------------------------
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Taxi> majTaxi(@PathVariable(value = "id") int id, @RequestBody Taxi nouvTaxi) throws Exception {
        System.out.println("maj de taxi id =  " + id);
        nouvTaxi.setId(id);
        Taxi taxi = taxiServiceImpl.update(nouvTaxi);
        return new ResponseEntity<>(taxi, HttpStatus.OK);
    }

    //-------------------Effacer un taxi avec un id donné--------------------------------------------------------
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteTaxi(@PathVariable(value = "id") int id) throws Exception {
        System.out.println("effacement du taxi d'id " + id);
        Taxi taxi = taxiServiceImpl.read(id);
        taxiServiceImpl.delete(taxi);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //-------------------Retrouver tous les taxis --------------------------------------------------------
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Taxi>> listTaxi() throws Exception {
        System.out.println("recherche de tous les taxis");
        return new ResponseEntity<>(taxiServiceImpl.all(), HttpStatus.OK);
    }

    //-------------------Retrouver tous les taxis --------------------------------------------------------
    @RequestMapping(value = "/all/location={idlocation}", method = RequestMethod.GET)
    public ResponseEntity<List<Taxi>> listTaxiNotInLocation(@PathVariable(value = "idlocation") int idlocation) throws Exception {
        System.out.println("recherche des taxi qui ne se trouve pas dans la location " + idlocation);
        Location loc = locationServiceImpl.read(idlocation);
        return new ResponseEntity<>(taxiServiceImpl.allNotInLoc(loc), HttpStatus.OK);
    }
    //-------------------Retrouver tous les taxis avec pagination --------------------------------------------------------

    @RequestMapping(value = "/allp",method = RequestMethod.GET)
    public ResponseEntity<Page<Taxi>> listTaxi(Pageable pageable) throws Exception{
        System.out.println("recherche de tous les taxis");
        return new ResponseEntity<>(taxiServiceImpl.allp(pageable), HttpStatus.OK);
    }

    //-------------------Gérer les erreurs--------------------------------------------------------
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Void> handleIOException(Exception ex) {
        System.out.println("erreur : " + ex.getMessage());
        return ResponseEntity.notFound().header("error", ex.getMessage()).build();
    }
}
