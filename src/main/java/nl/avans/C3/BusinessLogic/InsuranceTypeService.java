package nl.avans.C3.BusinessLogic;

/**
 *
 * @author Thom
 */

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import nl.avans.C3.DataStorage.InsuranceRepositoryIF;
import nl.avans.C3.DataStorage.InsuranceTypeRepositoryIF;
import nl.avans.C3.Domain.Client;
import nl.avans.C3.Domain.ClientNotFoundException;
import nl.avans.C3.Domain.Insurance;
import nl.avans.C3.Domain.InsuranceNotFoundException;
import nl.avans.C3.Domain.InsuranceType;
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
public class InsuranceTypeService {
    private InsuranceTypeRepositoryIF insuranceTypeRepository;
    //private InsuranceService insuranceService;
    
    /*@Autowired
    public void setInsuranceService(InsuranceService insuranceService) {
        this.insuranceService = insuranceService;
    }*/
   
    @Autowired
    public void setInsuranceTypeRepository(InsuranceTypeRepositoryIF insuranceTypeRepositoryIF) {
        this.insuranceTypeRepository = insuranceTypeRepositoryIF;
    }
    
    public List<InsuranceType> findAllInsuranceTypes() {
        return insuranceTypeRepository.findAll();
    }
    
    public InsuranceType findInsuranceTypeById(int ID) throws InsuranceNotFoundException {
        InsuranceType insuranceType = null;

        insuranceType = insuranceTypeRepository.findInsuranceTypeById(ID);
        if(insuranceType.equals(null)) {
            throw new InsuranceNotFoundException("Exception!");
        } else {
            return insuranceType;
        }
    }
    public InsuranceType create(final InsuranceType insuranceType)  {
        return insuranceTypeRepository.create(insuranceType);
    }
    public void edit(final InsuranceType insuranceType, int ID)  {
        insuranceTypeRepository.edit(insuranceType, ID);
    }
    public void delete (int ID)  {
        insuranceTypeRepository.deleteInsuranceTypeById(ID);
    }    

}
