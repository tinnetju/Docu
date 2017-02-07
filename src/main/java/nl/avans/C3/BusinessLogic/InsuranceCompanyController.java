/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.avans.C3.BusinessLogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Stefan
 */

@Controller
public class InsuranceCompanyController {
    private InsuranceCompanyService insuranceCompanyService;
    
    private String name;
    private String address;
    private String postalCode;
    private String city;
    private String country;
    private int kVK;
    
    @Autowired
    public InsuranceCompanyController(InsuranceCompanyService insuranceCompanyService) {
        this.insuranceCompanyService = insuranceCompanyService;
    }
    
    @RequestMapping("/company")
    String company(Model model) {
        name = insuranceCompanyService.getInsuranceCompany().getName();
        model.addAttribute("name", name);
        
        city = insuranceCompanyService.getInsuranceCompany().getCity();
        model.addAttribute("city", city);
        
        postalCode = insuranceCompanyService.getInsuranceCompany().getPostalCode();
        model.addAttribute("postalCode", postalCode);
        
        address = insuranceCompanyService.getInsuranceCompany().getAddress();
        model.addAttribute("address", address);
        
        country = insuranceCompanyService.getInsuranceCompany().getCountry();
        model.addAttribute("country", country);
        
        kVK = insuranceCompanyService.getInsuranceCompany().getKVK();
        model.addAttribute("kVK", kVK);
        
        return "company";
    }
   
    @RequestMapping(value="/editcompany")
    String editcompany(Model model) {
        name = insuranceCompanyService.getInsuranceCompany().getName();
        model.addAttribute("name", name);
        
        city = insuranceCompanyService.getInsuranceCompany().getCity();
        model.addAttribute("city", city);
        
        postalCode = insuranceCompanyService.getInsuranceCompany().getPostalCode();
        model.addAttribute("postalCode", postalCode);
        
        address = insuranceCompanyService.getInsuranceCompany().getAddress();
        model.addAttribute("address", address);
        
        country = insuranceCompanyService.getInsuranceCompany().getCountry();
        model.addAttribute("country", country);
        
        kVK = insuranceCompanyService.getInsuranceCompany().getKVK();
        model.addAttribute("kVK", kVK);
        
        return "editcompany";
    }
    
    @RequestMapping(value = "/editcompany", method = RequestMethod.POST)
    @ResponseBody
    public String companySubmit(
            @RequestParam(value = "name") String nameEdit,
            @RequestParam(value = "city") String cityEdit,
            @RequestParam(value = "postalCode") String postalCodeEdit,
            @RequestParam(value = "address") String addressEdit,
            @RequestParam(value = "country") String countryEdit,
            @RequestParam(value = "kVK") int kVKEdit
    ) {
        insuranceCompanyService.editInsuranceCompany(nameEdit, cityEdit, postalCodeEdit, addressEdit, countryEdit, kVKEdit);
        return "<a href='/company'>Keer terug</a>";
    }
}