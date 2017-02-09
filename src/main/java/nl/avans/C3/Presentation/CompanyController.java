/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.avans.C3.Presentation;

import nl.avans.C3.BusinessLogic.CompanyService;
import nl.avans.C3.Domain.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Stefan
 */

@Controller
public class CompanyController {
    private CompanyService companyService;
    
    private String name;
    private String city;
    private String postalCode;
    private String address;
    private String country;
    private int kVK;
    
    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }
    
    @RequestMapping("/company")
    String company(Model model) {
        name = companyService.getCompany().getName();
        model.addAttribute("name", name);
        
        city = companyService.getCompany().getCity();
        model.addAttribute("city", city);
        
        postalCode = companyService.getCompany().getPostalCode();
        model.addAttribute("postalCode", postalCode);
        
        address = companyService.getCompany().getAddress();
        model.addAttribute("address", address);
        
        country = companyService.getCompany().getCountry();
        model.addAttribute("country", country);
        
        kVK = companyService.getCompany().getKVK();
        model.addAttribute("kVK", kVK);
        
        return "views/company/company";
    }
   
    @RequestMapping(value="/editcompany")
    String editcompany(Model model) {
        name = companyService.getCompany().getName();
        model.addAttribute("name", name);
        
        city = companyService.getCompany().getCity();
        model.addAttribute("city", city);
        
        postalCode = companyService.getCompany().getPostalCode();
        model.addAttribute("postalCode", postalCode);
        
        address = companyService.getCompany().getAddress();
        model.addAttribute("address", address);
        
        country = companyService.getCompany().getCountry();
        model.addAttribute("country", country);
        
        kVK = companyService.getCompany().getKVK();
        model.addAttribute("kVK", kVK);
        
        return "views/company/editcompany";
    }
    
    @RequestMapping(value = "/editcompany", method = RequestMethod.POST)
    @ResponseBody
    public String companySubmit(
            @RequestParam(value = "name") String nameEdit,
            @RequestParam(value = "city") String cityEdit,
            @RequestParam(value = "postalCode") String postalCodeEdit,
            @RequestParam(value = "address") String addressEdit,
            @RequestParam(value = "country") String countryEdit,
            @RequestParam(value = "kVK") String kVKEdit
    ) {
        companyService.editCompany(nameEdit, cityEdit, postalCodeEdit, addressEdit, countryEdit, Integer.parseInt(kVKEdit));
        return "<script>window.location.href = \"/editconfirmed\";</script>";
        //return "Stamgegevens aangepast!<br/><br/><a href='/'>Klik hier om terug te keren naar het hoodfdmenu</a>";
    }
    
    @RequestMapping("/editconfirmed")
    public String home(Model model) {
        return "views/company/editconfirmed";
    }
}