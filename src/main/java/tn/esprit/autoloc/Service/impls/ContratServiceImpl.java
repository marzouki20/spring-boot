package tn.esprit.autoloc.Service.impls;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.Repository.IContratRepository;
import tn.esprit.autoloc.Service.IContratService;
import tn.esprit.autoloc.domain.Contrat;

import java.util.List;

@Service
public class ContratServiceImpl implements IContratService {
    private final IContratRepository contratRepository;

    public ContratServiceImpl(IContratRepository contratRepository) {
        this.contratRepository = contratRepository;
    }

    @Override
    public Contrat findById(Long id) {
        return contratRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Contract not found: " + id));
    }

    @Override
    public Contrat getById(Long id) {
        return findById(id);
    }

    @Override
    public List<Contrat> getAll() {
        return contratRepository.findAll();
    }

    @Override
    public Contrat add(Contrat contrat) {
        return contratRepository.save(contrat);
    }

    @Override
    public Contrat update(Long id, Contrat contrat) {
        Contrat existingContrat = findById(id);
        existingContrat.setDateSignature(contrat.getDateSignature());
        existingContrat.setMontantTotal(contrat.getMontantTotal());
        existingContrat.setValide(contrat.isValide());
        existingContrat.setReservation(contrat.getReservation());

        return contratRepository.save(existingContrat);
    }

    @Override
    public void delete(Long id) {
        Contrat existingContrat = findById(id);
        contratRepository.delete(existingContrat);
    }
}
