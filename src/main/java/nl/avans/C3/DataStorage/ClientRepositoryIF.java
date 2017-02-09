/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.avans.C3.DataStorage;

import java.util.List;
import java.util.Map;
import nl.avans.C3.Domain.Client;

/**
 *
 * @author Tinne
 */
public interface ClientRepositoryIF {
    public List<Client> findAll();

    public Client findClientByBSN(int BSN);
    
    public List<Client> findClientsByFirstName(String firstName);
    
    public List<Client> findClientsByLastName(String lastName);
    
    public List<Client> findClientsByEmailAddress(String emailAddress);
    
    public List<Client> findClientsByBSN(String bSN);

    public Client create(final Client client);
    
    public void update(final Client client);
    
    public void deleteClientByBSN(int BSN);
}
