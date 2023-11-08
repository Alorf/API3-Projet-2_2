package be.condorcet.api3projet2_2.webservices;

import be.condorcet.api3projet2_2.entities.*;
import be.condorcet.api3projet2_2.services.facture.InterfFactureService;
import be.condorcet.api3projet2_2.services.location.InterfLocationService;
import be.condorcet.api3projet2_2.services.taxi.InterfTaxiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "*")
@RestController
@RequestMapping("/factures")
public class RestFacture {
    @Autowired
    private InterfFactureService factureServiceImpl;

    @Autowired
    private InterfTaxiService taxiServiceImpl;

    @Autowired
    private InterfLocationService locationServiceImpl;



    //-------------------Retrouver le facture correspondant à un id donné--------------------------------------------------------
    @RequestMapping(value = "/{idlocation}/{idtaxi}", method = RequestMethod.GET)
    public ResponseEntity<Facture> getFacture(@PathVariable(value = "idlocation") int idLocation, @PathVariable(value = "idtaxi") int idTaxi) throws Exception {
        FactureKey id = new FactureKey(idLocation, idTaxi);
        System.out.println("recherche du facture d' id " + id);
        Facture facture = factureServiceImpl.read(id);
        return new ResponseEntity<>(facture, HttpStatus.OK);
    }

    //-------------------Retrouver les factures portant un taxi donné--------------------------------------------------------
    @RequestMapping(value = "/idtaxi={idtaxi}", method = RequestMethod.GET)
    public ResponseEntity<List<Facture>> getFactureTaxi(@PathVariable(value = "idtaxi") int id) throws Exception {
        System.out.println("recherche des commandes du client d'id " + id);
        System.out.println("test???");
        Taxi taxi = taxiServiceImpl.read(id);
        System.out.println(taxi);
        List<Facture> lfac = factureServiceImpl.read(taxi);
        return new ResponseEntity<>(lfac, HttpStatus.OK);
    }

    //-------------------Créer un facture--------------------------------------------------------
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Facture> createFacture(@RequestBody Facture facture) throws Exception {
        facture.setLocation(new Location(facture.getId().getIdLocation()));
        facture.setTaxi(new Taxi(facture.getId().getIdTaxi()));

        System.out.println("Création de Facture " + facture);

        factureServiceImpl.create(facture);
        return new ResponseEntity<>(facture, HttpStatus.OK);
    }

    //-------------------Mettre à jour une facture d'un id donné--------------------------------------------------------
    @RequestMapping(value = "/{idlocation}/{idtaxi}", method = RequestMethod.PUT)
    public ResponseEntity<Facture> majFacture(@PathVariable(value = "idlocation") int idLocation, @PathVariable(value = "idtaxi") int idTaxi, @RequestBody Facture fac) throws Exception {
        FactureKey id = new FactureKey(idLocation, idTaxi);
        System.out.println("maj de facture id =  " + id);
        fac.setId(id);
        Facture clact = factureServiceImpl.update(fac);
        return new ResponseEntity<>(clact, HttpStatus.OK);
    }

    //-------------------Effacer une facture d'un id donné--------------------------------------------------------
    @RequestMapping(value = "/{idlocation}/{idtaxi}", method = RequestMethod.DELETE)
    public ResponseEntity<Facture> deleteFacture(@PathVariable(value = "idlocation") int idLocation, @PathVariable(value = "idtaxi") int idTaxi) throws Exception {
        FactureKey id = new FactureKey(idLocation, idTaxi);
        System.out.println("effacement du facture d'id " + id);
        Facture facture = factureServiceImpl.read(id);
        factureServiceImpl.delete(facture);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //-------------------Retrouver tous les factures --------------------------------------------------------
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Facture>> listFacture() throws Exception {
        System.out.println("recherche de tous les factures");
        return new ResponseEntity<>(factureServiceImpl.all(), HttpStatus.OK);
    }

    //-------------------Gérer les erreurs--------------------------------------------------------
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Void> handleIOException(Exception ex) {
        System.out.println("erreur : " + ex.getMessage());
        return ResponseEntity.notFound().header("error", ex.getMessage()).build();
    }
}
