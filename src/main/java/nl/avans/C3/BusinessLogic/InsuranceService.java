package nl.avans.C3.BusinessLogic;

/**
 *
 * @author Thom
 */

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import nl.avans.C3.DataStorage.InsuranceRepositoryIF;
import nl.avans.C3.Domain.Client;
import nl.avans.C3.Domain.ClientNotFoundException;
import nl.avans.C3.Domain.Insurance;
import nl.avans.C3.Domain.InsuranceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;


@Service
public class InsuranceService {
    private InsuranceRepositoryIF insuranceRepository;
    //private InsuranceService insuranceService;
    
    /*@Autowired
    public void setInsuranceService(InsuranceService insuranceService) {
        this.insuranceService = insuranceService;
    }*/
   
    @Autowired
    public void setInsuranceRepository(InsuranceRepositoryIF insuranceRepositoryIF) {
        this.insuranceRepository = insuranceRepositoryIF;
    }
    
    public List<Insurance> findAllInsurances() {
        return insuranceRepository.findAll();
    }
    
    public Insurance findInsuranceById(int ID) throws InsuranceNotFoundException {
        Insurance insurance = null;

        insurance = insuranceRepository.findInsuranceById(ID);
        if(insurance.equals(null)) {
            throw new InsuranceNotFoundException("Exception!");
        } else {
            return insurance;
        }
    }
    
    public List<Client> getClientsForInsurances(int ID) throws InsuranceNotFoundException
    {
        Insurance insurance = null;
        List<Client> clients = null; //set in else 
        
        
        insurance = insuranceRepository.findInsuranceById(ID);
        if(insurance.equals(null)) {
            throw new InsuranceNotFoundException("Exception!");
        } else {
            return clients;
        }
    }
}
