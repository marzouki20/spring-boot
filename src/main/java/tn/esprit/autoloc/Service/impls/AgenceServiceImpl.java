package tn.esprit.autoloc.Service.impls;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.Repository.IAgenceRepository;
import tn.esprit.autoloc.Service.IAgenceService;
import tn.esprit.autoloc.domain.agence;

import java.util.List;

@Service
public class AgenceServiceImpl implements IAgenceService {
    private final IAgenceRepository agenceRepository;

    public AgenceServiceImpl(IAgenceRepository agenceRepository) {
        this.agenceRepository = agenceRepository;
    }

    @Override
    public agence findById(Long id) {
        return agenceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Agency not found: " + id));
    }

    @Override
    public agence getById(Long id) {
        return findById(id);
    }

    @Override
    public List<agence> getAll() {
        return agenceRepository.findAll();
    }

    @Override
    public agence add(agence agence) {
        return agenceRepository.save(agence);
    }

    @Override
    public agence update(Long id, agence agence) {
        agence existingAgence = findById(id);
        existingAgence.setNom(agence.getNom());
        existingAgence.setVille(agence.getVille());
        existingAgence.setAdresse(agence.getAdresse());
        existingAgence.setTelephone(agence.getTelephone());

        return agenceRepository.save(existingAgence);
    }

    @Override
    public void delete(Long id) {
        agence existingAgence = findById(id);
        agenceRepository.delete(existingAgence);
    }
}
