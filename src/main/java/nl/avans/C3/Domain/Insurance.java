/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.avans.C3.Domain;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 *
 * @author Thom
 */
public class Insurance {
    private int ID;
    private String name;
    private BigDecimal price;
    private int sessionsReimbursed;
    private InsuranceCompany insuranceCompany;
    
    public Insurance(){
        
    }
    
    public Insurance(int ID, String name, BigDecimal price, int sessionsReimbursed){
        this.ID = ID;
        this.name = name;
        this.price = price;
        this.sessionsReimbursed = sessionsReimbursed;
    }

    /**
     * @return the ID
     */
    public int getID() {
        return ID;
    }

    /**
     * @param ID the ID to set
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return the sessionsReimbursed
     */
    public int getSessionsReimbursed() {
        return sessionsReimbursed;
    }

    /**
     * @param sessionsReimbursed the sessionsReimbursed to set
     */
    public void setSessionsReimbursed(int sessionsReimbursed) {
        this.sessionsReimbursed = sessionsReimbursed;
    }

    /**
     * @return the insuranceCompany
     */
    public InsuranceCompany getInsuranceCompany() {
        return insuranceCompany;
    }

    /**
     * @param insuranceCompany the insuranceCompany to set
     */
    public void setInsuranceCompany(InsuranceCompany insuranceCompany) {
        this.insuranceCompany = insuranceCompany;
    }
}
