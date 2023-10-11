package be.condorcet.api3projet2_2;

import be.condorcet.api3projet2_2.model.Client;
import be.condorcet.api3projet2_2.model.ClientDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/clients")
public class GestClient {

    @Autowired     //instanciation "automatique" par le framework avec les paramètres indiqués, il s'agit d'un singleton
    ClientDAO clientDAO;


    @RequestMapping("/tous")
    public String affTous(Map<String, Object> model){
        //Le model ce sont les champ html qui devront être appelés dans la vue
        System.out.println("recherche clients");
        List<Client> liste;
        try {
            liste= clientDAO.readall();
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

        try {
            cl = clientDAO.read(numcli);   // à développer
            model.put("monClient", cl);
            model.put("mesLocations", cl.getLocations());
            model.put("adresseLocationSansDoublon", cl.adresseLocationSansDoublon());

        } catch (Exception e) {
            System.out.println("erreur lors de la lecture " + e);
            return "error";
        }
        return "affClient";  // page html à développer
    }
}