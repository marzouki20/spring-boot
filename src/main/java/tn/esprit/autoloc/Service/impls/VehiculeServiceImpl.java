package tn.esprit.autoloc.Service.impls;

import tn.esprit.autoloc.Repository.IVehiculeRepository;
import tn.esprit.autoloc.Service.IVehiculeService;
import tn.esprit.autoloc.domain.Vehicule;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehiculeServiceImpl  implements IVehiculeService {
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
        return null;
    }

    @Override
    public List<Vehicule> getAll() {
        return List.of();
    }

    @Override
    public Vehicule add(Vehicule vehicule) {
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public Vehicule update(Long id, Vehicule vehicule) {
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public void delete(Long id) {

    }
}
