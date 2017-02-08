package nl.avans.C3.BusinessLogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.swing.table.DefaultTableModel;
import javax.validation.Valid;
import nl.avans.C3.Domain.Client;
import nl.avans.C3.Domain.ClientNotFoundException;
import nl.avans.C3.Domain.Insurance;
import nl.avans.C3.Domain.InsuranceContract;
import nl.avans.C3.Domain.InsuranceContractNotFoundException;
import nl.avans.C3.Domain.InsuranceNotFoundException;
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
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Tinne
 */

@Controller
public class InsuranceContractController {
    private InsuranceContractService insuranceContractService;
    private ClientService clientService;
    private InsuranceService insuranceService;
    private InsuranceTypeService insuranceTypeService;
    
    @Autowired
    public InsuranceContractController(InsuranceContractService insuranceContractService, ClientService clientService, InsuranceService insuranceService, InsuranceTypeService insuranceTypeService) {
        this.insuranceContractService = insuranceContractService;
        this.clientService = clientService;
        this.insuranceService = insuranceService;
        this.insuranceTypeService = insuranceTypeService;
    }
    
    @RequestMapping(value = "insuranceContract/viewcontracts/{BSN}", method = RequestMethod.GET)
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
       
        for (InsuranceContract currentInsuranceContract : client.getContracts()) {
            try {
                currentInsuranceContract.setInsurance(insuranceService.findInsuranceById(currentInsuranceContract.getInsuranceID()));
                currentInsuranceContract.setInsuranceType(insuranceTypeService.findInsuranceTypeById(currentInsuranceContract.getInsuranceTypeID()));
            } catch(InsuranceNotFoundException ex){
                //logger.error(ex.getMessage());
            }
        }

        model.addAttribute("client", client);
        
