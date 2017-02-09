/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.avans.C3.DataStorage;

import nl.avans.C3.Domain.Treatment;

/**
 *
 * @author Stefan
 */
public interface TreatmentRepositoryIF {
    public Treatment getTreatment(int behandelCode);
}
