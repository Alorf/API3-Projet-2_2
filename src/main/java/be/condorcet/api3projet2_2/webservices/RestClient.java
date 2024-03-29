package be.condorcet.api3projet2_2.webservices;

import be.condorcet.api3projet2_2.entities.Adresse;
import be.condorcet.api3projet2_2.entities.Client;
import be.condorcet.api3projet2_2.entities.Taxi;
import be.condorcet.api3projet2_2.services.client.InterfClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "*")
@RestController
@RequestMapping("/clients")
public class RestClient {
    @Autowired
    private InterfClientService clientServiceImpl;


    //-------------------Retrouver le client correspondant à un id donné--------------------------------------------------------
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Client> getClient(@PathVariable(value = "id") int id) throws Exception {
        System.out.println("recherche du client d' id " + id);
        Client client = clientServiceImpl.read(id);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    //------------------- EXAMEN : Retrouver le kilométrage total correspondant d'un client gràce à un id donné--------------------------------------------------------
    @RequestMapping(value = "/kmtotal/{id}", method = RequestMethod.GET)
    public ResponseEntity<Integer> getKmTotalClient(@PathVariable(value = "id") int id) throws Exception {
        System.out.println("recherche du km total du client d' id " + id);
        Integer km = clientServiceImpl.getKmParcouruByClient(id);
        if (km == null) {
            km = 0;
        }
        return new ResponseEntity<>(km, HttpStatus.OK);
    }

    //-------------------Retrouver les clients portant un nom donné--------------------------------------------------------
    @RequestMapping(value = "/nom={nom}", method = RequestMethod.GET)
    public ResponseEntity<List<Client>> listClientsNom(@PathVariable(value = "nom") String nom) throws Exception {
        System.out.println("recherche de " + nom);
        List<Client> clients;
        clients = clientServiceImpl.read(nom);
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    //-------------------Retrouver le client correspondant à un triplet (nom,prénom,tel) unique donné--------------------------------------------------------
    @RequestMapping(value = "/{nom}/{prenom}/{tel}", method = RequestMethod.GET)
    public ResponseEntity<Client> getClientUnique(@PathVariable(value = "nom") String nom,
                                                  @PathVariable(value = "prenom") String prenom,
                                                  @PathVariable(value = "tel") String tel) throws Exception {
        System.out.println("recherche du client " + nom + " " + prenom + " " + tel);
        Client client = clientServiceImpl.read(nom, prenom, tel);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    //-------------------Créer un client--------------------------------------------------------
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Client> createClient(@RequestBody Client client) throws Exception {
        System.out.println("Création de Client " + client.getNom());
        clientServiceImpl.create(client);
        System.out.println("Debug");
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    //-------------------Mettre à jour un client d'un id donné--------------------------------------------------------
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Client> majClient(@PathVariable(value = "id") int id, @RequestBody Client nouvcli) throws Exception {
        System.out.println("maj de client id =  " + id);
        nouvcli.setId(id);
        Client clact = clientServiceImpl.update(nouvcli);
        return new ResponseEntity<>(clact, HttpStatus.OK);
    }

    //-------------------Effacer un client d'un id donné--------------------------------------------------------
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteClient(@PathVariable(value = "id") int id) throws Exception {
        System.out.println("effacement du client d'id " + id);
        Client client = clientServiceImpl.read(id);
        clientServiceImpl.delete(client);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //-------------------Retrouver tous les clients --------------------------------------------------------
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Client>> listClient() throws Exception {
        System.out.println("recherche de tous les clients");
        return new ResponseEntity<>(clientServiceImpl.all(), HttpStatus.OK);
    }

    //-------------------Retrouver tous les clients avec pagination --------------------------------------------------------

    @RequestMapping(value = "/allp",method = RequestMethod.GET)
    public ResponseEntity<Page<Client>> listClient(Pageable pageable) throws Exception{
        System.out.println("recherche de tous les clients");
        System.out.println(pageable);
        return new ResponseEntity<>(clientServiceImpl.allp(pageable), HttpStatus.OK);
    }

    //-------------------Retrouver tous les taxis sans doublons --------------------------------------------------------

    @RequestMapping(value = "/taxiSansDoublon/idclient={id}",method = RequestMethod.GET)
    public ResponseEntity<List<Taxi>> listTaxiSansDoublons(@PathVariable(value = "id") int idclient) throws Exception{
        System.out.println("recherche de tous les taxis sans doublons");
        return new ResponseEntity<>(clientServiceImpl.taxiUtiliseSansDoublon(idclient), HttpStatus.OK);
    }

    //-------------------Retrouver toutes les adresses sans doublons --------------------------------------------------------

    @RequestMapping(value = "/adressesSansDoublon/idclient={id}",method = RequestMethod.GET)
    public ResponseEntity<List<Adresse>> listAdressesSansDoublons(@PathVariable(value = "id") int idclient) throws Exception{
        System.out.println("recherche de toutes les adresses sans doublons");
        return new ResponseEntity<>(clientServiceImpl.adresseLocationSansDoublon(idclient), HttpStatus.OK);
    }

    //-------------------Gérer les erreurs--------------------------------------------------------
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Void> handleIOException(Exception ex) {
        System.out.println("erreur : " + ex.getMessage());
        return ResponseEntity.notFound().header("error", ex.getMessage()).build();
    }
}