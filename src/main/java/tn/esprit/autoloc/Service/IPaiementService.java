package tn.esprit.autoloc.Service;

import tn.esprit.autoloc.domain.Paiement;

import java.util.List;

public interface IPaiementService {

    Paiement findById(Long id);

    Paiement getById(Long id);

    List<Paiement> getAll();

    Paiement add(Paiement paiement);

    Paiement update(Long id, Paiement paiement);

    void delete(Long id);
}
