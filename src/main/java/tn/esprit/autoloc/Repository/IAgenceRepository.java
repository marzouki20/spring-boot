package tn.esprit.autoloc.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.domain.agence;

public interface IAgenceRepository extends JpaRepository<agence, Long> {
}
