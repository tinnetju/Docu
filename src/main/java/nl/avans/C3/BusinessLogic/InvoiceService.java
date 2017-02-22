/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.avans.C3.BusinessLogic;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import nl.avans.C3.DataStorage.TreatmentRepositoryIF;
import nl.avans.C3.Domain.Client;
import nl.avans.C3.Domain.ClientNotFoundException;
import nl.avans.C3.Domain.Treatment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Stefan
 */
@Service
public class InvoiceService {
    private TreatmentRepositoryIF treatmentRepositoryIF;
    private InsuranceContractService insuranceContractService;
    private ClientService clientService;
    private InsuranceCompanyService companyService;
    
    @Autowired
    public void setTreatmentRepository(TreatmentRepositoryIF treatmentRepositoryIF, InsuranceContractService insuranceContractService, ClientService clientService, InsuranceCompanyService companyService) {
        this.treatmentRepositoryIF = treatmentRepositoryIF;
        this.insuranceContractService = insuranceContractService;
        this.clientService = clientService;
        this.companyService = companyService;
    }
    
    public List<Treatment> getTreatments(int[] behandelCode) {
        List<Treatment> treatments = new LinkedList<>();
        
        boolean komtVoor = false;
        int komtVoorIndex = 0;
        
        DecimalFormat df = new DecimalFormat("#.#");
        
        for(int i = 0; i < behandelCode.length; i++) {
            
            for(int x = 0; x < treatments.size(); x++){
                if(treatments.get(x).getBehandelCode() == behandelCode[i]){
                    komtVoor = true;
                    komtVoorIndex = x;
                }
                else{
                    //doe niks
                }
            }
            
            if(komtVoor == false){
                treatments.add(treatmentRepositoryIF.getTreatment(behandelCode[i]));
            }
            else{
                int newIntAantalSessies;
                String newStringAantalSessies;
                newIntAantalSessies = Integer.parseInt(treatments.get(komtVoorIndex).getAantalSessies()) + Integer.parseInt(treatmentRepositoryIF.getTreatment(behandelCode[i]).getAantalSessies());
                newStringAantalSessies = String.valueOf(newIntAantalSessies);
                
                double newDoubleSessieDuur;
                String newStringSessieDuur;
                newDoubleSessieDuur = Double.parseDouble(treatments.get(komtVoorIndex).getSessieDuur().replaceAll(",",".")) + Double.parseDouble(treatmentRepositoryIF.getTreatment(behandelCode[i]).getSessieDuur().replaceAll(",","."));
                newStringSessieDuur = String.valueOf(df.format(newDoubleSessieDuur));
                
                treatments.set(komtVoorIndex, new Treatment(
                    behandelCode[i], 
                    treatments.get(komtVoorIndex).getBehandelingNaam(), 
                    newStringAantalSessies,
                    newStringSessieDuur,
                    treatments.get(komtVoorIndex).getTariefBehandeling() + treatmentRepositoryIF.getTreatment(behandelCode[i]).getTariefBehandeling())
                );
                
                komtVoor = false;
            }
        }
        
        return treatments;
    }
    
    public double getTotaalBedrag(int[] behandelCode) {
        double totaalBedrag = 0;
        
        for(int i = 0; i < behandelCode.length; i++) {
            totaalBedrag += treatmentRepositoryIF.getTreatment(behandelCode[i]).getTariefBehandeling();
        }
        
        return totaalBedrag;
    }
    
    public void generateInvoice(String invoiceBSN, int[] behandelCode) throws DocumentException, FileNotFoundException, ClientNotFoundException, ParseException {
        Date date = new Date();
        String dt = new SimpleDateFormat("yyyy-MM-dd").format(date);
        String fileId = "invoice-" + dt + "-" + invoiceBSN;
        
        String companyName = companyService.getInsuranceCompany().getName();
        String companyAddress = companyService.getInsuranceCompany().getAddress();
        String companyPostalCode = companyService.getInsuranceCompany().getPostalCode();
        String companyCity = companyService.getInsuranceCompany().getCity();
        
        String companyKVK = Integer.toString(companyService.getInsuranceCompany().getKVK());
        String companyIBAN = "NL91ABNA0417164300";
        
        Client client = clientService.findClientByBSN(Integer.parseInt(invoiceBSN));
        String clientFirstName = client.getFirstName();
        String clientLastName = client.getLastName();
        String clientAddress = client.getAddress();
        String clientPostalCode = client.getPostalCode();
        String clientCity = client.getCity();
        boolean incasso = client.isIncasso();
        
        Date date2 = new Date();
        String dt2 = new SimpleDateFormat("dd-MM-yyyy").format(date2);
        String currentDate = dt2;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(dt2));
        c.add(Calendar.MONTH, 1);
        dt2 = sdf.format(c.getTime());
        String expirationDate = dt2;
        
        String invoiceNumber = dt + "-" + invoiceBSN;
        
