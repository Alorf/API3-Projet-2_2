package be.condorcet.api3projet2_2.services.facture;


import be.condorcet.api3projet2_2.entities.*;
import be.condorcet.api3projet2_2.entities.Facture;
import be.condorcet.api3projet2_2.repositories.FactureRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackOn = Exception.class)
public class FactureServiceImpl implements InterfFactureService {
    @Autowired
    private FactureRepository factureRepository;

    @Override
    public Facture create(Facture facture) throws Exception {
        System.out.println(facture);
        factureRepository.save(facture);
        return facture;
    }

    @Override
    public Facture read(Integer id) throws Exception {
        return null;
    }

    @Override
    public Facture read(FactureKey id) throws Exception {
        return factureRepository.findById(id).get();
    }

    @Override
    public Page<Facture> allp(Pageable pageable, Location loc) throws Exception {
        return factureRepository.findAllByLocation(loc, pageable);
    }

    @Override
    public Facture update(Facture facture) throws Exception {
        read(facture.getId());
        factureRepository.save(facture);
        return facture;
    }

    @Override
    public void delete(Facture facture) throws Exception {
        factureRepository.deleteById(facture.getId());
    }

    @Override
    public List<Facture> all() throws Exception {
        return factureRepository.findAll();
    }

    @Override
    public Page<Facture> allp(Pageable pageable) throws Exception {
        return factureRepository.findAll(pageable);
    }

    @Override
    public List<Facture> read(Taxi taxi) throws Exception {
        return factureRepository.findByTaxi(taxi);
    }

    @Override
    public List<Facture> read(Location loc) throws Exception {
        return factureRepository.findByLocation(loc);
    }

}
