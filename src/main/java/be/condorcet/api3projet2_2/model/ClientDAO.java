package be.condorcet.api3projet2_2.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class ClientDAO {

    String url;
    String userid;

    String password;

    private Connection dbConnect = null;


    public ClientDAO(@Value("${spring.datasource.url}") String url, @Value("${spring.datasource.username}") String userid, @Value("${spring.datasource.password}") String password) {
        this.url = url;
        this.userid = userid;
        this.password = password;

        try {
            Class.forName("oracle.jdbc.OracleDriver");
            dbConnect = DriverManager.getConnection(url, userid, password);
            // connexion avec le login et le password
            // et récupération d'un objet connection
        } catch (Exception e) {
            System.out.println("erreur : " + e);
            System.exit(0);
        }
    }

    public List<Client> readall() throws Exception {
        List<Client> lc = new ArrayList<>();

        try (PreparedStatement pstm = dbConnect.prepareStatement("SELECT * FROM API_TCLIENT")) {
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                int idclient = rs.getInt("ID_CLIENT");
                String mail = rs.getString("MAIL");
                String nom = rs.getString("NOM");
                String prenom = rs.getString("PRENOM");
                String tel = rs.getString("TEL");
                lc.add(new Client(idclient, mail, nom, prenom, tel));
            }
            if (lc.isEmpty()) throw new Exception("aucun client");
            return lc;

        } catch (Exception e) {
            throw new Exception("Erreur de lecture " + e.getMessage());
        }
    }

    public Client read(int idRech) {
        String query = "SELECT * FROM API_LOCATIONCLIENTADRESSE WHERE ID_CLIENT = ?";

        try (PreparedStatement req = dbConnect.prepareStatement(query)) {
            req.setInt(1, idRech);
            ResultSet rs = req.executeQuery();

            if (rs.next()) {

                //int idClient = rs.getInt(1);
                String mailClient = rs.getString("mail");
                String nomClient = rs.getString("nom");
                String prenomClient = rs.getString("prenom");
                String telClient = rs.getString("tel");

                Client client = new Client(idRech, mailClient, nomClient, prenomClient, telClient);

                List<Location> locations = new ArrayList<>();

                do {
                    int idLoc = rs.getInt("id_location");
                    if (idLoc == 0) {
                        break;
                    }
                    LocalDate dateloc = rs.getDate("dateloc").toLocalDate();
                    int kmTotalLoc = rs.getInt("kmtotal");

                    int idAdresse = rs.getInt("id_adresse");
                    int cpAdresse = rs.getInt("cp");
                    String localiteAdresse = rs.getString("localite");
                    String rueAdresse = rs.getString("rue");
                    String numAdresse = rs.getString("num");

                    Adresse adresse = new Adresse(idAdresse, cpAdresse, localiteAdresse, rueAdresse, numAdresse);

                    Location loc = new Location(idLoc, dateloc, kmTotalLoc, client, adresse);

                    List<Facturation> facs = getFacturations(loc);
                    loc.setFacturation(facs);

                    locations.add(loc);

                } while (rs.next());


                client.setLocations(locations);
                return client;

            } else {
                System.err.println("Record introuvable lors du read");
            }
        } catch (SQLException e) {
            System.err.println("Erreur sql lors du read : " + e);
        } catch (Exception e) {
            System.err.println("Erreur Builder lors du read : " + e);
        }

        return null;

    }

    private List<Facturation> getFacturations(Location loc) {
        String query = "SELECT * FROM API_FACTURESLOCATION WHERE ID_LOCATION = ?";

        try (PreparedStatement req = dbConnect.prepareStatement(query)) {
            req.setInt(1, loc.getId());
            ResultSet rs = req.executeQuery();

            List<Facturation> facs = new ArrayList<>();

            if (rs.next()) {
                //le if permet de savoir si il y a une facturation ou pas pour une location
                do {


                    double cout = rs.getInt(1);
                    int idTaxi = rs.getInt(2);
                    String immatriculation = rs.getString(3);
                    String carburant = rs.getString(4);
                    double prixKm = rs.getDouble(5);

                    Taxi taxi = new Taxi(idTaxi, immatriculation, carburant, prixKm);

                    Facturation fac = new Facturation(cout, taxi);

                    facs.add(fac);

                } while (rs.next());
            }


            return facs;
        } catch (SQLException e) {
            System.err.println("Erreur sql lors du getFacturations: " + e);
        } catch (Exception e) {
            System.err.println("Erreur builder lors du getFacturations : " + e);
        }

        return null;
    }

    public Set<Adresse> adresseLocationSansDoublon(Client client) {
        return client.adresseLocationSansDoublon();
    }
}