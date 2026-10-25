package tn.esprit.autoloc.Service.impls;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.Repository.IPaiementRepository;
import tn.esprit.autoloc.Service.IPaiementService;
import tn.esprit.autoloc.domain.Paiement;

import java.util.List;

@Service
public class PaiementServiceImpl implements IPaiementService {
    private final IPaiementRepository paiementRepository;

    public PaiementServiceImpl(IPaiementRepository paiementRepository) {
        this.paiementRepository = paiementRepository;
    }

    @Override
    public Paiement findById(Long id) {
        return paiementRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found: " + id));
    }

    @Override
    public Paiement getById(Long id) {
        return findById(id);
    }

    @Override
    public List<Paiement> getAll() {
        return paiementRepository.findAll();
    }

    @Override
    public Paiement add(Paiement paiement) {
        return paiementRepository.save(paiement);
    }

    @Override
    public Paiement update(Long id, Paiement paiement) {
        Paiement existingPaiement = findById(id);
        existingPaiement.setMontant(paiement.getMontant());
        existingPaiement.setDatePaiement(paiement.getDatePaiement());
        existingPaiement.setModePaiement(paiement.getModePaiement());
        existingPaiement.setContrat(paiement.getContrat());

        return paiementRepository.save(existingPaiement);
    }

    @Override
    public void delete(Long id) {
        Paiement existingPaiement = findById(id);
        paiementRepository.delete(existingPaiement);
    }
}
