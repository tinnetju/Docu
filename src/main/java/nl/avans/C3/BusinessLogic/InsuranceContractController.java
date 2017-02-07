package nl.avans.C3.BusinessLogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.swing.table.DefaultTableModel;
import nl.avans.C3.Domain.Client;
import nl.avans.C3.Domain.ClientNotFoundException;
import nl.avans.C3.Domain.Insurance;
import nl.avans.C3.Domain.InsuranceContract;
import nl.avans.C3.Domain.InsuranceContractNotFoundException;
import nl.avans.C3.Domain.InsuranceNotFoundException;
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
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 *
 * @author Tinne
 */

@Controller
public class InsuranceContractController {
    private InsuranceContractService insuranceContractService;
    private ClientService clientService;
    
    @Autowired
    public InsuranceContractController(InsuranceContractService insuranceContractService, ClientService clientService) {
        this.insuranceContractService = insuranceContractService;
        this.clientService = clientService;
    }
    
    @RequestMapping(value = "/client/viewcontracts/{BSN}", method = RequestMethod.GET)
    public String getInsuranceContractsByBSN(@PathVariable int BSN, ModelMap model) throws InsuranceContractNotFoundException {
        Client client = null;
        try {
            client = clientService.findClientByBSN(BSN);
        } catch(ClientNotFoundException ex){
            //logger.error(ex.getMessage());
        }
        
        try {
            client.setContracts((ArrayList<InsuranceContract>) insuranceContractService.getContractsByBSN(BSN));
        } catch(InsuranceContractNotFoundException ex){
            //logger.error(ex.getMessage());
        }

        model.addAttribute("client", client);

        return "views/client/viewcontracts";
    }
}
