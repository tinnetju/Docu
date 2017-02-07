package nl.avans.C3.BusinessLogic;

/**
 *
 * @author Thom
 */

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.swing.table.DefaultTableModel;
import nl.avans.C3.Domain.Client;
import nl.avans.C3.Domain.ClientNotFoundException;
import nl.avans.C3.Domain.Insurance;
import nl.avans.C3.Domain.InsuranceCompany;
import nl.avans.C3.Domain.InsuranceCompanyNotFoundException;
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

@Controller
public class InsuranceCompanyController {
    private javax.swing.JTable tblClients;
    private InsuranceCompanyService insuranceCompanyService;
   
   
    @Autowired
    public InsuranceCompanyController(InsuranceCompanyService insuranceCompanyService) {
        this.insuranceCompanyService = insuranceCompanyService;
    }
    
    @RequestMapping(value = "/viewinsuranceCompanies", method = RequestMethod.GET)
    public String getinsurancecompanies(ModelMap model) throws InsuranceCompanyNotFoundException {

        ArrayList<InsuranceCompany> insuranceCompany = new ArrayList<>();
        
        for (InsuranceCompany company:insuranceCompanyService.findAllCompanies()){
            insuranceCompany.add(company);
        } 
        
        model.addAttribute("insuranceCompanies", insuranceCompany);
        return "views/insuranceCompany/viewinsuranceCompanies";
    }
    
    
    @RequestMapping(value = "/addinsuranceCompany", method = RequestMethod.GET)
    public String createInsuranceCompany(ModelMap model){
        InsuranceCompany insuranceCompany = new InsuranceCompany();
        model.addAttribute("insuranceCompany",insuranceCompany);
        return "views/insuranceCompany/addinsuranceCompany";
    }
    
    @RequestMapping(value = "/addinsuranceCompany", method = RequestMethod.POST)
    public String addInsuranceCompany(InsuranceCompany insuranceCompany, ModelMap model) {
        InsuranceCompany result = insuranceCompanyService.create(insuranceCompany);
        model.addAttribute("message", new String("New insurance company has been added successfully")); //CHECK VOOR MAKEN
        // Open de juiste view template als resultaat.
        return "views/insuranceCompany/addinsuranceCompany";
    }
      
    @RequestMapping(value = "/editinsuranceCompany/{KVK}", method = RequestMethod.POST)
    public String editInsuranceCompany(InsuranceCompany insuranceCompany, ModelMap model,@PathVariable int KVK) {
        insuranceCompanyService.edit(insuranceCompany,KVK);
        model.addAttribute("message", new String("Insurancecompany has been changed successfully")); 
        return "views/insuranceCompany/editinsuranceCompany";
    }
    
    @RequestMapping(value = "/editinsuranceCompany/{KVK}", method = RequestMethod.GET)
    public String getInsuranceCompanyByKvk(ModelMap model,@PathVariable int KVK) throws InsuranceCompanyNotFoundException {
        InsuranceCompany insuranceCompany = null;      
        try {
            insuranceCompany = insuranceCompanyService.findInsuranceCompanyByKvk(KVK);
        } catch(InsuranceNotFoundException ex){
            //logger.error(ex.getMessage());
        }
        
        model.addAttribute("insuranceCompany", insuranceCompany);
        return "views/insuranceCompany/editinsuranceCompany";
    }
}
