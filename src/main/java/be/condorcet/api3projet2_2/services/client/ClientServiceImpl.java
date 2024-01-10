package be.condorcet.api3projet2_2.services.client;


import be.condorcet.api3projet2_2.entities.Adresse;
import be.condorcet.api3projet2_2.entities.Client;
import be.condorcet.api3projet2_2.entities.Location;
import be.condorcet.api3projet2_2.entities.Taxi;
import be.condorcet.api3projet2_2.repositories.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional(rollbackOn = Exception.class)
public class ClientServiceImpl implements InterfClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Client create(Client client) throws Exception {
        clientRepository.save(client);
        return client;
    }

    @Override
    public Client read(Integer id) throws Exception {
        Optional<Client> ocl = clientRepository.findById(id);
        return ocl.get();
    }

    @Override
    public Client update(Client client) throws Exception {
        read(client.getId());
        clientRepository.save(client);
        return client;
    }

    @Override
    public void delete(Client client) throws Exception {
        clientRepository.deleteById(client.getId());
    }

    @Override
    public List<Client> all() throws Exception {
        return clientRepository.findAll();
    }

    @Override
    public Page<Client> allp(Pageable pageable) throws Exception {
        return clientRepository.findAll(pageable);
    }

    @Override
    public List<Client> read(String nom) throws Exception {
        return clientRepository.findByNomLike(nom + "%");
    }

    @Override
    public Client read(String nom, String prenom, String tel) throws Exception {
        return clientRepository.findByNomAndPrenomAndTel(nom, prenom, tel);
    }

    @Override
    public List<Taxi> taxiUtiliseSansDoublon(Integer idClient) throws Exception {
        return clientRepository.taxiUtiliseSansDoublon(idClient);
    }

    @Override
    public List<Adresse> adresseLocationSansDoublon(Integer idClient) throws Exception {
        return clientRepository.adresseLocationSansDoublon(idClient);
    }

    //Examen Q1
    @Override
    public Integer getKmParcouruByClient(Integer idClient) throws Exception {
        return clientRepository.getKmParcouruByClient(idClient);
    }

}
