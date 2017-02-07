/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.avans.C3.Domain;

import java.math.BigDecimal;

/**
 *
 * @author Thom
 */
public class Treatment {
    private String name;
    private String code;
    private BigDecimal priceSession;
    private int amountSessions;
    
    public Treatment(String name, String code, BigDecimal priceSession, int amountSessions){
        this.name = name;
        this.code = code;
        this.priceSession = priceSession;
        this.amountSessions = amountSessions;
    }
    public BigDecimal getPrice(){
        return priceSession;
    }
}
