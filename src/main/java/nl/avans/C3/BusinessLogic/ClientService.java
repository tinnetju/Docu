package nl.avans.C3.BusinessLogic;

/**
 *
 * @author Tinne
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import nl.avans.C3.DataStorage.ClientRepository;
import nl.avans.C3.DataStorage.ClientRepositoryIF;
import nl.avans.C3.Domain.Client;
import nl.avans.C3.Domain.ClientNotFoundException;
import nl.avans.C3.Domain.Insurance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;


@Service
public class ClientService {
    private ClientRepositoryIF clientRepository;
    //private InsuranceService insuranceService;
    
    /*@Autowired
    public void setInsuranceService(InsuranceService insuranceService) {
        this.insuranceService = insuranceService;
    }*/
   
    @Autowired
    public void setClientRepository(ClientRepositoryIF clientRepositoryIF) {
        this.clientRepository = clientRepositoryIF;
    }
    
    public List<Client> findAllClients() {
        return clientRepository.findAll();
    }
    
    public Client findClientByBSN(int BSN) throws ClientNotFoundException {
        Client client = null;

        client = clientRepository.findClientByBSN(BSN);
        if(client.equals(null)) {
            throw new ClientNotFoundException("Exception!");
        } else {
            return client;
        }
    }
    
    public List<Client> findClientsByFirstName(String firstName) throws ClientNotFoundException {
        List<Client> clients = null;

        clients = clientRepository.findClientsByFirstName(firstName);
        if(clients.equals(null) || clients.size() < 1) {
            throw new ClientNotFoundException("Exception!");
        } else {
            return clients;
        }
    }
    
    public List<Client> findClientsByLastname(String lastName) throws ClientNotFoundException {
        List<Client> clients = null;

        clients = clientRepository.findClientsByLastName(lastName);
        if(clients.equals(null) || clients.size() < 1) {
            throw new ClientNotFoundException("Exception!");
        } else {
            return clients;
        }
    }
    
    public List<Client> findClientsByEmailAddress(String emailAddress) throws ClientNotFoundException {
        List<Client> clients = null;

        clients = clientRepository.findClientsByEmailAddress(emailAddress);
        if(clients.equals(null) || clients.size() < 1) {
            throw new ClientNotFoundException("Exception!");
        } else {
            return clients;
        }
    }
    
    public List<Client> findClientsByBSN(String bSN) throws ClientNotFoundException {
        List<Client> clients = null;

        clients = clientRepository.findClientsByBSN(bSN);
        if(clients.equals(null) || clients.size() < 1) {
            throw new ClientNotFoundException("Exception!");
        } else {
            return clients;
        }
    }
    
    public Client create(final Client client)  {
        return clientRepository.create(client);
    }
    
    public void update(final Client client)  {
        clientRepository.update(client);
    }
    
    public void delete(final int BSN)  {
        clientRepository.deleteClientByBSN(BSN);
    }
    
    public List<Insurance> getInsurancesForClient(int BSN) throws ClientNotFoundException
    {
        Client client = null;
        List<Insurance> insurances = null; //set in else 
        
        client = clientRepository.findClientByBSN(BSN);
        if(client.equals(null)) {
            throw new ClientNotFoundException("Exception!");
        } else {
            return insurances;
        }
    }
}
