package nl.avans.C3.BusinessLogic;

/**
 *
 * @author Thom
 */

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import nl.avans.C3.DataStorage.InsuranceCompanyRepositoryIF;
import nl.avans.C3.DataStorage.InsuranceRepositoryIF;
import nl.avans.C3.Domain.Client;
import nl.avans.C3.Domain.ClientNotFoundException;
import nl.avans.C3.Domain.Insurance;
import nl.avans.C3.Domain.InsuranceCompany;
import nl.avans.C3.Domain.InsuranceCompanyNotFoundException;
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
public class InsuranceCompanyService {
    private InsuranceCompanyRepositoryIF insuranceCompanyRepository;

    @Autowired
    public void setInsuranceCompanyRepository(InsuranceCompanyRepositoryIF insuranceCompanyRepositoryIF) {
        this.insuranceCompanyRepository = insuranceCompanyRepositoryIF;
    }
    
    public List<InsuranceCompany> findAllCompanies() {
        return insuranceCompanyRepository.findAll();
    }
    
    public InsuranceCompany findInsuranceCompanyByKvk(int KVK) throws InsuranceNotFoundException {
        InsuranceCompany insuranceCompany = null;

        insuranceCompany = insuranceCompanyRepository.findInsuranceCompanyByKvk(KVK);
        if(insuranceCompany.equals(null)) {
            throw new InsuranceNotFoundException("Exception!");
        } else {
            return insuranceCompany;
        }
    }
    public InsuranceCompany create(final InsuranceCompany insuranceCompany)  {
        return insuranceCompanyRepository.create(insuranceCompany);
    }
    public void edit(final InsuranceCompany insuranceCompany, int KVK)  {
            insuranceCompanyRepository.edit(insuranceCompany, KVK);
    }
    public List<Insurance> getInsurancesForCompany(int KVK) throws InsuranceCompanyNotFoundException
    {
        InsuranceCompany insuranceCompany = null;
        List<Insurance> insurances = null; //set in else 
        
        
        insuranceCompany = insuranceCompanyRepository.findInsuranceCompanyByKvk(KVK);
        if(insuranceCompany.equals(null)) {
            throw new InsuranceCompanyNotFoundException("Exception!");
        } else {
            return insurances;
        }
    }
}
