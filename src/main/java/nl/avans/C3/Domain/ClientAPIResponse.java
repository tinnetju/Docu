package nl.avans.C3.Domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.sql.Date;

/**
 *
 * @author Tinne
 */

@JacksonXmlRootElement(localName = "client")
public class ClientAPIResponse {

    public ClientAPIResponse(Client client) {
        this.clientBSN = client.getBSN();
        this.clientFirstName = client.getFirstName();
        this.clientLastName = client.getLastName();
        this.clientAddress = client.getAddress();
        this.clientPostalCode = client.getPostalCode();
        this.clientCity = client.getCity();
        this.clientDateOfBirth = client.getDateOfBirth();
        this.clientTelephoneNumber = client.getTelephoneNumber();
        this.emailAddress = client.getEmailAddress();
    }
    
    @JacksonXmlProperty(localName = "BSN")
    int clientBSN;
    @JacksonXmlProperty(localName = "firstName")
    String clientFirstName;
    @JacksonXmlProperty(localName = "lastName")
    String clientLastName;
    @JacksonXmlProperty(localName = "address")
    String clientAddress;
    @JacksonXmlProperty(localName = "postalCode")
    String clientPostalCode;
    @JacksonXmlProperty(localName = "city")
    String clientCity;
    @JacksonXmlProperty(localName = "dateOfBirth")
    Date clientDateOfBirth;
    @JacksonXmlProperty(localName = "telephoneNumber")
    String clientTelephoneNumber;
    @JacksonXmlProperty(localName = "emailAddress")
    String emailAddress;
    
    public int getClientBSN() {
        return clientBSN;
    }

    public void setClientBSN(int clientBSN) {
        this.clientBSN = clientBSN;
    }

    public String getClientFirstName() {
        return clientFirstName;
    }

    public void setClientFirstName(String clientFirstName) {
        this.clientFirstName = clientFirstName;
    }

    public String getClientLastName() {
        return clientLastName;
    }

    public void setClientLastName(String clientLastName) {
        this.clientLastName = clientLastName;
    }
    
    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public String getClientPostalCode() {
        return clientPostalCode;
    }

    public void setClientPostalCode(String clientPostalCode) {
        this.clientPostalCode = clientPostalCode;
    }

    public String getClientCity() {
        return clientCity;
    }

    public void setClientCity(String clientCity) {
        this.clientCity = clientCity;
    }

    public Date getClientDateOfBirth() {
        return clientDateOfBirth;
    }

    public void setClientDateOfBirth(Date clientDateOfBirth) {
        this.clientDateOfBirth = clientDateOfBirth;
    }

    public String getClientTelephoneNumber() {
        return clientTelephoneNumber;
    }

    public void setClientTelephoneNumber(String clientTelephoneNumber) {
        this.clientTelephoneNumber = clientTelephoneNumber;
    }

    public String getClientEmail() {
        return emailAddress;
    }

    public void setClientEmail(String clientEmail) {
        this.emailAddress = clientEmail;
    }
}
