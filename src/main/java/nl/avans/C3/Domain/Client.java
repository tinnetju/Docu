/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.avans.C3.Domain;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Thom
 */
public class Client {
    @NotNull
    private int BSN;
    
    @NotNull
    private String lastName;
    
    @NotNull
    private String firstName;
    
    @NotNull
    private Date dateOfBirth;
    
    @NotNull
    private String city;
    
    @NotNull
    private String postalCode;
    
    @NotNull
    private String address;
    
    @NotNull
    private String IBAN;
    
    @NotNull
    private boolean incasso;
    
    @NotNull
    private String emailAddress;
    
    @NotNull
    private String telephoneNumber;
    
    private List<InsuranceContract> contracts;
    private List<Invoice> invoices;
 
    public Client()
    {
        this.contracts = new ArrayList<>();
        this.invoices = new ArrayList<>();
    }
    
    public Client(int BSN, String lastName, String firstName, Date dateOfBirth, String city, String postalcode, String address, String IBAN, boolean incasso, String emailaddress, String telephonenumber, InsuranceContract contract, ArrayList<Invoice> invoices){
        this.BSN = BSN;
        this.lastName = lastName;
        this.firstName = firstName;
        this.dateOfBirth = dateOfBirth;
        this.city = city;
        this.postalCode = postalcode;
        this.address = address;
        this.IBAN = IBAN;
        this.incasso = incasso;
        this.emailAddress = emailaddress;
        this.telephoneNumber = telephonenumber;
        this.contracts = new ArrayList<>();

        if(contract != null)
            contracts.add(contract);            
        
        if(invoices != null)
            this.invoices = invoices;
        else
            this.invoices = new ArrayList<>();
    }
 
    public int getBSN() {
        return BSN;
    }

    public void setBSN(int BSN) {
        this.BSN = BSN;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalcode) {
        this.postalCode = postalcode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public boolean isIncasso() {
        return incasso;
    }

    public void setIncasso(boolean incasso) {
        this.incasso = incasso;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailaddress) {
        this.emailAddress = emailaddress;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephonenumber) {
        this.telephoneNumber = telephonenumber;
    }

    public List<InsuranceContract> getContracts() {
        return contracts;
    }

    public void setContracts(ArrayList<InsuranceContract> contracts) {
        this.contracts = contracts;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(ArrayList<Invoice> invoices) {
        this.invoices = invoices;
    }
 
 public void addInvoice(Invoice invoice){
        invoices.add(invoice);
    }
}
