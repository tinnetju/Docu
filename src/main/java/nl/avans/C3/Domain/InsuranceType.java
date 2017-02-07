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
public class InsuranceType {
    private int ID;
    private String name;
    private String description;
    private String reimbursementDescription;
    private int reimbursementAmount;
    
    public InsuranceType(){
        
    }
    
    public InsuranceType(int ID, String name, String description, String reimbursementDescription,int reimbursementAmount){
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.reimbursementDescription = reimbursementDescription;
        this.reimbursementAmount = reimbursementAmount;        
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
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the reimbursementDescription
     */
    public String getReimbursementDescription() {
        return reimbursementDescription;
    }

    /**
     * @param reimbursementDescription the reimbursementDescription to set
     */
    public void setReimbursementDescription(String reimbursementDescription) {
        this.reimbursementDescription = reimbursementDescription;
    }

    /**
     * @return the reimbursementAmount
     */
    public int getReimbursementAmount() {
        return reimbursementAmount;
    }

    /**
     * @param reimbursementAmount the reimbursementAmount to set
     */
    public void setReimbursementAmount(int reimbursementAmount) {
        this.reimbursementAmount = reimbursementAmount;
    }


}
