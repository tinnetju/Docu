package nl.avans.C3.Presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.swing.table.DefaultTableModel;
import javax.validation.Valid;
import nl.avans.C3.BusinessLogic.ClientService;
import nl.avans.C3.BusinessLogic.InsuranceContractService;
import nl.avans.C3.BusinessLogic.InsuranceService;
import nl.avans.C3.BusinessLogic.InsuranceTypeService;
import nl.avans.C3.Domain.Client;
import nl.avans.C3.Domain.ClientNotFoundException;
import nl.avans.C3.Domain.Insurance;
import nl.avans.C3.Domain.InsuranceContract;
import nl.avans.C3.Domain.InsuranceContractNotFoundException;
import nl.avans.C3.Domain.InsuranceNotFoundException;
import nl.avans.C3.Domain.SearchQuery;
import org.joda.time.DateTime;
import org.joda.time.Interval;
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
        InitializePageData(model);
        return "views/insuranceContract/create";
    }

    private void InitializePageData(ModelMap model)
    {
        model.addAttribute("insurances", insuranceService.findAllInsurances());
        model.addAttribute("insuranceTypes", insuranceTypeService.findAllInsuranceTypes());
    }
    
    @RequestMapping(value="/insuranceContract/create", method = RequestMethod.POST)
    public String validateAndSaveContract(@Valid InsuranceContract insuranceContract, final BindingResult bindingResult, final ModelMap model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("BSN", insuranceContract.getBSN());
            model.addAttribute("insuranceContract", insuranceContract);
            InitializePageData(model);
            return "views/insuranceContract/create";
        }
        
        InsuranceContract newInsuranceContract = insuranceContract;
        try {
            newInsuranceContract.setRemainingReimbursements(insuranceTypeService.findInsuranceTypeById(insuranceContract.getInsuranceTypeID()).getReimbursementAmount());
        } catch(InsuranceNotFoundException ex){
             //logger.error(ex.getMessage());
        }
        
        try {
            if(insuranceContractService.getContractsByBSN(newInsuranceContract.getBSN()).size() > 1)
            {
            for(InsuranceContract currentInsuranceContract : insuranceContractService.getContractsByBSN(newInsuranceContract.getBSN())) //Check overlapping contracts with the same insurance ID
            {
                if((currentInsuranceContract.getInsuranceID() == newInsuranceContract.getInsuranceID() && newInsuranceContract.getInsuranceContractID() != currentInsuranceContract.getInsuranceContractID())) //Is it the same insurance but not the same contract?
                {
                    Interval interval = new Interval(new DateTime(newInsuranceContract.getStartDate()), new DateTime(newInsuranceContract.getEndDate()));
                    Interval interval2 = new Interval(new DateTime(currentInsuranceContract.getStartDate()), new DateTime(currentInsuranceContract.getEndDate()));

                    if (interval.overlaps(interval2))
                    {
                        model.addAttribute("info", "De cliënt beschikt al over een contract voor deze verzekering in de opgegeven periode.");
                        model.addAttribute("BSN", insuranceContract.getBSN());
                        model.addAttribute("insuranceContract", insuranceContract);
                        InitializePageData(model);
                        return "views/insuranceContract/create"; 
                    }
                }
            }
            }
        } catch(Exception ex){
            model.addAttribute("info", "Het contract kon niet worden aangemaakt, mogelijk valt de einddatum voor de begindatum.");
            model.addAttribute("BSN", insuranceContract.getBSN());
            model.addAttribute("insuranceContract", insuranceContract);
            InitializePageData(model);
            return "views/insuranceContract/create"; 
        }
        
        
        if(insuranceContractService.create(newInsuranceContract) != null) {
            model.addAttribute("info", "Het nieuwe contract is aangemaakt.");
        } else {
            model.addAttribute("info", "Het contract kon niet worden aangemaakt.");
            model.addAttribute("BSN", insuranceContract.getBSN());
            model.addAttribute("insuranceContract", insuranceContract);
            InitializePageData(model);
            return "views/insuranceContract/create"; 
        }
        
        return "redirect:/insuranceContract/viewcontracts/" + newInsuranceContract.getBSN();
    }

    @RequestMapping(value = "/insuranceContract/edit/{ID}", method = RequestMethod.GET)
    public String editContract(@PathVariable int ID, ModelMap model) {
        InsuranceContract insuranceContract = insuranceContractService.findInsuranceContractByID(ID);
        
        model.addAttribute("BSN", insuranceContract.getBSN());
        model.addAttribute("insuranceContract", insuranceContract);
        InitializePageData(model);

        return "views/insuranceContract/edit";
    }
    
    @RequestMapping(value="/insuranceContract/edit", method = RequestMethod.POST)
    public String validateAndEditContract(@Valid InsuranceContract insuranceContract, final BindingResult bindingResult, final ModelMap model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("BSN", insuranceContract.getBSN());
            model.addAttribute("insuranceContractID", insuranceContract.getInsuranceContractID());
            InitializePageData(model);
            return "views/insuranceContract/edit"; 
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
                if((currentInsuranceContract.getInsuranceID() == newInsuranceContract.getInsuranceID() && newInsuranceContract.getInsuranceContractID() != currentInsuranceContract.getInsuranceContractID())) //Is it the same insurance but not the same contract?
                {
                    Interval interval = new Interval(new DateTime(newInsuranceContract.getStartDate()), new DateTime(newInsuranceContract.getEndDate()));
                    Interval interval2 = new Interval(new DateTime(currentInsuranceContract.getStartDate()), new DateTime(currentInsuranceContract.getEndDate()));
                    
                    if (interval.overlaps(interval2))
                    {
                        model.addAttribute("info", "De cliënt beschikt al over een contract voor deze verzekering in de opgegeven periode.");
                        model.addAttribute("BSN", insuranceContract.getBSN());
                        model.addAttribute("insuranceContract", insuranceContract);
                        InitializePageData(model);
                        return "views/insuranceContract/edit"; 
                    }
                }
            }
        } catch(Exception ex){
            model.addAttribute("info", "Het contract kon niet worden gewijzigd, mogelijk valt de einddatum voor de begindatum.");
            model.addAttribute("BSN", insuranceContract.getBSN());
            model.addAttribute("insuranceContract", insuranceContract);
            InitializePageData(model);
            return "views/insuranceContract/edit"; 
        }
        
        try {
            insuranceContractService.update(newInsuranceContract);
            model.addAttribute("info", "Het contract is gewijzigd");
        } catch(Exception e){
            model.addAttribute("info", "Het contract kon niet worden gewijzigd");
        }

        return "redirect:/insuranceContract/viewcontracts/" + insuranceContract.getBSN();
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
        
        model.addAttribute("BSN", insuranceContract.getBSN());
        model.addAttribute("insuranceContract", insuranceContract);
        InitializePageData(model);
        
        return "forward:/insuranceContract/viewcontracts/" + insuranceContract.getBSN();
    }
}
