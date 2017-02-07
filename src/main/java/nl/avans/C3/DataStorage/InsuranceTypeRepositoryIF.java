/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.avans.C3.DataStorage;

import java.util.List;
import nl.avans.C3.Domain.InsuranceType;

/**
 *
 * @author Thom
 */
public interface InsuranceTypeRepositoryIF {
    public List<InsuranceType> findAll();

    public InsuranceType findInsuranceTypeById(int ID);

    public InsuranceType create(final InsuranceType insuranceType);
    
    public void edit(final InsuranceType insuranceType, int ID);
    
    public void deleteInsuranceTypeById(int ID);
}
