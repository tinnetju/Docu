/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.avans.C3.DataStorage;

import java.util.List;
import nl.avans.C3.Domain.Insurance;

/**
 *
 * @author Thom
 */
public interface InsuranceRepositoryIF {
    public List<Insurance> findAll();

    public Insurance findInsuranceById(int ID);

    public Insurance create(final Insurance insurance);
    
    public void deleteInsuranceById(int ID);
}
