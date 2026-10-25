package tn.esprit.autoloc.Service;

import tn.esprit.autoloc.domain.Maintenance;

import java.util.List;

public interface IMaintenanceService {

    Maintenance findById(Long id);

    Maintenance getById(Long id);

    List<Maintenance> getAll();

    Maintenance add(Maintenance maintenance);

    Maintenance update(Long id, Maintenance maintenance);

    void delete(Long id);
}
