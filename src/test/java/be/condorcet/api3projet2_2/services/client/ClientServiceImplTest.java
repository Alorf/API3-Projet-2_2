package be.condorcet.api3projet2_2.services.client;

import be.condorcet.api3projet2_2.entities.Adresse;
import be.condorcet.api3projet2_2.entities.Client;
import be.condorcet.api3projet2_2.entities.Location;
import be.condorcet.api3projet2_2.entities.Taxi;
import be.condorcet.api3projet2_2.services.adresse.InterfAdresseService;
import be.condorcet.api3projet2_2.services.location.InterfLocationService;
import ch.qos.logback.core.encoder.EchoEncoder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClientServiceImplTest {

    @Autowired
    private InterfClientService clientServiceImpl;

    @Autowired
    private InterfLocationService locationServiceImpl;

    @Autowired
    private InterfAdresseService adresseServiceImpl;

    Client cl;
    Location loc;

    Adresse adresse;

    @BeforeEach
    void setUp() {
        try {
            cl = new Client(null, "MailTest@gmail.com", "NomTest", "PrenomTest", "123", new ArrayList<>());
            clientServiceImpl.create(cl);

            adresse = new Adresse(null, 7000, "Mons", "Rue de la chaussée", "1");
            adresseServiceImpl.create(adresse);

            loc = new Location(null, LocalDate.now(), 30, cl, adresse, new ArrayList<>());
            locationServiceImpl.create(loc);

            System.out.println("création du client : " + cl);
        } catch (Exception e) {
            System.out.println("erreur de création du client : " + cl + " erreur : " + e);
        }
    }

    @AfterEach
    void tearDown() {
        try {
            locationServiceImpl.delete(loc);
            adresseServiceImpl.delete(adresse);
            clientServiceImpl.delete(cl);

            System.out.println("effacement du client : " + cl);
        } catch (Exception e) {
            System.out.println("erreur d'effacement du client :" + cl + " erreur : " + e);
        }
    }

    @Test
    void create() {
        assertNotEquals(0, cl.getId(), "id client non incrémenté");
        assertEquals("MailTest@gmail.com", cl.getMail(), "mail client non enregistré : " + cl.getMail() + " au lieu de MailTest");
        assertEquals("NomTest", cl.getNom(), "nom client non enregistré : " + cl.getNom() + " au lieu de NomTest");
        assertEquals("PrenomTest", cl.getPrenom(), "prénom client non enregistré : " + cl.getPrenom() + " au lieu de PrenomTest");
        assertEquals("123", cl.getTel(), "tel client non enregistré : " + cl.getTel() + " au lieu de 123");
        assertEquals(new ArrayList<>(), cl.getLocations(), "liste de locations non vide");
    }

    @Test
    void read() {
        try {
            int idCli = cl.getId();
            Client cl2 = clientServiceImpl.read(idCli);
            assertEquals(cl.getMail(), cl2.getMail(), "mail client non lu : " + cl2.getMail() + " au lieu de MailTest");
            assertEquals(cl.getNom(), cl2.getNom(), "nom client non lu : " + cl2.getNom() + " au lieu de NomTest");
            assertEquals(cl.getPrenom(), cl2.getPrenom(), "prénom client non lu : " + cl2.getPrenom() + " au lieu de PrenomTest");
            assertEquals(cl.getTel(), cl2.getTel(), "tel client non lu : " + cl2.getTel() + " au lieu de 123");

        } catch (Exception e) {
            fail("Recherche infructueuse : " + e);
        }
    }

    @Test
    void update() {
        try {
            Client cl2 = new Client(cl.getId(), cl.getMail(), cl.getNom(), cl.getPrenom(), cl.getTel(), new ArrayList<>());
            cl2.setMail("MailTest2@gmail.com");
            cl2.setNom("NomTest2");
            cl2.setPrenom("PrenomTest2");
            cl2.setTel("126454");
            clientServiceImpl.update(cl2);
            assertNotEquals(cl, cl2, "client non mis à jour");
        } catch (Exception e) {
            fail("Mise à jour infructueuse : " + e);
        }
    }

    @Test
    void delete() {
        try {
            locationServiceImpl.delete(loc);
            adresseServiceImpl.delete(adresse);
            clientServiceImpl.delete(cl);
            Assertions.assertThrows(Exception.class, () -> {
                adresseServiceImpl.read(adresse.getId());
                locationServiceImpl.read(loc.getId());
                clientServiceImpl.read(cl.getId());
            }, "Enregistrement non effacé");
        } catch (Exception e) {
            fail("erreur d'effacement " + e);
        }
    }

    @Test
    void all() {
        try {
            List<Client> lc = clientServiceImpl.all();
            assertNotEquals(0, lc.size(), "la liste ne contient aucun élément");
        } catch (Exception e) {
            fail("erreur de recherche de tous les clients " + e);
        }
    }

    @Test
    void testRead() {
        //Recherche nom
        try{
            List<Client> lc = clientServiceImpl.read("NomTest");
            boolean trouve = false;
            for (Client c : lc) {
                System.out.println("client : " + c.getNom());
                if (c.getNom().startsWith("NomTest")){
                    trouve = true;
                    break;
                }
                else {
                    fail("un record ne correspond pas , nom = " + c.getNom());
                }
            }
            assertTrue(trouve, "record non trouvé dans la liste");
        }catch (Exception e){
            fail("Erreur lors du read by nom : " + e);
        }

    }

    @Test
    void findLocationsByClient() {
        try{
            List<Location> locs = locationServiceImpl.getLocationsByClientAndDatelocBetween(cl,LocalDate.now().minusYears(3),LocalDate.now());
            locs.forEach(System.out::println);

            assertNotEquals(0, locs.size(), "la liste ne contient aucun élément");
        }catch (Exception e){
            fail("erreur de recherche de locations par client " + e);
        }

    }

    @Test
    void taxiUtiliseSansDoublon() {
        try{
            List<Taxi> taxis = clientServiceImpl.taxiUtiliseSansDoublon(1);

            assertNotEquals(0, taxis.size(), "la liste ne contient aucun élément");
        }catch (Exception e){
            fail("erreur de recherche de taxis par client " + e);
        }
    }

    @Test
    void adresseLocationSansDoublon() {
        try{
            List<Adresse> adresses = clientServiceImpl.adresseLocationSansDoublon(1);

            assertNotEquals(0, adresses.size(), "la liste ne contient aucun élément");
        }catch (Exception e){
            fail("erreur de recherche d'adresses par client " + e);
        }
    }
}