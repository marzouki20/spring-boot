package tn.esprit.autoloc.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.domain.Client;

public interface IClientRepository extends JpaRepository<Client, Long> {
}
