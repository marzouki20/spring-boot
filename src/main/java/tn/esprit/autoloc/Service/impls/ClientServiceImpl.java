package tn.esprit.autoloc.Service.impls;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.Repository.IClientRepository;
import tn.esprit.autoloc.Service.IClientService;
import tn.esprit.autoloc.domain.Client;

import java.util.List;

@Service
public class ClientServiceImpl implements IClientService {
    private final IClientRepository clientRepository;

    public ClientServiceImpl(IClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client findById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client not found: " + id));
    }

    @Override
    public Client getById(Long id) {
        return findById(id);
    }

    @Override
    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client add(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Client update(Long id, Client client) {
        Client existingClient = findById(id);
        existingClient.setNom(client.getNom());
        existingClient.setPrenom(client.getPrenom());
        existingClient.setEmail(client.getEmail());
        existingClient.setTelephone(client.getTelephone());
        existingClient.setNumPermis(client.getNumPermis());
        existingClient.setDateInscription(client.getDateInscription());

        return clientRepository.save(existingClient);
    }

    @Override
    public void delete(Long id) {
        Client existingClient = findById(id);
        clientRepository.delete(existingClient);
    }
}
