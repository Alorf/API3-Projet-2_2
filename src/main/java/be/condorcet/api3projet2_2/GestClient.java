package be.condorcet.api3projet2_2;

import be.condorcet.api3projet2_2.entities.Client;
import be.condorcet.api3projet2_2.services.client.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/clients")
public class GestClient {

    @Autowired     //instanciation "automatique" par le framework avec les paramètres indiqués, il s'agit d'un singleton
    ClientServiceImpl ClientService;

    @RequestMapping("/tous")
    public String affTous(Map<String, Object> model) {
        //Le model ce sont les champ html qui devront être appelés dans la vue
        System.out.println("recherche clients");
        List<Client> liste;
        try {
            liste = ClientService.all();
            model.put("mesClients", liste);
        } catch (Exception e) {
            System.out.println("----------erreur lors de la recherche-------- " + e);
            return "error";
        }
        return "affichagetousClients";
    }

    @RequestMapping("/selection")
    String selection(@RequestParam("numcli") int numcli, Map<String, Object> model) {
        Client cl = null;
        Optional<Client> ocl;

        try {
            cl = ClientService.read(numcli);   // à développer

            model.put("monClient", cl);
            model.put("mesLocations", cl.getLocations());
            model.put("adresseLocationSansDoublon", cl.adresseLocationSansDoublon());

        } catch (Exception e) {
            System.out.println("erreur lors de la lecture " + e);
            return "error";
        }
        return "affClient";  // page html à développer
    }

    @RequestMapping("/selectionNom")
    String selection(@RequestParam("nom") String nom, Map<String, Object> model) {
        List<Client> cl = null;

        try {
            cl = ClientService.read(nom);   // à développer

            model.put("mesClients", cl);
        } catch (Exception e) {
            System.out.println("erreur lors de la lecture " + e);
            return "error";
        }
        return "affMesClients";  // page html à développer
    }

}