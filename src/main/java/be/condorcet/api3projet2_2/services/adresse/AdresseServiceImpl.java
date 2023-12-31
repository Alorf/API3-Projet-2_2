package be.condorcet.api3projet2_2.services.adresse;


import be.condorcet.api3projet2_2.entities.Adresse;
import be.condorcet.api3projet2_2.entities.Client;
import be.condorcet.api3projet2_2.repositories.AdresseRepository;
import be.condorcet.api3projet2_2.repositories.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackOn = Exception.class)
public class AdresseServiceImpl implements InterfAdresseService {
    @Autowired
    private AdresseRepository adresseRepository;


    @Override
    public Adresse create(Adresse adresse) throws Exception {
        adresseRepository.save(adresse);
        return adresse;
    }

    @Override
    public Adresse read(Integer id) throws Exception {
        Optional<Adresse> ocl= adresseRepository.findById(id);
        return ocl.get();
    }

    @Override
    public Adresse update(Adresse adresse) throws Exception {
        read(adresse.getId());
        adresseRepository.save(adresse);
        return adresse;
    }

    @Override
    public void delete(Adresse adresse) throws Exception {
        adresseRepository.deleteById(adresse.getId());
    }

    @Override
    public List<Adresse> all() throws Exception {
        return adresseRepository.findAll();
    }

    @Override
    public Page<Adresse> allp(Pageable pageable) throws Exception {
        return adresseRepository.findAll(pageable);
    }

    @Override
    public List<Adresse> read(String localite) throws Exception {
        return adresseRepository.findByLocalite(localite);
    }


}
