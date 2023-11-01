package be.condorcet.api3projet2_2.services.adresse;


import be.condorcet.api3projet2_2.entities.Adresse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdresseServiceImplTest {

    @Autowired
    private InterfAdresseService adresseServiceImpl;

    Adresse adresse;

    @BeforeEach
    void setUp() {
        try {
            adresse = new Adresse(null, 7000, "Mons", "Rue de la chaussée", "1");
            adresseServiceImpl.create(adresse);

            System.out.println("création de l'adresse : " + adresse);
        } catch (Exception e) {
            System.out.println("erreur de création de l'adresse : " + adresse + " erreur : " + e);
        }
    }

    @AfterEach
    void tearDown() {
        try {
            adresseServiceImpl.delete(adresse);

            System.out.println("effacement de l'adresse : " + adresse);
        } catch (Exception e) {
            System.out.println("erreur d'effacement de l'adresse :" + adresse + " erreur : " + e);
        }
    }

    @Test
    void create() {
        assertNotEquals(0, adresse.getId(), "id adresse non incrémenté");
        assertEquals(7000, adresse.getCp(), "code postal non conforme");
        assertEquals("Mons", adresse.getLocalite(), "ville non conforme");
        assertEquals("Rue de la chaussée", adresse.getRue(), "rue non conforme");
        assertEquals("1", adresse.getNum(), "numero non conforme");

    }

    @Test
    void read() {
        try{
            int idadresse = adresse.getId();
            Adresse adresse2 = adresseServiceImpl.read(idadresse);
            assertEquals(adresse.getCp(), adresse2.getCp(), "adresse non conforme");
            assertEquals(adresse.getLocalite(), adresse2.getLocalite(), "adresse non conforme");
            assertEquals(adresse.getRue(), adresse2.getRue(), "adresse non conforme");
            assertEquals(adresse.getNum(), adresse2.getNum(), "adresse non conforme");

        }catch (Exception e){
            System.out.println("erreur de création de l'adresse : " + adresse + " erreur : " + e);
        }
    }

    @Test
    void update() {
        try {
            adresse.setCp(7904);
            adresse.setLocalite("Willaupuis");
            adresse.setRue("Avenue des Sartiaux");
            adresse.setNum("159");
            adresseServiceImpl.update(adresse);
            assertEquals(7904, adresse.getCp(), "code postal non conforme");
            assertEquals("Willaupuis", adresse.getLocalite(), "ville non conforme");
            assertEquals("Avenue des Sartiaux", adresse.getRue(), "rue non conforme");
            assertEquals("159", adresse.getNum(), "numero non conforme");

        } catch (Exception e) {
            System.out.println("erreur de création de l'adresse : " + adresse + " erreur : " + e);
        }
    }

    @Test
    void delete() {
        try {
            adresseServiceImpl.delete(adresse);
            System.out.println("effacement de l'adresse : " + adresse);
        } catch (Exception e) {
            System.out.println("erreur de création de l'adresse : " + adresse + " erreur : " + e);
        }
    }

    @Test
    void all() {
        try {
            List<Adresse> adresses = adresseServiceImpl.all();
            assertNotEquals(0, adresses.size(), "nombre de adresses non conforme");
        } catch (Exception e) {
            System.out.println("erreur de création de l'adresse : " + adresse + " erreur : " + e);
        }
    }

    @Test
    void testRead() {
        try {
            List<Adresse> adresses = adresseServiceImpl.read("Mons");
            assertNotEquals(0, adresses.size(), "nombre de adresses non conforme");
        } catch (Exception e) {
            System.out.println("erreur de création de l'adresse : " + adresse + " erreur : " + e);
        }
    }
}