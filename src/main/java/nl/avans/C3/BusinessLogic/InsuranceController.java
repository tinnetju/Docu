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
    
    @RequestMapping(value = "/insurance", method = RequestMethod.GET)
    public String helloInsurance(ModelMap model) {
        try {
            Insurance testInsurance = insuranceService.findInsuranceById(1);
        } catch (InsuranceNotFoundException ex) {
            System.out.println("ERRORRRRRRRRR");
        }

        model.addAttribute("message", "Hello from the controller");
        
        return "insurance";
    }
    
    @RequestMapping(value = "/viewinsurance/{ID}", method = RequestMethod.GET)
    public String getInsuranceByID(@PathVariable int ID, ModelMap model) throws InsuranceNotFoundException {

        Insurance insurance = null;
        try {
            insurance = insuranceService.findInsuranceById(ID);
        } catch(InsuranceNotFoundException ex){
            //logger.error(ex.getMessage());
        }
        
        List<Client> clients = insuranceService.getClientsForInsurances(ID);

        model.addAttribute("client", insurance);
        model.addAttribute("insurances", clients);
        model.addAttribute("message", "Hello from the controller");
        return "views/insurance/viewinsurance";
    }
    
    @RequestMapping(value = "/addinsurance", method = RequestMethod.GET)
    public String addInsurance(ModelMap model) {
        Insurance insurance = new Insurance();
        
        model.addAttribute("client", insurance);
        model.addAttribute("message", new String("New insurance has been added successfully")); //CHECK VOOR MAKEN
        // Open de juiste view template als resultaat.
        return "views/client/addinsurance";
    }
    
    
    public void refreshTable(){
        DefaultTableModel insuranceTableModel = new DefaultTableModel();
        tblClients.setModel(insuranceTableModel);
        insuranceTableModel.addColumn("Ingrediëntnaam"); 
        insuranceTableModel.addColumn("Hoeveelheid");
        
        tblClients.getColumnModel().getColumn(1).setMinWidth(100);
        tblClients.getColumnModel().getColumn(1).setMaxWidth(100);
        
        List<Insurance> insuranceList = insuranceService.findAllInsurances();
        
        if(insuranceList.size() > 0){
            for (Insurance currentInsurance : insuranceList) {
                insuranceTableModel.addRow(new Object[]{"Naam", "Beschrijving", "Prijs", "Eigen risico"});
            }
        }
        else
            insuranceTableModel.addRow(new Object[]{"Geen ingrediënten gevonden"});
    }
}