        return "views/insuranceContract/viewcontracts";
    }

    
    @RequestMapping(value = "/insuranceContract/create/{BSN}", method = RequestMethod.GET)
    public String createContract(@PathVariable int BSN, ModelMap model) {
        InsuranceContract insuranceContract = new InsuranceContract();

        model.addAttribute("BSN", BSN);
        model.addAttribute("insuranceContract", insuranceContract);
        model.addAttribute("insurances", insuranceService.findAllInsurances());
        model.addAttribute("insuranceTypes", insuranceTypeService.findAllInsuranceTypes());
        return "views/insuranceContract/create";
    }
    
    

    @RequestMapping(value="/insuranceContract/create", method = RequestMethod.POST)
    public String validateAndSaveContract(@Valid InsuranceContract insuranceContract, final BindingResult bindingResult, final ModelMap model) {
        if (bindingResult.hasErrors()) {
            return "views/insuranceContract/create/";
        }
        
        InsuranceContract newInsuranceContract = insuranceContract;
        try {
            newInsuranceContract.setRemainingReimbursements(insuranceTypeService.findInsuranceTypeById(insuranceContract.getInsuranceTypeID()).getReimbursementAmount());
        } catch(InsuranceNotFoundException ex){
             //logger.error(ex.getMessage());
        }
        
        try {
            for(InsuranceContract currentInsuranceContract : insuranceContractService.getContractsByBSN(newInsuranceContract.getBSN())) //Check overlapping contracts with the same insurance ID
            {
                if(currentInsuranceContract.getInsuranceID() == newInsuranceContract.getInsuranceID()) //Is it the same insurance?
                {        
                    if ((newInsuranceContract.getStartDate().after(currentInsuranceContract.getStartDate()) && newInsuranceContract.getStartDate().before(currentInsuranceContract.getEndDate())) ||  (newInsuranceContract.getEndDate().after(currentInsuranceContract.getStartDate()) && newInsuranceContract.getEndDate().before(currentInsuranceContract.getEndDate())) || newInsuranceContract.getStartDate().after(newInsuranceContract.getEndDate()))
                    {
                        model.addAttribute("info", "De cliënt beschikt al over een contract voor deze verzekering in de opgegeven periode of de datum is ongeldig.");
                        model.addAttribute("BSN", insuranceContract.getBSN());
                        model.addAttribute("insuranceContract", insuranceContract);
                        model.addAttribute("insurances", insuranceService.findAllInsurances());
                        model.addAttribute("insuranceTypes", insuranceTypeService.findAllInsuranceTypes());
                        return "views/insuranceContract/create"; 
                    }
                }
            }
        } catch(InsuranceContractNotFoundException ex){
             //logger.error(ex.getMessage());
        }
        
        if(insuranceContractService.create(newInsuranceContract) != null) {
            model.addAttribute("info", "Het nieuwe contract is aangemaakt.");
        } else {
            model.addAttribute("BSN", insuranceContract.getBSN());
            model.addAttribute("insuranceContract", insuranceContract);
            model.addAttribute("insurances", insuranceService.findAllInsurances());
            model.addAttribute("insuranceTypes", insuranceTypeService.findAllInsuranceTypes());
            model.addAttribute("info", "Het contract kon niet worden aangemaakt.");
        }
        
        return "redirect:/insuranceContract/viewcontracts/" + newInsuranceContract.getBSN();
    }
    
    
    
    
    
    
    
    @RequestMapping(value = "/insuranceContract/edit/{ID}", method = RequestMethod.GET)
    public String editContract(@PathVariable int ID, ModelMap model) {
        InsuranceContract insuranceContract = null;
        insuranceContract = insuranceContractService.findInsuranceContractByID(ID);
        
        model.addAttribute("BSN", insuranceContract.getBSN());
        model.addAttribute("insuranceContract", insuranceContract);
        model.addAttribute("insurances", insuranceService.findAllInsurances());
        model.addAttribute("insuranceTypes", insuranceTypeService.findAllInsuranceTypes());

        return "views/insuranceContract/edit";
    }
    
    @RequestMapping(value="/insuranceContract/edit", method = RequestMethod.POST)
    public String validateAndEditContract(@Valid InsuranceContract insuranceContract, final BindingResult bindingResult, final ModelMap model) {
        if (bindingResult.hasErrors()) {
            return "views/insuranceContract/edit/";
        }
       
        InsuranceContract newInsuranceContract = insuranceContract;
        try {
            newInsuranceContract.setRemainingReimbursements(insuranceTypeService.findInsuranceTypeById(insuranceContract.getInsuranceTypeID()).getReimbursementAmount());
        } catch(InsuranceNotFoundException ex){
             //logger.error(ex.getMessage());
        }
        
        try {
            for(InsuranceContract currentInsuranceContract : insuranceContractService.getContractsByBSN(newInsuranceContract.getBSN())) //Check overlapping contracts with the same insurance ID
            {
                if(newInsuranceContract.getInsuranceContractID() == currentInsuranceContract.getInsuranceContractID())
                    break;
                
                if((currentInsuranceContract.getInsuranceID() == newInsuranceContract.getInsuranceID())) //Is it the same insurance but not the same contract?
                {        
                    if ((newInsuranceContract.getStartDate().after(currentInsuranceContract.getStartDate()) && newInsuranceContract.getStartDate().before(currentInsuranceContract.getEndDate())) ||  (newInsuranceContract.getEndDate().after(currentInsuranceContract.getStartDate()) && newInsuranceContract.getEndDate().before(currentInsuranceContract.getEndDate())) || newInsuranceContract.getStartDate().after(newInsuranceContract.getEndDate()))
                    {
                        model.addAttribute("info", "De cliënt beschikt al over een contract voor deze verzekering in de opgegeven periode of de datum is ongeldig.");
                        model.addAttribute("BSN", insuranceContract.getBSN());
                        model.addAttribute("insuranceContract", insuranceContract);
                        model.addAttribute("insurances", insuranceService.findAllInsurances());
                        model.addAttribute("insuranceTypes", insuranceTypeService.findAllInsuranceTypes());
                        return "views/insuranceContract/create"; 
                    }
                }
            }
        } catch(InsuranceContractNotFoundException ex){
             //logger.error(ex.getMessage());
            model.addAttribute("info", "Contract kon niet worden gewijzigd");
            
        }

        insuranceContractService.update(newInsuranceContract);
        
        return "redirect:/insuranceContract/viewcontracts/" + insuranceContract.getBSN() + "?success=true";
    }

    
    @RequestMapping(value = "/insuranceContract/delete/{ID}", method = RequestMethod.GET)
    public String deleteContract(@PathVariable int ID, ModelMap model) {
        
        InsuranceContract insuranceContract = insuranceContractService.findInsuranceContractByID(ID);

        try {
            insuranceContractService.delete(ID);
            model.addAttribute("info", "Het contract is verwijderd.");
        } catch(Exception e){
             model.addAttribute("info", "Het contract kon niet worden verwijderd.");
        }
        
        model.addAttribute("clients", clientService.findAllClients());
        return "forward:insuranceContract/viewcontracts/" + insuranceContract.getBSN();
    }
}
