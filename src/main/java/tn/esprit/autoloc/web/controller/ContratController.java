package tn.esprit.autoloc.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autoloc.Service.IContratService;
import tn.esprit.autoloc.domain.Contrat;

import java.util.List;

@RestController
@RequestMapping("/api/contrats")
public class ContratController {

    private final IContratService contratService;

    public ContratController(IContratService contratService) {
        this.contratService = contratService;
    }

    @PostMapping("/add")
    public Contrat add(@RequestBody Contrat contrat) {
        return contratService.add(contrat);
    }

    @GetMapping
    public List<Contrat> getAll() {
        return contratService.getAll();
    }

    @GetMapping("/{id}")
    public Contrat getById(@PathVariable Long id) {
        return contratService.getById(id);
    }

    @PutMapping("/{id}")
    public Contrat update(@PathVariable Long id, @RequestBody Contrat contrat) {
        return contratService.update(id, contrat);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        contratService.delete(id);
    }
}
