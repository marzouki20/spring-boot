package tn.esprit.autoloc.Service;

import tn.esprit.autoloc.domain.Vehicule;

import java.util.List;

public interface IVehiculeService {

    Vehicule findById(Long id);

    Vehicule getById(Long id);

    List<Vehicule> getAll();

    Vehicule add(Vehicule vehicule);

    Vehicule update(Long id, Vehicule vehicule);

    void delete(Long id);
}