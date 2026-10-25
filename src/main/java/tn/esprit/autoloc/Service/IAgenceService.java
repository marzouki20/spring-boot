package tn.esprit.autoloc.Service;

import tn.esprit.autoloc.domain.agence;

import java.util.List;

public interface IAgenceService {

    agence findById(Long id);

    agence getById(Long id);

    List<agence> getAll();

    agence add(agence agence);

    agence update(Long id, agence agence);

    void delete(Long id);
}
