package tn.esprit.autoloc.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.domain.Contrat;

public interface IContratRepository extends JpaRepository<Contrat, Long> {
}
