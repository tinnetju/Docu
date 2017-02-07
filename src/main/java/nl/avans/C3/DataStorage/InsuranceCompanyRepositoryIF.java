/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.avans.C3.DataStorage;

import java.util.List;
import nl.avans.C3.Domain.Insurance;
import nl.avans.C3.Domain.InsuranceCompany;

/**
 *
 * @author Thom
 */
public interface InsuranceCompanyRepositoryIF {
    public List<InsuranceCompany> findAll();

    public InsuranceCompany findInsuranceCompanyByKvk(int KVK);

    public InsuranceCompany create(final InsuranceCompany insuranceCompany);
    
    public void edit(final InsuranceCompany insuranceCompany, int ID);
    
    public void deleteInsuranceCompanyByKvk(int KVK);
}
