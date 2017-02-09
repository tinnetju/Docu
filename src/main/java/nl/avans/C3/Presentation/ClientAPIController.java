package nl.avans.C3.Presentation;

import java.util.List;
import nl.avans.C3.BusinessLogic.ClientService;
import nl.avans.C3.Domain.Client;
import nl.avans.C3.Domain.ClientAPIResponse;
import nl.avans.C3.Domain.ClientNotFoundException;
import nl.avans.C3.Domain.Insurance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Tinne
 */
@RestController
public class ClientAPIController {
    private ClientService clientService;
    
    @Autowired
    public ClientAPIController(ClientService clientService) {
        this.clientService = clientService;
    }
    
    @RequestMapping(value = "/api/viewclient/{BSN}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE}) //XML is ook mogelijk met MediaType.APPLICATION_XML_VALUE
    public ClientAPIResponse getClientByBSN(@PathVariable int BSN, ModelMap model) throws ClientNotFoundException {

        ClientAPIResponse clientAPIResponse = null;
        try {
            clientAPIResponse = new ClientAPIResponse(clientService.findClientByBSN(BSN));
        } catch(ClientNotFoundException ex){
            //logger.error(ex.getMessage());
        }

        return clientAPIResponse;
    }
}
