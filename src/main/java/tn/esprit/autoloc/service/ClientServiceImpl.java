package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IClientRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientServiceImpl implements IClientService {
    private final IClientRepository clientRepository;

    @Override
    public Client create(Client client) {
        ServiceValidation.requireNewEntity(client, client == null ? null : client.getIdClient(), "client");
        validateIdentity(client);
        return clientRepository.save(client);
    }

    @Override
    public Client findById(Long id) {
        ServiceValidation.requireId(id, "le client");
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client", id));
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client update(Long id, Client client) {
        ServiceValidation.requireUpdateEntity(client, "Le client");
        Client existing = findById(id);
        validateIdentity(client);
        existing.setNom(client.getNom());
        existing.setPrenom(client.getPrenom());
        existing.setEmail(client.getEmail());
        existing.setTelephone(client.getTelephone());
        existing.setNumPermis(client.getNumPermis());
        existing.setDateInscription(client.getDateInscription());
        return clientRepository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        ServiceValidation.requireId(id, "le client");
        if (!clientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Client", id);
        }
        clientRepository.deleteById(id);
    }

    private void validateIdentity(Client client) {
        ServiceValidation.requireText(client.getNom(), "Le nom du client");
        ServiceValidation.requireText(client.getEmail(), "L'e-mail du client");
    }
}
