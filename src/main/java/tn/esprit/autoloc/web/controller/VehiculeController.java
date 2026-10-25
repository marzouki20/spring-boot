package tn.esprit.autoloc.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autoloc.Service.IVehiculeService;
import tn.esprit.autoloc.domain.Vehicule;

import java.util.List;

@RestController
@RequestMapping("/api/vehicules")
public class VehiculeController {

    private final IVehiculeService vehiculeService;

    public VehiculeController(IVehiculeService vehiculeService) {
        this.vehiculeService = vehiculeService;
    }

    @PostMapping("/add")
    public Vehicule add(@RequestBody Vehicule vehicule) {
        return vehiculeService.add(vehicule);
    }

    @GetMapping
    public List<Vehicule> getAll() {
        return vehiculeService.getAll();
    }

    @GetMapping("/{id}")
    public Vehicule getById(@PathVariable Long id) {
        return vehiculeService.getById(id);
    }

    @PutMapping("/{id}")
    public Vehicule update(@PathVariable Long id, @RequestBody Vehicule vehicule) {
        return vehiculeService.update(id, vehicule);
    }

    @DeleteMapping("/{id}")
    @org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        vehiculeService.delete(id);
    }
}