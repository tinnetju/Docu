/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.avans.C3.Domain;

/**
 *
 * @author Stefan
 */
public class Payment {
    private String paymentId;
    private String totalAmount;
    private String clientBIC;
    private String clientName;
    private String clientIBAN;
    
    public Payment(String paymentId, String totalAmount, String clientBIC, String clientName, String clientIBAN){
        this.paymentId = paymentId;
        this.totalAmount = totalAmount;
        this.clientBIC = clientBIC;
        this.clientName = clientName;
        this.clientIBAN = clientIBAN;
    }

    //getters
    public String getPaymentId() {
        return paymentId;
    }
    
    public String getTotalAmount() {
        return totalAmount;
    }

    public String getClientBIC() {
        return clientBIC;
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientIBAN() {
        return clientIBAN;
    }
    
    //setters
    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setClientBIC(String clientBIC) {
        this.clientBIC = clientBIC;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setClientIBAN(String clientIBAN) {
        this.clientIBAN = clientIBAN;
    }
}
