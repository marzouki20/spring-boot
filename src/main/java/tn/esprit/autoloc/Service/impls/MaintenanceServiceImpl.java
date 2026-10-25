package tn.esprit.autoloc.Service.impls;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.Repository.IMaintenanceRepository;
import tn.esprit.autoloc.Service.IMaintenanceService;
import tn.esprit.autoloc.domain.Maintenance;

import java.util.List;

@Service
public class MaintenanceServiceImpl implements IMaintenanceService {
    private final IMaintenanceRepository maintenanceRepository;

    public MaintenanceServiceImpl(IMaintenanceRepository maintenanceRepository) {
        this.maintenanceRepository = maintenanceRepository;
    }

    @Override
    public Maintenance findById(Long id) {
        return maintenanceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Maintenance not found: " + id));
    }

    @Override
    public Maintenance getById(Long id) {
        return findById(id);
    }

    @Override
    public List<Maintenance> getAll() {
        return maintenanceRepository.findAll();
    }

    @Override
    public Maintenance add(Maintenance maintenance) {
        return maintenanceRepository.save(maintenance);
    }

    @Override
    public Maintenance update(Long id, Maintenance maintenance) {
        Maintenance existingMaintenance = findById(id);
        existingMaintenance.setDateDebut(maintenance.getDateDebut());
        existingMaintenance.setDateFin(maintenance.getDateFin());
        existingMaintenance.setDescription(maintenance.getDescription());
        existingMaintenance.setVehicule(maintenance.getVehicule());

        return maintenanceRepository.save(existingMaintenance);
    }

    @Override
    public void delete(Long id) {
        Maintenance existingMaintenance = findById(id);
        maintenanceRepository.delete(existingMaintenance);
    }
}
