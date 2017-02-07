package nl.avans.C3.BusinessLogic;

import nl.avans.C3.DataStorage.ClientRepositoryIF;
import nl.avans.C3.DataStorage.InsuranceRepositoryIF;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import nl.avans.C3.DataStorage.ClientRepository;
import nl.avans.C3.DataStorage.ClientRepositoryIF;
import nl.avans.C3.DataStorage.InsuranceContractRepositoryIF;
import nl.avans.C3.Domain.Client;
import nl.avans.C3.Domain.ClientNotFoundException;
import nl.avans.C3.Domain.Insurance;
import nl.avans.C3.Domain.InsuranceContract;
import nl.avans.C3.Domain.InsuranceContractNotFoundException;
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

/**
 *
 * @author Tinne
 */
@Service
public class InsuranceContractService {
    private InsuranceContractRepositoryIF insuranceContractRepository;
    
    @Autowired
    public void setInsuranceRepository(InsuranceContractRepositoryIF insuranceContractRepositoryIF) {
        this.insuranceContractRepository = insuranceContractRepositoryIF;
    }
    
    public List<InsuranceContract> getContractsByBSN(int BSN) throws InsuranceContractNotFoundException
    {
        List<InsuranceContract> insuranceContracts = null;

        insuranceContracts = insuranceContractRepository.findInsuranceContractsByBSN(BSN);
        if(insuranceContracts.equals(null) || insuranceContracts.size() < 1) {
            throw new InsuranceContractNotFoundException("Exception!");
        } else {
            return insuranceContracts;
        }
    }
    
    public InsuranceContract create(final InsuranceContract insuranceContract)  {
        return insuranceContractRepository.create(insuranceContract);
    }
    
    public void update(final InsuranceContract insuranceContract)  {
        insuranceContractRepository.update(insuranceContract);
    }
    
    public void delete(final int ID)  {
        insuranceContractRepository.deleteInsuranceContractByID(ID);
    }
}
