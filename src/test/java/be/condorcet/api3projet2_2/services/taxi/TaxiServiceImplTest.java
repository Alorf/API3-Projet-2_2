package be.condorcet.api3projet2_2.services.taxi;


import be.condorcet.api3projet2_2.entities.Taxi;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaxiServiceImplTest {

    @Autowired
    private InterfTaxiService taxiServiceImpl;

    Taxi taxi;

    @BeforeEach
    void setUp() {
        try {
            taxi = new Taxi(null, "T-TEST", "Diesel", 2.0);
            taxiServiceImpl.create(taxi);

            System.out.println("création du taxi : " + taxi);
        } catch (Exception e) {
            System.out.println("erreur de création du taxi : " + taxi + " erreur : " + e);
        }
    }

    @AfterEach
    void tearDown() {
        try {
            taxiServiceImpl.delete(taxi);

            System.out.println("effacement du taxi : " + taxi);
        } catch (Exception e) {
            System.out.println("erreur d'effacement du taxi :" + taxi + " erreur : " + e);
        }
    }

    @Test
    void create() {
        assertNotEquals(0, taxi.getId(), "id taxi non incrémenté");
        assertEquals("T-TEST", taxi.getImmatriculation(), "immatriculation non conforme");
        assertEquals("Diesel", taxi.getCarburant(), "carburant non conforme");
    }

    @Test
    void read() {
        try{
            int idTaxi = taxi.getId();
            Taxi taxi2 = taxiServiceImpl.read(idTaxi);
            assertEquals(taxi.getImmatriculation(), taxi2.getImmatriculation(), "taxi non conforme");
            assertEquals(taxi.getCarburant(), taxi2.getCarburant(), "taxi non conforme");
        }catch (Exception e){
            System.out.println("erreur de création du taxi : " + taxi + " erreur : " + e);
        }
    }

    @Test
    void update() {
        try {
            taxi.setImmatriculation("T-TEST2");
            taxi.setCarburant("Essence");
            taxiServiceImpl.update(taxi);
            assertEquals("T-TEST2", taxi.getImmatriculation(), "immatriculation non conforme");
            assertEquals("Essence", taxi.getCarburant(), "carburant non conforme");
        } catch (Exception e) {
            System.out.println("erreur de création du taxi : " + taxi + " erreur : " + e);
        }
    }

    @Test
    void delete() {
        try {
            taxiServiceImpl.delete(taxi);
            System.out.println("effacement du taxi : " + taxi);
        } catch (Exception e) {
            System.out.println("erreur de création du taxi : " + taxi + " erreur : " + e);
        }
    }

    @Test
    void all() {
        try {
            List<Taxi> taxis = taxiServiceImpl.all();
            assertNotEquals(0, taxis.size(), "nombre de taxis non conforme");
        } catch (Exception e) {
            System.out.println("erreur de création du taxi : " + taxi + " erreur : " + e);
        }
    }

    @Test
    void testRead() {
        try {
            List<Taxi> taxis = taxiServiceImpl.read("Diesel");
            assertNotEquals(0, taxis.size(), "nombre de taxis non conforme");
        } catch (Exception e) {
            System.out.println("erreur de création du taxi : " + taxi + " erreur : " + e);
        }
    }
}