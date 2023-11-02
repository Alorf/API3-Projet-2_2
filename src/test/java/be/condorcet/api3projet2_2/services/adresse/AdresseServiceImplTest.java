package be.condorcet.api3projet2_2.services.adresse;


import be.condorcet.api3projet2_2.entities.Adresse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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
        assertEquals(7000, adresse.getCp(), "code postal non enregistré : " + adresse.getCp() + " au lieu de 7000");
        assertEquals("Mons", adresse.getLocalite(), "ville non enrégistrée : " + adresse.getLocalite() + " au lieu de Mons");
        assertEquals("Rue de la chaussée", adresse.getRue(), "rue non enregistrée : " + adresse.getRue() + " au lieu de Rue de la chaussée");
        assertEquals("1", adresse.getNum(), "numero non enregistré : " + adresse.getNum() + " au lieu de 1");

    }

    @Test
    void read() {
        try {
            int idadresse = adresse.getId();
            Adresse adresse2 = adresseServiceImpl.read(idadresse);

            assertEquals(adresse.getCp(), adresse2.getCp(), "code postal différent" + adresse.getCp() + " au lieu de " + adresse2.getCp());
            assertEquals(adresse.getLocalite(), adresse2.getLocalite(), "localite différente" + adresse.getLocalite() + " au lieu de " + adresse2.getLocalite());
            assertEquals(adresse.getRue(), adresse2.getRue(), "rue différente" + adresse.getRue() + " au lieu de " + adresse2.getRue());
            assertEquals(adresse.getNum(), adresse2.getNum(), "numero différent" + adresse.getNum() + " au lieu de " + adresse2.getNum());
        } catch (Exception e) {
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
            adresse = adresseServiceImpl.update(adresse);

            assertEquals(7904, adresse.getCp(), "code postal différent " + adresse.getCp() + " au lieu de 7904");
            assertEquals("Willaupuis", adresse.getLocalite(), "localite différente " + adresse.getLocalite() + " au lieu de Willaupuis");
            assertEquals("Avenue des Sartiaux", adresse.getRue(), "rue différente " + adresse.getRue() + " au lieu de Avenue des Sartiaux");
            assertEquals("159", adresse.getNum(), "numero différent " + adresse.getNum() + " au lieu de 159");
        } catch (Exception e) {
            System.out.println("erreur de création de l'adresse : " + adresse + " erreur : " + e);
        }
    }

    @Test
    void delete() {
        try {
            adresseServiceImpl.delete(adresse);
            Assertions.assertThrows(Exception.class, () -> {
                adresseServiceImpl.read(adresse.getId());
            }, "record non effacé");
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
    void rechLocalite() {
        try {
            List<Adresse> adresses = adresseServiceImpl.read("Mons");
            boolean trouve = false;
            for (Adresse adresse : adresses) {
                if (adresse.getLocalite().startsWith("Mons")) {
                    trouve = true;
                } else {
                    fail("un record ne correspond pas , nom = " + adresse.getLocalite());
                }
            }
            assertTrue(trouve, "record non trouvé dans la liste");
        } catch (Exception e) {
            System.out.println("erreur de recherche de l'adresse : " + adresse + " erreur : " + e);
        }
    }
}