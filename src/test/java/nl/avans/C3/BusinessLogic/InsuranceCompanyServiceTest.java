/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.avans.C3.BusinessLogic;

import java.util.ArrayList;
import java.util.List;
import nl.avans.C3.DataStorage.InsuranceCompanyRepository;
import nl.avans.C3.Domain.InsuranceCompany;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
/**
 *
 * @author Stefan
 */
public class InsuranceCompanyServiceTest {
    @Mock
    private InsuranceCompanyRepository insuranceCompanyRepositoryMock;
    @Mock
    private InsuranceCompany insuranceCompanyMock;
    
    private List<InsuranceCompany> insuranceCompanyArrayList;
    private int kVK = 12341234;
    
    @Before
    public void setupMock() {
        insuranceCompanyMock = mock(InsuranceCompany.class);
        when(insuranceCompanyMock.getKVK()).thenReturn(12341234);

        insuranceCompanyArrayList = new ArrayList<>();
        insuranceCompanyArrayList.add(insuranceCompanyMock);

        insuranceCompanyRepositoryMock = mock(InsuranceCompanyRepository.class);
        
        when(insuranceCompanyRepositoryMock.getInsuranceCompany()).thenReturn(insuranceCompanyArrayList);
    }
    
    @After
    public void tearDown(){
    }
    
    /**
     * Test of getInsuranceCompany method, of class InsuranceCompanyService.
     */
    @Test
    public void testGetInsuranceCompany(){
        System.out.println("testGetInsuranceCompany");

        InsuranceCompanyService insuranceCompanyServiceToTest = new InsuranceCompanyService();
        insuranceCompanyServiceToTest.setInsuranceCompanyRepository(insuranceCompanyRepositoryMock);

        InsuranceCompany insuranceCompany = null;
        
        insuranceCompany = insuranceCompanyServiceToTest.getInsuranceCompany();
        
        assertEquals(insuranceCompany.getKVK(), kVK);
    }
}
