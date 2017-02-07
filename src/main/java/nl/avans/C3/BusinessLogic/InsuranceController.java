package nl.avans.C3.BusinessLogic;

/**
 *
 * @author Thom
 */

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.swing.table.DefaultTableModel;
import nl.avans.C3.Domain.Client;
import nl.avans.C3.Domain.ClientNotFoundException;
import nl.avans.C3.Domain.Insurance;
import nl.avans.C3.Domain.InsuranceNotFoundException;
import nl.avans.C3.Domain.InsuranceType;
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
public class InsuranceController {
    private javax.swing.JTable tblClients;
    private InsuranceService insuranceService;
   
   
    @Autowired
    public InsuranceController(InsuranceService insuranceService) {
        this.insuranceService = insuranceService;
    }
    
    @RequestMapping(value = "/editinsurance/{ID}", method = RequestMethod.GET)
    public String getInsuranceByID(ModelMap model,@PathVariable int ID) throws InsuranceNotFoundException {

        Insurance insurance = null;
        try {
            insurance = insuranceService.findInsuranceById(ID);
        } catch(InsuranceNotFoundException ex){
            //logger.error(ex.getMessage());
        }

        model.addAttribute("insurance", insurance);
        return "views/insurance/editinsurance";
    }
    @RequestMapping(value = "/editinsurance/{ID}", method = RequestMethod.POST)
    public String editInsurance(Insurance insurance, ModelMap model,@PathVariable int ID) {
        insuranceService.edit(insurance,ID);
        model.addAttribute("message", new String("Insurance has been changed successfully")); 
        // Open de juiste view template als resultaat.
        return "views/insurance/viewinsurances";
    }
    
    @RequestMapping(value = "/deleteinsurance/{ID}", method = RequestMethod.GET)
    public String deleteInsurance(@PathVariable int ID,Insurance insurance,ModelMap model) {
        insuranceService.delete(ID);
        model.addAttribute("message", new String("Insurance deleted successfully"));     
        return "views/insurance/viewinsurances";
    }    
        @RequestMapping(value = "/viewinsurances", method = RequestMethod.GET)
    public String getinsurances(ModelMap model) {

        ArrayList<Insurance> insurances = new ArrayList<>();
        
        for (Insurance insurance:insuranceService.findAllInsurances()){
            insurances.add(insurance);
        } 
        
        model.addAttribute("insurances", insurances);
        return "views/insurance/viewinsurances";
    }
    
    @RequestMapping(value = "/addinsurance", method = RequestMethod.GET)
    public String createInsurance(ModelMap model){
        Insurance insurance = new Insurance();
        model.addAttribute("insurance",insurance);
        return "views/insurance/addinsurance";
    }
    
    @RequestMapping(value = "/addinsurance", method = RequestMethod.POST)
    public String addInsurance(Insurance insurance, ModelMap model) {
        Insurance result = insuranceService.create(insurance);
        model.addAttribute("message", new String("New insurance has been added successfully")); //CHECK VOOR MAKEN
        // Open de juiste view template als resultaat.
        return "views/insurance/addinsurance";
    }
    
    
}
