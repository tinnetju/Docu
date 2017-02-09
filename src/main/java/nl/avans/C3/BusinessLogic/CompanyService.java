/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.avans.C3.BusinessLogic;

import java.util.List;
import nl.avans.C3.DataStorage.CompanyRepositoryIF;
import nl.avans.C3.Domain.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Stefan
 */
@Service
public class CompanyService {
    private CompanyRepositoryIF companyRepositoryIF;
   
    @Autowired
    public void setCompanyRepository(CompanyRepositoryIF companyRepositoryIF) {
        this.companyRepositoryIF = companyRepositoryIF;
    }
    
    public Company getCompany() {
        List<Company> insuranceCompanyList = companyRepositoryIF.getCompany();
        
        Company insuranceCompany = insuranceCompanyList.get(0);
        
        return insuranceCompany;
    }
    
    public void editCompany(String name, String city, String postalCode, String address, String country, int kVK) {
        companyRepositoryIF.editCompany(name, city, postalCode, address, country, kVK);
    }
}