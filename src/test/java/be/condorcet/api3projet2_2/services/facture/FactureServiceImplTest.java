package be.condorcet.api3projet2_2.services.facture;

import be.condorcet.api3projet2_2.entities.*;
import be.condorcet.api3projet2_2.services.adresse.InterfAdresseService;
import be.condorcet.api3projet2_2.services.client.InterfClientService;
import be.condorcet.api3projet2_2.services.location.InterfLocationService;
import be.condorcet.api3projet2_2.services.taxi.InterfTaxiService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FactureServiceImplTest {

    @Autowired
    private InterfFactureService factureServiceImpl;

    @Autowired
    private InterfLocationService locationServiceImpl;

    @Autowired
    private InterfClientService clientServiceImpl;

    @Autowired
    private InterfAdresseService adresseServiceImpl;

    @Autowired
    private InterfTaxiService taxiServiceImpl;

    Facture facture;

    Location loc;

    Client cl;

    Adresse adresse;

    Taxi taxi;

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

            taxi = new Taxi(null, "T-TEST", "Diesel", 2.0);
            taxiServiceImpl.create(taxi);
            System.out.println("création du taxi : " + taxi);

            facture = new Facture(loc, taxi, new BigDecimal(loc.getKmTotal() * taxi.getPrixKm()).setScale(2, RoundingMode.HALF_UP));
            factureServiceImpl.create(facture);
            System.out.println("création de la facture : " + facture);
            //Un trigger s'occupe déjà de faire le calcul du prix de la facture

        } catch (Exception e) {
            System.out.println("erreur de création de la facture : " + facture + " erreur : " + e);
        }
    }

    @AfterEach
    void tearDown() {
        try {
            factureServiceImpl.delete(facture);

            System.out.println("effacement de la facture : " + facture);
        } catch (Exception e) {
            System.out.println("erreur d'effacement de la facture :" + facture + " erreur : " + e);
        }

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

        try {
            taxiServiceImpl.delete(taxi);

            System.out.println("effacement du taxi : " + taxi);
        } catch (Exception e) {
            System.out.println("erreur d'effacement du taxi :" + taxi + " erreur : " + e);
        }
    }

    @Test
    void create() {
        assertNotNull(facture.getTaxi(), "le taxi est null");
        assertNotNull(facture.getLocation(), "la location est null");
        assertEquals(new BigDecimal(loc.getKmTotal() * taxi.getPrixKm()).setScale(2, RoundingMode.HALF_UP), facture.getCout(), "le prix est différent");
    }

    @Test
    void read() {
        try {
            FactureKey idFacture = facture.getId();
            Facture facture2 = factureServiceImpl.read(idFacture);

            assertEquals(facture.getTaxi().getId(), facture2.getTaxi().getId(), "taxi différent" + facture.getTaxi().getId() + " au lieu de " + facture2.getTaxi().getId());
            assertEquals(facture.getLocation().getId(), facture2.getLocation().getId(), "location différente" + facture.getLocation().getId() + " au lieu de " + facture2.getLocation().getId());
            assertEquals(facture.getCout(), new BigDecimal(60).setScale(2, RoundingMode.HALF_UP), "prix différent" + facture.getCout() + " au lieu de " + facture2.getCout());
        } catch (Exception e) {
            fail("erreur de lecture de la facture : " + facture + " erreur : " + e);
        }
    }

    @Test
    void update() {
        try {
            facture.setCout(new BigDecimal(50.25).setScale(2, RoundingMode.HALF_UP));
            facture = factureServiceImpl.update(facture);

            assertEquals(facture.getCout(), new BigDecimal(50.25).setScale(2, RoundingMode.HALF_UP), "montants différents " + facture.getCout() + "-" + new BigDecimal(50.25));
        } catch (Exception e) {
            fail("erreur de mise à jour de la facture : " + facture + " erreur : " + e);

        }
    }

    @Test
    void delete() {
        try {
            factureServiceImpl.delete(facture);

            Assertions.assertThrows(Exception.class, () -> {
                factureServiceImpl.read(facture.getId());
            }, "record non effacé");
        } catch (Exception e) {
            fail("erreur d'effacement de la facture : " + facture + " erreur : " + e);
        }
    }

    @Test
    void all() {
        try {
            List<Facture> lf = factureServiceImpl.all();
            assertNotEquals(0, lf.size(), "la liste ne contient aucun élément");
        } catch (Exception e) {
            fail("erreur de lecture de toutes les factures : " + e);
        }
    }
}