        Font bfBold12 = new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0)); 
        Font bf12 = new Font(FontFamily.TIMES_ROMAN, 12);
        
        // Creating new file
        Document doc = new Document();
        
        // Trying to parse the data to a new PDF
        PdfWriter.getInstance(doc, new FileOutputStream("generatedfiles/invoice/" + fileId + ".pdf"));
        
        // Opening the file
        doc.open();

        // Parsing the order details to the PDF
        doc.add(new Paragraph(
            companyName + "\n" + companyAddress + "\n" + companyPostalCode + " " + companyCity + "\n\n" + 
            "KVK: " + companyKVK + "\nIBAN: " + companyIBAN + "\n\n\n" + 
            clientFirstName + " " + clientLastName + "\n" + clientAddress + "\n" + clientPostalCode + " " + clientCity + "\n\n" +
            "Factuurdatum: " + currentDate + "\nVerloopdatum: " + expirationDate + "\n\n\n" +
            "Factuurnummer: " + invoiceNumber + "\n\n\n\n"
        ));
        
        float[] columnWidths = {2f, 2f, 2f, 2f, 2f};
        PdfPTable table = new PdfPTable(columnWidths);
        table.setWidthPercentage(100f);
        
        insertCell(table, "Behandelcode", Element.ALIGN_LEFT, 1, bfBold12);
        insertCell(table, "Behandelingnaam", Element.ALIGN_LEFT, 1, bfBold12);
        insertCell(table, "Aantal sessies", Element.ALIGN_LEFT, 1, bfBold12);
        insertCell(table, "Sessieduur", Element.ALIGN_LEFT, 1, bfBold12);
        insertCell(table, "Tarief", Element.ALIGN_LEFT, 1, bfBold12);
        table.setHeaderRows(1);
        
        List<Treatment> treatments = getTreatments(behandelCode);
        
        for(int i = 0; i < treatments.size(); i++) {
            insertCell(table, "" + treatments.get(i).getBehandelCode(), Element.ALIGN_LEFT, 1, bf12);
            insertCell(table, "" + treatments.get(i).getBehandelingNaam(), Element.ALIGN_LEFT, 1, bf12);
            insertCell(table, "" + treatments.get(i).getAantalSessies(), Element.ALIGN_LEFT, 1, bf12);
            insertCell(table, "" + treatments.get(i).getSessieDuur(), Element.ALIGN_LEFT, 1, bf12);
            insertCell(table, "€" + treatments.get(i).getTariefBehandeling(), Element.ALIGN_RIGHT, 1, bf12);
        }
        
        //totaalbedrag zonder eigen risico
        double totaalBedrag = getTotaalBedrag(behandelCode);
        insertCell(table, "Totaalbedrag: ", Element.ALIGN_RIGHT, 4, bfBold12);
        insertCell(table, "€" + totaalBedrag, Element.ALIGN_RIGHT, 1, bfBold12);
        
        //lege row
        insertCell(table, "", Element.ALIGN_RIGHT, 5, bf12);
        
        //huidig eigen risico
        double excess = insuranceContractService.getInsuranceContractByBSN(Integer.parseInt(invoiceBSN)).getExcess();
        insertCell(table, "Huidig eigen risico: ", Element.ALIGN_RIGHT, 4, bf12);
        insertCell(table, "€" + excess, Element.ALIGN_RIGHT, 1, bf12);
        
        //totaal te betalen bedrag
        double teBetalenBedrag;
        double newExcess = excess - totaalBedrag;
        insertCell(table, "Te betalen bedrag: ", Element.ALIGN_RIGHT, 4, bfBold12);
        if (excess > 0){
            if(excess > totaalBedrag){
                insuranceContractService.updateInsuranceContractExcess(newExcess, insuranceContractService.getInsuranceContractByBSN(Integer.parseInt(invoiceBSN)).getInsuranceContractID());
                insertCell(table, "€" + totaalBedrag, Element.ALIGN_RIGHT, 1, bfBold12);
                teBetalenBedrag = totaalBedrag;
            }
            else{
                insuranceContractService.updateInsuranceContractExcess(0.00, insuranceContractService.getInsuranceContractByBSN(Integer.parseInt(invoiceBSN)).getInsuranceContractID());
                insertCell(table, "€" + excess, Element.ALIGN_RIGHT, 1, bfBold12);
                teBetalenBedrag = excess;
            }
        }
        else{
            insertCell(table, "€0.0", Element.ALIGN_RIGHT, 1, bfBold12);
            teBetalenBedrag = 0.00;
        }
        
        Paragraph paragraph = new Paragraph("");
        paragraph.add(table);
        doc.add(paragraph);
        
        doc.add(new Paragraph("\n\n"));
        
        if (newExcess > 0){
            doc.add(new Paragraph("U heeft nog €" + newExcess + " eigen risico over."));
        }
        else{
            doc.add(new Paragraph("U heeft nog €0.0 eigen risico over."));
        }
        
        doc.add(new Paragraph("\n\n"));
        if(incasso == false){
            if (teBetalenBedrag > 0){
                doc.add(new Paragraph("We verzoeken u vriendelijk het bovenstaande bedrag van €" + teBetalenBedrag + " voor " + expirationDate + " te voldoen op onze bankrekening onder vermelding van het factuurnummer " + invoiceNumber + ". Voor vragen kunt u contact opnemen per e-mail."));
            }
            else{
                doc.add(new Paragraph("Omdat uw eigen risico op is worden er geen kosten in rekening gebracht voor de bovenstaande behandelingen."));
            }
        }
        else{
            doc.add(new Paragraph("Het geld zal binnen 10 werkdagen van uw rekening afgeschreven worden door middel van een automatische incasso."));
        }
        
        // Closing the file
        doc.close();
    }
    
    private void insertCell(PdfPTable table, String text, int align, int colspan, Font font){
        //create a new cell with the specified Text and Font
        PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
        //set the cell alignment
        cell.setHorizontalAlignment(align);
        //set the cell column span in case you want to merge two or more cells
        cell.setColspan(colspan);
        //in case there is no text and you wan to create an empty row
        if(text.trim().equalsIgnoreCase("")){
         cell.setMinimumHeight(10f);
        }
        //add the call to the table
        table.addCell(cell);
    }
}
