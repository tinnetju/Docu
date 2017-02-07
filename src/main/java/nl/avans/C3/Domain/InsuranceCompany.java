/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.avans.C3.Domain;

import java.util.ArrayList;

/**
 *
 * @author Stefan
 */
public class InsuranceCompany {
    private String name;
    private String city;
    private String postalCode;
    private String address;
    private String country;
    private int kVK;
    private ArrayList<Insurance> insurances;
    
    public InsuranceCompany(){
        
    }
    
    public InsuranceCompany(String name, String city, String postalCode, String address, String country, int kVK){
        this.name = name;
        this.city = city;
        this.postalCode = postalCode;
        this.address = address;
        this.country = country;
        this.kVK = kVK;
    }

    //getters
    public String getName() {
        return name;
    }
    
    public String getCity() {
        return city;
    }
    
    public String getPostalCode() {
        return postalCode;
    }
    
    public String getAddress() {
        return address;
    }
    
    public String getCountry() {
        return country;
    }
    
    public int getKVK() {
        return kVK;
    }

    //setters
    public void setName(String name) {
        this.name = name;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    public void setKVK(int kVK) {
        this.kVK = kVK;
    }
}
