package tn.esprit.autoloc.Service;

import tn.esprit.autoloc.domain.Client;

import java.util.List;

public interface IClientService {

    Client findById(Long id);

    Client getById(Long id);

    List<Client> getAll();

    Client add(Client client);

    Client update(Long id, Client client);

    void delete(Long id);
}
