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

import java.time.LocalDate;
import java.util.ArrayList;
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

    @BeforeEach
    void setUp() {
        try {
            cl = new Client(null, "MailTest@gmail.com", "NomTest", "PrenomTest", "123", new ArrayList<>());
            clientServiceImpl.create(cl);

            adresse = new Adresse(null, 7000, "Mons", "Rue de la chaussée", "1");
            adresseServiceImpl.create(adresse);

            loc = new Location(null, LocalDate.now(), 30, cl, adresse, new ArrayList<>());
            locationServiceImpl.create(loc);
        } catch (Exception e) {
            System.out.println("erreur de création de la location : " + loc + " erreur : " + e);
        }
    }

    @AfterEach
    void tearDown() {
        try {
            locationServiceImpl.delete(loc);
            clientServiceImpl.delete(cl);
            adresseServiceImpl.delete(adresse);
            System.out.println("effacement de la location : " + loc);
        } catch (Exception e) {
            System.out.println("erreur d'effacement de la location :" + loc + " erreur : " + e);
        }
    }

    @Test
    void create() {
        assertNotEquals(0, loc.getId(), "id location non incrémenté");
        assertNotNull(loc.getClient(), "client non créé");
        assertNotNull(loc.getAdrDepart(), "adresse de départ non créée");
        assertNotNull(loc.getDateloc(), "date de location non créée");
        assertNotNull(loc.getKmTotal(), "kilométrage total non créé");
        assertNotNull(loc.getFacturations(), "facturation non créée");

    }

    @Test
    void read() {
        try {
            int idLoc = loc.getId();
            Location loc2 = locationServiceImpl.read(idLoc);

            assertEquals(loc.getKmTotal(), loc2.getKmTotal(), "kilométrage total non lu");
            assertEquals(loc.getDateloc(), loc2.getDateloc(), "date de location non lue");
            assertEquals(loc.getClient().getId(), loc2.getClient().getId(), "client non lu");
            assertEquals(loc.getAdrDepart(), loc2.getAdrDepart(), "adresse de départ non lue");

        } catch (Exception e) {
            fail("erreur de lecture de la location : " + loc + " erreur : " + e);
        }
    }

    @Test
    void update() {
        try {
            Location loc2 = new Location(loc.getId(), LocalDate.now(), 30, cl, adresse, new ArrayList<>());
            loc2.setDateloc(LocalDate.of(2036, 1, 1));
            locationServiceImpl.update(loc2);

            assertNotEquals(loc, loc2, "location non mise à jour");
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
    void getLocations() {
    }

    @Test
    void getLocationsByDate() {
    }

    @Test
    void getLocationsByClientAndDatelocBetween() {
    }
}