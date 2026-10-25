package tn.esprit.autoloc.Service;

import tn.esprit.autoloc.domain.Equipement;

import java.util.List;

public interface IEquipementService {

    Equipement findById(Long id);

    Equipement getById(Long id);

    List<Equipement> getAll();

    Equipement add(Equipement equipement);

    Equipement update(Long id, Equipement equipement);

    void delete(Long id);
}
