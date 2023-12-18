package be.condorcet.api3projet2_2.services.location;

import be.condorcet.api3projet2_2.entities.Adresse;
import be.condorcet.api3projet2_2.entities.Client;
import be.condorcet.api3projet2_2.entities.Location;
import be.condorcet.api3projet2_2.services.adresse.InterfAdresseService;
import be.condorcet.api3projet2_2.services.client.InterfClientService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LocationServiceImplTest {

    @Autowired
    private InterfLocationService locationServiceImpl;

    @Autowired
    private InterfClientService clientServiceImpl;

    @Autowired
    private InterfAdresseService adresseServiceImpl;
    Location loc;
    Client cl;
    Adresse adresse;

    LocalDate date = LocalDate.now();

    @BeforeEach
    void setUp() {
        try {
            cl = new Client(null, "MailTest@gmail.com", "NomTest", "PrenomTest", "123", new ArrayList<>());
            clientServiceImpl.create(cl);

            adresse = new Adresse(null, 7000, "Mons", "Rue de la chaussée", "1");
            adresseServiceImpl.create(adresse);

            loc = new Location(null, date, 30, cl, adresse, new ArrayList<>());
            locationServiceImpl.create(loc);

            System.out.println("création de la location : " + loc);
        } catch (Exception e) {
            System.out.println("erreur de création de la location : " + loc + " erreur : " + e);
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
            clientServiceImpl.delete(cl);

            System.out.println("effacement du client : " + cl);
        } catch (Exception e) {
            System.out.println("erreur d'effacement du client :" + cl + " erreur : " + e);
        }

        try {
            adresseServiceImpl.delete(adresse);

            System.out.println("effacement de l'adresse : " + adresse);
        } catch (Exception e) {
            System.out.println("erreur d'effacement de l'adresse :" + adresse + " erreur : " + e);
        }
    }

    @Test
    void create() {
        assertNotEquals(0, loc.getId(), "id location non incrémenté");
        assertEquals(30, loc.getKmTotal(), "kilométrage total non enregistré : " + loc.getKmTotal() + " au lieu de 30");
        assertEquals(date, loc.getDateloc(), "date de location non enregistrée : " + loc.getDateloc() + " au lieu de " + date);
        assertEquals(cl.getId(), loc.getClient().getId(), "client non enregistré : " + loc.getClient().getId() + " au lieu de " + cl.getId());
        assertEquals(adresse.getId(), loc.getAdrDepart().getId(), "adresse de départ non enregistrée : " + loc.getAdrDepart().getId() + " au lieu de " + adresse.getId());

    }

    @Test
    void read() {
        try {
            int idLoc = loc.getId();
            Location loc2 = locationServiceImpl.read(idLoc);

            assertEquals(loc.getKmTotal(), loc2.getKmTotal(), "kilométrage total différent" + loc.getKmTotal() + " au lieu de " + loc2.getKmTotal());
            assertEquals(loc.getDateloc(), loc2.getDateloc(), "date de location différente" + loc.getDateloc() + " au lieu de " + loc2.getDateloc());
            assertEquals(loc.getClient().getId(), loc2.getClient().getId(), "client différent" + loc.getClient().getId() + " au lieu de " + loc2.getClient().getId());
            assertEquals(loc.getAdrDepart().getId(), loc2.getAdrDepart().getId(), "adresse de départ différente" + loc.getAdrDepart().getId() + " au lieu de " + loc2.getAdrDepart().getId());
        } catch (Exception e) {
            fail("erreur de lecture de la location : " + loc + " erreur : " + e);
        }
    }

    @Test
    void update() {
        try {
            loc.setDateloc(LocalDate.of(2018, 1, 1));
            loc = locationServiceImpl.update(loc);

            assertEquals(LocalDate.of(2018, 1, 1), loc.getDateloc(), "date de location différente" + loc.getDateloc() + " au lieu de " + LocalDate.of(2036, 1, 1));
        } catch (Exception e) {
            fail("erreur de mise à jour de la location : " + loc + " erreur : " + e);
        }
    }

    @Test
    void delete() {
        try {
            locationServiceImpl.delete(loc);

            Assertions.assertThrows(Exception.class, () -> {
                locationServiceImpl.read(loc.getId());
            }, "Enregistrement non effacé");
        } catch (Exception e) {
            fail("erreur d'effacement " + e);
        }
    }

    @Test
    void all() {
        try {
            List<Location> ll = locationServiceImpl.all();
            assertNotEquals(0, ll.size(), "la liste ne contient aucun élément");
        } catch (Exception e) {
            fail("erreur de recherche de tous les clients " + e);
        }
    }

    @Test
    void rechClient() {
        try {
            Collection<Location> lco = locationServiceImpl.read(cl);
            boolean trouve = false;
            for (Location c : lco) {
                if (c.getId().equals(loc.getId())) {
                    trouve = true;
                    break;
                }
            }
            assertTrue(trouve, "location absente de la liste du client");
        } catch (Exception e) {
            fail("Erreur de recherche " + e);
        }
    }

    @Test
    void rechDate() {
        try {
            LocalDate now = LocalDate.now();
            List<Location> ll = locationServiceImpl.read(now);
            boolean trouve = false;
            for (Location location : ll) {
                if (location.getDateloc().equals(now)) {
                    trouve = true;
                } else {
                    fail("un record ne correspond pas , nom = " + location.getDateloc());
                }
            }
            assertNotEquals(0, ll.size(), "la liste ne contient aucun élément");
        } catch (Exception e) {
            fail("erreur de recherche de toutes les locations d'une date " + e);
        }
    }

    @Test
    void locationEntreDeuxDates() {
        try {
            List<Location> locs = locationServiceImpl.locationEntreDeuxDates(LocalDate.now().minusYears(3), LocalDate.now());
            assertNotEquals(0, locs.size(), "la liste ne contient aucun élément");
        } catch (Exception e) {
            fail("erreur de recherche de toutes les locations d'un client et d'une date " + e);
        }
    }
}