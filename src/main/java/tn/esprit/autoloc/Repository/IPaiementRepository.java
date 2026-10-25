package tn.esprit.autoloc.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.domain.Paiement;

public interface IPaiementRepository extends JpaRepository<Paiement, Long> {
}
