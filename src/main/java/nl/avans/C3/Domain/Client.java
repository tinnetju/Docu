/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.avans.C3.Domain;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Min;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author Thom
 */
public class Client {
    @NotNull
    @Min(100000000)
    @Max(999999999)
    private int BSN;
    
    @NotNull
    @Size(min=2, max=50)
    private String lastName;
    
    @NotNull
    @Size(min=2, max=25)
    private String firstName;
    
    @NotNull
    @Past
    private Date dateOfBirth;
    
    @NotNull
    @Size(min=2, max=25)
    private String city;
    
    @NotNull
    @Pattern(regexp = "^[1-9][0-9]{3}\\s?[a-zA-Z]{2}$", message = "De waarde van dit veld moet een geldige postcode zijn")
    private String postalCode;
    
    @NotNull
    @Size(min=2, max=50)
    private String address;
    
    @NotNull
    @Pattern(regexp = "^((NO)[0-9A-Z]{2}[ ][0-9A-Z]{4}[ ][0-9A-Z]{4}[ ][0-9A-Z]{3}|(NO)[0-9A-Z]{13}|(BE)[0-9A-Z]{2}[ ][0-9A-Z]{4}[ ][0-9A-Z]{4}[ ][0-9A-Z]{4}|(BE)[0-9A-Z]{14}|(DK|FO|FI|GL|NL)[0-9A-Z]{2}[ ][0-9A-Z]{4}[ ][0-9A-Z]{4}[ ][0-9A-Z]{4}[ ][0-9A-Z]{2}|(DK|FO|FI|GL|NL)[0-9A-Z]{16}|(MK|SI)[0-9A-Z]{2}[ ][0-9A-Z]{4}[ ][0-9A-Z]{4}[ ][0-9A-Z]{4}[ ][0-9A-Z]{3}|(MK|SI)[0-9A-Z]{17}|(BA|EE|KZ|LT|LU|AT)[0-9A-Z]{2}[ ][0-9A-Z]{4}[ ][0-9A-Z]{4}[ ][0-9A-Z]{4}[ ][0-9A-Z]{4}|(BA|EE|KZ|LT|LU|AT)[0-9A-Z]{18}|(HR|LI|LV|CH)[0-9A-Z]{2}[ ][0-9A-Z]{4}[ ][0-9A-Z]{4}[ ][0-9A-Z]{4}[ ][0-9A-Z]{4}[ ][0-9A-Z]{1}|(HR|LI|LV|CH)[0-9A-Z]{19}|(BG|DE|IE|ME|RS|GB)[0-9A-Z]{2}[ ][0-9A-Z]{4}[ ][0-9A-Z]{4}[ ][0-9A-Z]{4}[ ][0-9A-Z]{4}[ ][0-9A-Z]{2}|(BG|DE|IE|ME|RS|GB)[0-9A-Z]{20}|(GI|IL)[0-9A-Z]{2}[ ][0-9A-Z]{4}[ ][0-9A-Z]{4}[ ][0-9A-Z]{4}[ ][0-9A-Z]{4}[ ][0-9A-Z]{3}|(GI|IL)[0-9A-Z]{21}|(AD|CZ|SA|RO|SK|ES|SE|TN)[0-9A-Z]{2}[ ][0-9A-Z]{4}[ ][0-9A-Z]{4}[ ][0-9A-Z]{4}[ ][0-9A-Z]{4}[ ][0-9A-Z]{4}|(AD|CZ|SA|RO|SK|ES|SE|TN)[0-9A-Z]{22}|(PT)[0-9A-Z]{2}[ ][0-9A-Z]{4}[ ][0-9A-Z]{4}[ ][0-9A-Z]{4}[ ][0-9A-Z]{4}[ ][0-9A-Z]{4}[ ][0-9A-Z]{1}|(PT)[0-9A-Z]{23}|(IS|TR)[0-9A-Z]{2}[ ][0-9A-Z]{4}[ ][0-9A-Z]{4}[ ][0-9A-Z]{4}[ ][0-9A-Z]{4}[ ][0-9A-Z]{4}[ ][0-9A-Z]{2}|(IS|TR)[0-9A-Z]{24}|(FR|GR|IT|MC|SM)[0-9A-Z]{2}[ ][0-9A-Z]{4}[ ][0-9A-Z]{4}[ ][0-9A-Z]{4}[ ][0-9A-Z]{4}[ ][0-9A-Z]{4}[ ][0-9A-Z]{3}|(FR|GR|IT|MC|SM)[0-9A-Z]{25}|(AL|CY|HU|LB|PL)[0-9A-Z]{2}[ ][0-9A-Z]{4}[ ][0-9A-Z]{4}[ ][0-9A-Z]{4}[ ][0-9A-Z]{4}[ ][0-9A-Z]{4}[ ][0-9A-Z]{4}|(AL|CY|HU|LB|PL)[0-9A-Z]{26}|(MU)[0-9A-Z]{2}[ ][0-9A-Z]{4}[ ][0-9A-Z]{4}[ ][0-9A-Z]{4}[ ][0-9A-Z]{4}[ ][0-9A-Z]{4}[ ][0-9A-Z]{4}[ ][0-9A-Z]{2}|(MU)[0-9A-Z]{28}|(MT)[0-9A-Z]{2}[ ][0-9A-Z]{4}[ ][0-9A-Z]{4}[ ][0-9A-Z]{4}[ ][0-9A-Z]{4}[ ][0-9A-Z]{4}[ ][0-9A-Z]{4}[ ][0-9A-Z]{3}|(MT)[0-9A-Z]{29})$", message = "De waarde van dit veld moet een geldige IBAN zijn")
    private String IBAN;

    private boolean incasso;
    
    @NotNull
    @Pattern(regexp = "^([0-9a-zA-Z]([-.\\w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-\\w]*[0-9a-zA-Z]\\.)+[a-zA-Z]{2,9})$", message = "De waarde van dit veld moet een geldig e-mailadres zijn")
    private String emailAddress;
    
    @NotNull
    @Pattern(regexp = "^(?=^.{10,11}$)0\\d*-?\\d*$", message = "De waarde van dit veld moet een geldig (mobiel) telefoonnummer zijn")
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
