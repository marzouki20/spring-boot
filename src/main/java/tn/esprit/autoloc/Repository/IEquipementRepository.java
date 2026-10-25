package tn.esprit.autoloc.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.domain.Equipement;

public interface IEquipementRepository extends JpaRepository<Equipement, Long> {
}
