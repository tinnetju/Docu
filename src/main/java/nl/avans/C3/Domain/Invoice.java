/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.avans.C3.Domain;

import java.math.BigDecimal;
import java.util.Date;



public class Invoice {
    private int invoiceNumber;
    private Date date;
    private Date expirationDate;
    private long totalAmount;
    private BigDecimal VAT;
    private Treatment treatment;
    private InsuranceCompany insuranceCompany;
    
    public Invoice(int invoiceNumber, Date date, Date expirationDate, long totalAmount, BigDecimal VAT, Treatment treatment, InsuranceCompany insuranceCompany){
        this.invoiceNumber = invoiceNumber;
        this.date = date;
        this.expirationDate = expirationDate;
        this.totalAmount = totalAmount;
        this.VAT = VAT;
        this.treatment = treatment;
        this.insuranceCompany = insuranceCompany;
    }
    
    public long getTotalAmount(){
        return totalAmount;
    }
}
