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
public class InsuranceTypeController {
    private javax.swing.JTable tblClients;
    private InsuranceTypeService insuranceTypeService;
   
   
    @Autowired
    public InsuranceTypeController(InsuranceTypeService insuranceTypeSService) {
        this.insuranceTypeService = insuranceTypeSService;
    }

    @RequestMapping(value = "/addinsurancetype", method = RequestMethod.GET)
    public String createInsuranceType(ModelMap model){
        InsuranceType insuranceType = new InsuranceType();
        model.addAttribute("insuranceType",insuranceType);
        return "views/insurance/addinsurancetype";
    }
    
    @RequestMapping(value = "/addinsurancetype", method = RequestMethod.POST)
    public String addInsuranceType(InsuranceType insuranceType, ModelMap model) {
        InsuranceType result = insuranceTypeService.create(insuranceType);
        model.addAttribute("message", new String("New insurancetype has been added successfully")); //CHECK VOOR MAKEN
        // Open de juiste view template als resultaat.
        return "views/insurance/addinsurancetype";
    } 
    
    @RequestMapping(value = "/viewinsurancetypes", method = RequestMethod.GET)
    public String getinsurancetypes(ModelMap model) {

        ArrayList<InsuranceType> insurancetypes = new ArrayList<>();
        
        for (InsuranceType insurancetype:insuranceTypeService.findAllInsuranceTypes()){
            insurancetypes.add(insurancetype);
        } 
        
        model.addAttribute("insurancetypes", insurancetypes);
        return "views/insurance/viewinsurancetypes";
    }
    
    @RequestMapping(value = "/editinsurancetype/{ID}", method = RequestMethod.GET)
    public String getInsuranceTypeByID(ModelMap model,@PathVariable int ID) throws InsuranceNotFoundException {

        InsuranceType insuranceType = null;
        try {
            insuranceType = insuranceTypeService.findInsuranceTypeById(ID);
        } catch(InsuranceNotFoundException ex){
            //logger.error(ex.getMessage());
        }

        model.addAttribute("insuranceType", insuranceType);
        return "views/insurance/editinsurancetype";
    }
    @RequestMapping(value = "/editinsurancetype/{ID}", method = RequestMethod.POST)
    public String editInsuranceType(InsuranceType insuranceType, ModelMap model,@PathVariable int ID) {
        insuranceTypeService.edit(insuranceType,ID);
        model.addAttribute("message", new String("Insurancetype has been changed successfully")); 
        // Open de juiste view template als resultaat.
        return "views/insurance/viewinsurancetypes";
    }    
    @RequestMapping(value = "/deleteinsurancetype/{ID}", method = RequestMethod.GET)
    public String deleteInsurance(@PathVariable int ID,Insurance insurance,ModelMap model) {
        insuranceTypeService.delete(ID);
        model.addAttribute("message", new String("Insurancetype deleted successfully"));     
        return "views/insurance/viewinsurancetypes";
    }       
    
}
