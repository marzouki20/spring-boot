package tn.esprit.autoloc.Service;

import tn.esprit.autoloc.domain.Employe;

import java.util.List;

public interface IEmployeService {

    Employe findById(Long id);

    Employe getById(Long id);

    List<Employe> getAll();

    Employe add(Employe employe);

    Employe update(Long id, Employe employe);

    void delete(Long id);
}
