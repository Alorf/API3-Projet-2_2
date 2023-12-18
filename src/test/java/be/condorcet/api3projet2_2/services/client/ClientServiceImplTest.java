package be.condorcet.api3projet2_2.services.client;

import be.condorcet.api3projet2_2.entities.Adresse;
import be.condorcet.api3projet2_2.entities.Client;
import be.condorcet.api3projet2_2.entities.Location;
import be.condorcet.api3projet2_2.entities.Taxi;
import be.condorcet.api3projet2_2.services.adresse.InterfAdresseService;
import be.condorcet.api3projet2_2.services.location.InterfLocationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

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
            System.out.println("création du client : " + cl);

            adresse = new Adresse(null, 7000, "Mons", "Rue de la chaussée", "1");
            adresseServiceImpl.create(adresse);
            System.out.println("création de l'adresse : " + adresse);


            loc = new Location(null, LocalDate.now(), 30, cl, adresse, new ArrayList<>());
            locationServiceImpl.create(loc);
            System.out.println("création de la location : " + loc);



        } catch (Exception e) {
            System.out.println("erreur de création du client : " + cl + " erreur : " + e);
        }
    }

    @AfterEach
    void tearDown() {


        try {
            locationServiceImpl.delete(loc);

            System.out.println("effacement de la location : " + loc);
        } catch (Exception e) {
            System.out.println("erreur d'effacement de la location :" + loc + " erreur : " + e);
        }

        try {
            adresseServiceImpl.delete(adresse);

            System.out.println("effacement de l'adresse : " + adresse);
        } catch (Exception e) {
            System.out.println("erreur d'effacement de l'adresse :" + adresse + " erreur : " + e);
        }

        try {
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
    }

    @Test
    void read() {
        try {
            int idCli = cl.getId();
            Client cl2 = clientServiceImpl.read(idCli);

            assertEquals(cl.getMail(), cl2.getMail(), "mail différent" + cl.getMail() + " au lieu de " + cl2.getMail());
            assertEquals(cl.getNom(), cl2.getNom(), "nom différent" + cl.getNom() + " au lieu de " + cl2.getNom());
            assertEquals(cl.getPrenom(), cl2.getPrenom(), "prénom différent" + cl.getPrenom() + " au lieu de " + cl2.getPrenom());
            assertEquals(cl.getTel(), cl2.getTel(), "tel différent" + cl.getTel() + " au lieu de " + cl2.getTel());
        } catch (Exception e) {
            fail("Recherche infructueuse : " + e);
        }
    }

    @Test
    void update() {
        try {
            cl.setMail("MailTest2@gmail.com");
            cl.setNom("NomTest2");
            cl.setPrenom("PrenomTest2");
            cl.setTel("126454");
            cl = clientServiceImpl.update(cl);

            assertEquals(cl.getMail(), "MailTest2@gmail.com", "mail différent" + cl.getMail() + " au lieu de MailTest2@gmail.com");
            assertEquals(cl.getNom(), "NomTest2", "nom différent" + cl.getNom() + " au lieu de NomTest2");
            assertEquals(cl.getPrenom(), "PrenomTest2", "prénom différent" + cl.getPrenom() + " au lieu de PrenomTest2");
            assertEquals(cl.getTel(), "126454", "tel différent" + cl.getTel() + " au lieu de 126454");
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
    void rechNom() {
        //Recherche nom
        try {
            List<Client> lc = clientServiceImpl.read("NomTest");
            boolean trouve = false;
            for (Client c : lc) {
                System.out.println("client : " + c.getNom());
                if (c.getNom().startsWith("NomTest")) {
                    trouve = true;
                    break;
                } else {
                    fail("un record ne correspond pas , nom = " + c.getNom());
                }
            }
            assertTrue(trouve, "record non trouvé dans la liste");
        } catch (Exception e) {
            fail("Erreur lors du read by nom : " + e);
        }

    }

    @Test
    void findLocationsByClient() {
        try {
            List<Location> locs = locationServiceImpl.locationEntreDeuxDates(LocalDate.now().minusYears(3), LocalDate.now());
            locs.forEach(System.out::println);

            assertNotEquals(0, locs.size(), "la liste ne contient aucun élément");
        } catch (Exception e) {
            fail("erreur de recherche de locations par client " + e);
        }

    }

    /*@Test
    void taxiUtiliseSansDoublon() {
        try {
            Page<Taxi> taxis = clientServiceImpl.taxiUtiliseSansDoublon(1, null);
            taxis.forEach(System.out::println);

            assertNotEquals(0, taxis.size(), "la liste ne contient aucun élément");
        } catch (Exception e) {
            fail("erreur de recherche de taxis par client " + e);
        }
    }*/

    @Test
    void deadresseLocationSansDoublon() {
        try {
            List<Adresse> adresses = clientServiceImpl.adresseLocationSansDoublon(1);
            adresses.forEach(System.out::println);

            assertNotEquals(0, adresses.size(), "la liste ne contient aucun élément");
        } catch (Exception e) {
            fail("erreur de recherche d'adresses par client " + e);
        }
    }
}