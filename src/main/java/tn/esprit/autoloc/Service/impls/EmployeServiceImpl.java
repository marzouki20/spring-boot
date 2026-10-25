package tn.esprit.autoloc.Service.impls;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.Repository.IEmployeRepository;
import tn.esprit.autoloc.Service.IEmployeService;
import tn.esprit.autoloc.domain.Employe;

import java.util.List;

@Service
public class EmployeServiceImpl implements IEmployeService {
    private final IEmployeRepository employeRepository;

    public EmployeServiceImpl(IEmployeRepository employeRepository) {
        this.employeRepository = employeRepository;
    }

    @Override
    public Employe findById(Long id) {
        return employeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found: " + id));
    }

    @Override
    public Employe getById(Long id) {
        return findById(id);
    }

    @Override
    public List<Employe> getAll() {
        return employeRepository.findAll();
    }

    @Override
    public Employe add(Employe employe) {
        return employeRepository.save(employe);
    }

    @Override
    public Employe update(Long id, Employe employe) {
        Employe existingEmploye = findById(id);
        existingEmploye.setRole(employe.getRole());
        existingEmploye.setAgence(employe.getAgence());

        return employeRepository.save(existingEmploye);
    }

    @Override
    public void delete(Long id) {
        Employe employe = findById(id);
        employeRepository.delete(employe);
    }
}
