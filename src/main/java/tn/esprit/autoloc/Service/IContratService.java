package tn.esprit.autoloc.Service;

import tn.esprit.autoloc.domain.Contrat;

import java.util.List;

public interface IContratService {

    Contrat findById(Long id);

    Contrat getById(Long id);

    List<Contrat> getAll();

    Contrat add(Contrat contrat);

    Contrat update(Long id, Contrat contrat);

    void delete(Long id);
}
