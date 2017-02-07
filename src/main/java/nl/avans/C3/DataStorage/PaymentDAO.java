/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.avans.C3.DataStorage;

import nl.avans.C3.Domain.Payment;

/**
 *
 * @author Stefan
 */
public class PaymentDAO {
    private String paymentId = "0001";
    private String totalAmount = "100.00";
    private String clientBIC = "RABONL2U";
    private String clientName = "Peter de Wit";
    private String clientIBAN = "NL44RABO0123456789";
    
    private Payment payment = new Payment(paymentId, totalAmount, clientBIC, clientName, clientIBAN);
    
    public Payment getPaymentInformation(){
        return payment;
    }
}
