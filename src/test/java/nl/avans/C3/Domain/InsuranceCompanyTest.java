/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.avans.C3.Domain;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Stefan
 */
public class InsuranceCompanyTest {
    
    public InsuranceCompanyTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of getName method, of class InsuranceCompany.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        InsuranceCompany instance = new InsuranceCompany("Fysiotherapiepraktijk A", "Rotterdam", "4532PK", "Langeweg 7a", "Nederland", 22123412);
        String expResult = "Fysiotherapiepraktijk A";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getCity method, of class InsuranceCompany.
     */
    @Test
    public void testGetCity() {
        System.out.println("getCity");
        InsuranceCompany instance = new InsuranceCompany("Fysiotherapiepraktijk A", "Rotterdam", "4532PK", "Langeweg 7a", "Nederland", 22123412);
        String expResult = "Rotterdam";
        String result = instance.getCity();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPostalCode method, of class InsuranceCompany.
     */
    @Test
    public void testGetPostalCode() {
        System.out.println("getPostalCode");
        InsuranceCompany instance = new InsuranceCompany("Fysiotherapiepraktijk A", "Rotterdam", "4532PK", "Langeweg 7a", "Nederland", 22123412);
        String expResult = "4532PK";
        String result = instance.getPostalCode();
        assertEquals(expResult, result);
    }

    /**
     * Test of getAddress method, of class InsuranceCompany.
     */
    @Test
    public void testGetAddress() {
        System.out.println("getAddress");
        InsuranceCompany instance = new InsuranceCompany("Fysiotherapiepraktijk A", "Rotterdam", "4532PK", "Langeweg 7a", "Nederland", 22123412);
        String expResult = "Langeweg 7a";
        String result = instance.getAddress();
        assertEquals(expResult, result);
    }

    /**
     * Test of getCountry method, of class InsuranceCompany.
     */
    @Test
    public void testGetCountry() {
        System.out.println("getCountry");
        InsuranceCompany instance = new InsuranceCompany("Fysiotherapiepraktijk A", "Rotterdam", "4532PK", "Langeweg 7a", "Nederland", 22123412);
        String expResult = "Nederland";
        String result = instance.getCountry();
        assertEquals(expResult, result);
    }

    /**
     * Test of getKVK method, of class InsuranceCompany.
     */
    @Test
    public void testGetKVK() {
        System.out.println("getKVK");
        InsuranceCompany instance = new InsuranceCompany("Fysiotherapiepraktijk A", "Rotterdam", "4532PK", "Langeweg 7a", "Nederland", 22123412);
        int expResult = 22123412;
        int result = instance.getKVK();
        assertEquals(expResult, result);
    }

    /**
     * Test of setName method, of class InsuranceCompany.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "Fysiotherapiepraktijk B";
        InsuranceCompany instance = new InsuranceCompany("Fysiotherapiepraktijk A", "Rotterdam", "4532PK", "Langeweg 7a", "Nederland", 22123412);
        instance.setName(name);
        String newName = instance.getName();
        assertEquals(name, newName);
    }

    /**
     * Test of setCity method, of class InsuranceCompany.
     */
    @Test
    public void testSetCity() {
        System.out.println("setCity");
        String city = "Tilburg";
        InsuranceCompany instance = new InsuranceCompany("Fysiotherapiepraktijk A", "Rotterdam", "4532PK", "Langeweg 7a", "Nederland", 22123412);
        instance.setCity(city);
        String newCity = instance.getCity();
        assertEquals(city, newCity);
    }

    /**
     * Test of setPostalCode method, of class InsuranceCompany.
     */
    @Test
    public void testSetPostalCode() {
        System.out.println("setPostalCode");
        String postalCode = "4532QQ";
        InsuranceCompany instance = new InsuranceCompany("Fysiotherapiepraktijk A", "Rotterdam", "4532PK", "Langeweg 7a", "Nederland", 22123412);
        instance.setPostalCode(postalCode);
        String newPostalCode = instance.getPostalCode();
        assertEquals(postalCode, newPostalCode);
    }

    /**
     * Test of setAddress method, of class InsuranceCompany.
     */
    @Test
    public void testSetAddress() {
        System.out.println("setAddress");
        String address = "Langeweg 7b";
        InsuranceCompany instance = new InsuranceCompany("Fysiotherapiepraktijk A", "Rotterdam", "4532PK", "Langeweg 7a", "Nederland", 22123412);
        instance.setAddress(address);
        String newAddress = instance.getAddress();
        assertEquals(address, newAddress);
    }

    /**
     * Test of setCountry method, of class InsuranceCompany.
     */
    @Test
    public void testSetCountry() {
        System.out.println("setCountry");
        String country = "Duitsland";
        InsuranceCompany instance = new InsuranceCompany("Fysiotherapiepraktijk A", "Rotterdam", "4532PK", "Langeweg 7a", "Nederland", 22123412);
        instance.setCountry(country);
        String newCountry = instance.getCountry();
        assertEquals(country, newCountry);
    }

    /**
     * Test of setKVK method, of class InsuranceCompany.
     */
    @Test
    public void testSetKVK() {
        System.out.println("setKVK");
        int kVK = 22123412;
        InsuranceCompany instance = new InsuranceCompany("Fysiotherapiepraktijk A", "Rotterdam", "4532PK", "Langeweg 7a", "Nederland", 22123412);
        instance.setKVK(kVK);
        int newKVK = instance.getKVK();
        assertEquals(kVK, newKVK);
    }
    
}
