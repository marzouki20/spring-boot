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
import tn.esprit.autoloc.Service.IAgenceService;
import tn.esprit.autoloc.domain.agence;

import java.util.List;

@RestController
@RequestMapping("/api/agences")
public class AgenceController {

    private final IAgenceService agenceService;

    public AgenceController(IAgenceService agenceService) {
        this.agenceService = agenceService;
    }

    @PostMapping("/add")
    public agence add(@RequestBody agence agence) {
        return agenceService.add(agence);
    }

    @GetMapping
    public List<agence> getAll() {
        return agenceService.getAll();
    }

    @GetMapping("/{id}")
    public agence getById(@PathVariable Long id) {
        return agenceService.getById(id);
    }

    @PutMapping("/{id}")
    public agence update(@PathVariable Long id, @RequestBody agence agence) {
        return agenceService.update(id, agence);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        agenceService.delete(id);
    }
}
