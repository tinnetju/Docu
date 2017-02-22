package nl.avans.C3.Presentation;

/**
 *
 * @author Tinne
 */

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.validation.Valid;
import nl.avans.C3.BusinessLogic.ClientService;
import nl.avans.C3.Domain.Client;
import nl.avans.C3.Domain.ClientNotFoundException;
import nl.avans.C3.Domain.Insurance;
import nl.avans.C3.Domain.SearchQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class ClientController {
    private ClientService clientService;
   
   
    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }
    
    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    public String Index(ModelMap model) {
        List<Client> clients = clientService.findAllClients();
        
        InitializeSearchOptions(model);
        SearchQuery searchquery = new SearchQuery();
        
        model.addAttribute("clients", clients);
        model.addAttribute("searchquery", searchquery);
        
        return "views/client/clients";
    }
    
    @RequestMapping(value = "/clients", method = RequestMethod.POST)
    public String Index(@Valid SearchQuery searchquery, final BindingResult bindingResult, final ModelMap model) {
        List<Client> clients = new ArrayList<>();
        
        if(searchquery.getSearchWords().length() > 0)
        {
            try {
                switch (searchquery.getSearchOption()) {
                    case "Voornaam":
                        clients = clientService.findClientsByFirstName(searchquery.getSearchWords());
                        break;
                    case "Achternaam":
                        clients = clientService.findClientsByLastname(searchquery.getSearchWords());
                        break;
                    case "E-mailadres":
                        clients = clientService.findClientsByEmailAddress(searchquery.getSearchWords());
                        break;
                }
            } catch(ClientNotFoundException ex){
                //logger.error(ex.getMessage());
            }
        }
        else
        {
            clients = clientService.findAllClients();
        }
        InitializeSearchOptions(model);
        model.addAttribute("clients", clients);
        model.addAttribute("searchquery", searchquery);
        return "views/client/clients";
    }

    private void InitializeSearchOptions(ModelMap model)
    {
        ArrayList<String> searchOptions = new ArrayList<>();
        searchOptions.add("Voornaam");
        searchOptions.add("Achternaam");
        searchOptions.add("E-mailadres");
        
        model.addAttribute("searchOptions", searchOptions);
    }
    
    @RequestMapping(value = "/client/viewclient/{BSN}", method = RequestMethod.GET)
    public String getClientByBSN(@PathVariable int BSN, ModelMap model) throws ClientNotFoundException {

        Client client = null;
        try {
            client = clientService.findClientByBSN(BSN);
        } catch(ClientNotFoundException ex){
            //logger.error(ex.getMessage());
        }
        
        List<Insurance> insurances = clientService.getInsurancesForClient(BSN);

        model.addAttribute("client", client);
        return "views/client/viewclient";
    }
    
    @RequestMapping(value = "/client/create", method = RequestMethod.GET)
    public String createClient(ModelMap model) {
        Client client = new Client();
        
        model.addAttribute("client", client);
        return "views/client/create";
    }
    
    @RequestMapping(value="/client/create", method = RequestMethod.POST)
    public String validateAndSaveClient(@Valid Client client, final BindingResult bindingResult, final ModelMap model) {
        if (bindingResult.hasErrors()) {
            return "views/client/create";
        }
        
        Client newClient = null;
        try {
            newClient = clientService.create(client);
        } catch(Exception ex){
            model.addAttribute("info", "Cliënt kon niet gemaakt worden (mogelijk bestaat er al een cliënt met dit burgerservicenummer.");
            return "views/client/create";
            //logger.error(ex.getMessage());
        }
        
        if(newClient != null) {
            model.addAttribute("info", "Cliënt '" + newClient.getFirstName() + " " + newClient.getLastName() + "' is toegevoegd.");
        } else {
            model.addAttribute("info", "Cliënt kon niet gemaakt worden.");
            return "views/client/create";
        }
        
        return "redirect:/clients";
    }
    
    @RequestMapping(value = "/client/edit/{BSN}", method = RequestMethod.GET)
    public String editClient(@PathVariable int BSN, ModelMap model) {
        Client client = null;
        try {
            client = clientService.findClientByBSN(BSN);
        } catch(ClientNotFoundException ex){
            //logger.error(ex.getMessage());
        }
        
        model.addAttribute("client", client);
        return "views/client/edit";
    }
    
    @RequestMapping(value="/client/edit", method = RequestMethod.POST)
    public String validateAndEditClient(@Valid Client client, final BindingResult bindingResult, final ModelMap model) {
        if (bindingResult.hasErrors()) {
            return "views/client/edit";
        }
        
        try {
            clientService.update(client);
            model.addAttribute("info", "Cliënt '" + client.getFirstName() + " " + client.getLastName() + "' is gewijzigd.");
        } catch(Exception e){
            model.addAttribute("info", "Cliënt kon niet worden gewijzigd");
            return "views/client/edit";
        }

        return "redirect:/clients";
    }
    
    
    @RequestMapping(value = "/client/delete/{BSN}", method = RequestMethod.GET)
    public String deleteClient(@PathVariable int BSN, ModelMap model) {
        Client client = null;
        try {
            client = clientService.findClientByBSN(BSN);
            clientService.delete(BSN);
            model.addAttribute("info", "Cliënt '" + client.getFirstName() + " " + client.getLastName() + "' is verwijderd.");
        } catch(Exception e){
             model.addAttribute("info", "Cliënt kon niet worden verwijderd, mogelijk heeft deze cliënt nog contracten lopen.");
        }
        
        InitializeSearchOptions(model);
        SearchQuery searchquery = new SearchQuery();
        model.addAttribute("clients", clientService.findAllClients());
        model.addAttribute("searchquery", searchquery);
        return "forward:/clients";
    }
}

