package tn.esprit.autoloc.Service.impls;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.Repository.IEquipementRepository;
import tn.esprit.autoloc.Service.IEquipementService;
import tn.esprit.autoloc.domain.Equipement;

import java.util.List;

@Service
public class EquipementServiceImpl implements IEquipementService {
    private final IEquipementRepository equipementRepository;

    public EquipementServiceImpl(IEquipementRepository equipementRepository) {
        this.equipementRepository = equipementRepository;
    }

    @Override
    public Equipement findById(Long id) {
        return equipementRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Equipment not found: " + id));
    }

    @Override
    public Equipement getById(Long id) {
        return findById(id);
    }

    @Override
    public List<Equipement> getAll() {
        return equipementRepository.findAll();
    }

    @Override
    public Equipement add(Equipement equipement) {
        return equipementRepository.save(equipement);
    }

    @Override
    public Equipement update(Long id, Equipement equipement) {
        Equipement existingEquipement = findById(id);
        existingEquipement.setLibelle(equipement.getLibelle());
        existingEquipement.setVehicules(equipement.getVehicules());

        return equipementRepository.save(existingEquipement);
    }

    @Override
    public void delete(Long id) {
        Equipement existingEquipement = findById(id);
        equipementRepository.delete(existingEquipement);
    }
}
