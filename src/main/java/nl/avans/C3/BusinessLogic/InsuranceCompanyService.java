/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.avans.C3.BusinessLogic;

import java.util.List;
import nl.avans.C3.DataStorage.InsuranceCompanyRepositoryIF;
import nl.avans.C3.Domain.InsuranceCompany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Stefan
 */
@Service
public class InsuranceCompanyService {
    private InsuranceCompanyRepositoryIF insuranceCompanyRepositoryIF;
   
    @Autowired
    public void setInsuranceCompanyRepository(InsuranceCompanyRepositoryIF insuranceCompanyRepositoryIF) {
        this.insuranceCompanyRepositoryIF = insuranceCompanyRepositoryIF;
    }
    
    public InsuranceCompany getInsuranceCompany() {
        List<InsuranceCompany> insuranceCompanyList = insuranceCompanyRepositoryIF.getInsuranceCompany();
        
        InsuranceCompany insuranceCompany = insuranceCompanyList.get(0);
        
        return insuranceCompany;
    }
    
    public void editInsuranceCompany(String name, String city, String postalCode, String address, String country, int kVK) {
        insuranceCompanyRepositoryIF.editInsuranceCompany(name, city, postalCode, address, country, kVK);
    }
}
