package tn.esprit.autoloc.Service.impls;

import tn.esprit.autoloc.Repository.IVehiculeRepository;
import tn.esprit.autoloc.Service.IVehiculeService;
import tn.esprit.autoloc.domain.Vehicule;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehiculeServiceImpl implements IVehiculeService {
    private final IVehiculeRepository vehiculeRepository;

    public VehiculeServiceImpl(IVehiculeRepository iVehiculeRepository) {
        vehiculeRepository = iVehiculeRepository;
    }

    @Override
    public Vehicule findById(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found: " + id));
    }

    @Override
    public Vehicule getById(Long id) {
        return findById(id);
    }

    @Override
    public List<Vehicule> getAll() {
        return vehiculeRepository.findAll();
    }

    @Override
    public Vehicule add(Vehicule vehicule) {
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public Vehicule update(Long id, Vehicule vehicule) {
        Vehicule existingVehicule = findById(id);

        existingVehicule.setImmatriculation(vehicule.getImmatriculation());
        existingVehicule.setMarque(vehicule.getMarque());
        existingVehicule.setModele(vehicule.getModele());
        existingVehicule.setCategorie(vehicule.getCategorie());
        existingVehicule.setTarifJournalier(vehicule.getTarifJournalier());
        existingVehicule.setStatut(vehicule.getStatut());
        existingVehicule.setAgence(vehicule.getAgence());
        existingVehicule.setEquipements(vehicule.getEquipements());

        return vehiculeRepository.save(existingVehicule);
    }

    @Override
    public void delete(Long id) {
        Vehicule vehicule = findById(id);
        vehiculeRepository.delete(vehicule);
    }
}
