/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.avans.C3.DataStorage;

import java.util.List;
import nl.avans.C3.Domain.Company;

/**
 *
 * @author Stefan
 */
public interface CompanyRepositoryIF {
    public List<Company> getCompany();
            
    public void editCompany(String name, String city, String postalCode, String address, String country, int kVK);
}
