/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.avans.C3.BusinessLogic;

import com.itextpdf.text.DocumentException;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import nl.avans.C3.Domain.Client;
import nl.avans.C3.Domain.ClientNotFoundException;
import nl.avans.C3.Domain.SearchQuery;
import nl.avans.C3.Domain.Treatment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Stefan
 */

@Controller
public class InvoiceController {
    private ClientService clientService;
    private InvoiceService invoiceService;
    private SEPAService sEPAService;
    private InsuranceContractService insuranceContractService;
    
    private int[] behandelCode = {002,003,006,005};
    
    @Autowired
    public InvoiceController(ClientService clientService, InvoiceService invoiceService, SEPAService sEPAService, InsuranceContractService insuranceContractService) {
        this.clientService = clientService;
        this.invoiceService = invoiceService;
        this.sEPAService = sEPAService;
        this.insuranceContractService = insuranceContractService;
    }
    
    @RequestMapping("/invoice")
    public String Index(ModelMap model) {
        List<Client> clients = clientService.findAllClients();
        
        InitializeSearchOptions(model);
        SearchQuery searchquery = new SearchQuery();
        
        model.addAttribute("clients", clients);
        model.addAttribute("searchquery", searchquery);
        
        return "views/invoice/invoice";
    }
    
    @RequestMapping(value = "/invoice", method = RequestMethod.POST)
    public String Index(@Valid SearchQuery searchquery, final BindingResult bindingResult, final ModelMap model) {
        List<Client> clients = new ArrayList<Client>();
        
        InitializeSearchOptions(model);
        
        if(searchquery.getSearchWords().length() > 0){
            try {
                switch (searchquery.getSearchOption()) {
                    case "BSN":
                        clients = clientService.findClientsByBSN(searchquery.getSearchWords());
                        break;
                    case "Voornaam":
                        clients = clientService.findClientsByFirstName(searchquery.getSearchWords());
                        break;
                    case "Achternaam":
                        clients = clientService.findClientsByLastname(searchquery.getSearchWords());
                        break;
                }
            } catch(ClientNotFoundException ex){
                //logger.error(ex.getMessage());
            }
        }
        
        model.addAttribute("clients", clients);
        model.addAttribute("searchquery", searchquery);
        return "views/invoice/invoice";
    }

    private void InitializeSearchOptions(ModelMap model){
        ArrayList<String> searchOptions = new ArrayList<>();
        searchOptions.add("BSN");
        searchOptions.add("Voornaam");
        searchOptions.add("Achternaam");
        
        model.addAttribute("searchOptions", searchOptions);
    }
    
    @RequestMapping(value = "/clientinvoice/{bSN}", method = RequestMethod.GET)
    public String getClientByBSN(@PathVariable int bSN, ModelMap model) throws ClientNotFoundException {
        Client client = clientService.findClientByBSN(bSN);
        
        String firstName = client.getFirstName();
        String lastName = client.getLastName();
        
        List<Treatment> treatments = invoiceService.getTreatments(behandelCode);
        double totaalBedrag = invoiceService.getTotaalBedrag(behandelCode);
        double excess = insuranceContractService.getInsuranceContractByBSN(bSN).getExcess();
        double teBetalenBedrag;
        if (excess > 0){
            if(excess > totaalBedrag){
                teBetalenBedrag = totaalBedrag;
            }
            else{
                teBetalenBedrag = excess;
            }
        }
        else{
            teBetalenBedrag = 0.00;
        }
        
        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);
        model.addAttribute("treatments", treatments); 
        model.addAttribute("totaalBedrag", totaalBedrag);
        model.addAttribute("bSN", bSN);
        model.addAttribute("excess", excess);
        model.addAttribute("teBetalenBedrag", teBetalenBedrag);
        
        return "views/invoice/clientinvoice";
    }
    
    @RequestMapping(value = "/clientinvoice", method = RequestMethod.POST)
    @ResponseBody
    public String invoiceSubmit(@RequestParam(value = "bSN") String invoiceBSN) throws TransformerException, ParseException, ClientNotFoundException, ParserConfigurationException, DocumentException, FileNotFoundException {
        sEPAService.generateSEPA(invoiceBSN, behandelCode);
        invoiceService.generateInvoice(invoiceBSN, behandelCode);
        
        return "<script>window.location.href = \"/invoiceconfirmed/" + invoiceBSN + "\";</script>";
        //return "Factuur en SEPA incasso bestand gegenereerd.<br /> <br /><a href='/invoice'>Klik hier om meer facturen te genereren</a>";
    }
     
    @RequestMapping(value = "/invoiceconfirmed/{bSN}", method = RequestMethod.GET)
    public String getClientInvoiceByBSN(@PathVariable int bSN, ModelMap model) throws ClientNotFoundException {
        Client client = clientService.findClientByBSN(bSN);
        
        String firstName = client.getFirstName();
        String lastName = client.getLastName();
        
        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);
        
        return "views/invoice/invoiceconfirmed";
    }
}
