/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.avans.C3.BusinessLogic;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import nl.avans.C3.Domain.Client;
import nl.avans.C3.Domain.ClientNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Stefan
 */
@Service
public class SEPAService {
    private ClientService clientService;
    private CompanyService companyService;
    private InvoiceService invoiceService;
    private InsuranceContractService insuranceContractService;
    
    @Autowired
    public void setTreatmentRepository(ClientService clientService, CompanyService companyService, InvoiceService invoiceService, InsuranceContractService insuranceContractService) {
        this.clientService = clientService;
        this.companyService = companyService;
        this.invoiceService = invoiceService;
        this.insuranceContractService = insuranceContractService;
    }
    
    
    public void generateSEPA(String sepaBSN, int[] behandelCode) throws ClientNotFoundException, TransformerException, ParseException, ParserConfigurationException {
        Client client = clientService.findClientByBSN(Integer.parseInt(sepaBSN));
        
        String firstName = client.getFirstName();
        String lastName = client.getLastName();
        
        String creationDateTime = LocalDateTime.now().toString();
        
        double totaalBedrag = invoiceService.getTotaalBedrag(behandelCode);
        double excess = insuranceContractService.getInsuranceContractByBSN(Integer.parseInt(sepaBSN)).getExcess();
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
        String totalAmount = String.valueOf(teBetalenBedrag);
        
        Date date = new Date();
        String dt = new SimpleDateFormat("yyyy-MM-dd").format(date);
        String fileId = "pain-" + dt + "-" + sepaBSN;
        String description = "Afschrijving behandeling(en) door " + companyService.getCompany().getName() + " op " + dt;
        String creationDate = dt;
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(dt));
        c.add(Calendar.DATE, 30);
        dt = sdf.format(c.getTime());
        String collectDate = dt;
        
        String clientIBAN = client.getIBAN();
        String clientBIC = client.getIBAN();
        String clientName = firstName + " " + lastName;
        
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        
        Document document = documentBuilder.newDocument();
        
        //Document
        Element sepa = document.createElement("Document");
        document.appendChild(sepa);
        
        //eerste attribuut van Document
        Attr attr1 = document.createAttribute("xmlns");
        attr1.setValue("urn:iso:std:iso:20022:tech:xsd:pain.008.001.02");
        sepa.setAttributeNode(attr1);
        
        //tweede attribuut van document
        Attr attr2 = document.createAttribute("xmlns:xsi");
        attr2.setValue("http://www.w3.org/2001/XMLSchema-instance");
        sepa.setAttributeNode(attr2);
        
        //CstmrDrctDbtInitn
        Element cstmrDrctDbtInitn = document.createElement("CstmrDrctDbtInitn");
        sepa.appendChild(cstmrDrctDbtInitn);
        
        //GrpHdr
        Element grpHdr = document.createElement("GrpHdr");
        cstmrDrctDbtInitn.appendChild(grpHdr);
        
        //MsgId
        Element msgId = document.createElement("MsgId");
        msgId.appendChild(document.createTextNode(fileId));
        grpHdr.appendChild(msgId);
        
        //CreDtTm
        Element creDtTm = document.createElement("CreDtTm");
        creDtTm.appendChild(document.createTextNode(creationDateTime));
        grpHdr.appendChild(creDtTm);
        
        //NbOfTxs
        Element nbOfTxs = document.createElement("NbOfTxs");
        nbOfTxs.appendChild(document.createTextNode("1"));
        grpHdr.appendChild(nbOfTxs);
        
        //CtrlSum
        Element ctrlSum = document.createElement("CtrlSum");
        ctrlSum.appendChild(document.createTextNode(totalAmount));
        grpHdr.appendChild(ctrlSum);
        
        //InitgPty
        Element initgPty = document.createElement("InitgPty");
        grpHdr.appendChild(initgPty);
        
        //Nm
        Element nm = document.createElement("Nm");
        nm.appendChild(document.createTextNode("Fysio C3"));
        initgPty.appendChild(nm);
        
        //PmtInf
        Element pmtInf = document.createElement("PmtInf");
        cstmrDrctDbtInitn.appendChild(pmtInf);
        
        //PmtInfId
        Element pmtInfId = document.createElement("PmtInfId");
        pmtInfId.appendChild(document.createTextNode("19626314-OOFF"));
        pmtInf.appendChild(pmtInfId);
        
        //PmtMtd
        Element pmtMtd = document.createElement("PmtMtd");
        pmtMtd.appendChild(document.createTextNode("DD"));
        pmtInf.appendChild(pmtMtd);
        
        //BtchBookg
        Element btchBookg = document.createElement("BtchBookg");
        btchBookg.appendChild(document.createTextNode("true"));
        pmtInf.appendChild(btchBookg);
        
        //NbOfTxs
        Element nbOfTxs2 = document.createElement("NbOfTxs");
        nbOfTxs2.appendChild(document.createTextNode("1"));
        pmtInf.appendChild(nbOfTxs2);
        
        //CtrlSum
        Element ctrlSum2 = document.createElement("CtrlSum");
        ctrlSum2.appendChild(document.createTextNode(totalAmount));
        pmtInf.appendChild(ctrlSum2);
        
        //PmtTpInf
        Element pmtTpInf = document.createElement("PmtTpInf");
        pmtInf.appendChild(pmtTpInf);
        
        //SvcLvl
        Element svcLvl = document.createElement("SvcLvl");
        pmtTpInf.appendChild(svcLvl);
        
        //Cd
        Element cd = document.createElement("Cd");
        cd.appendChild(document.createTextNode("SEPA"));
        svcLvl.appendChild(cd);
        
        //LclInstrm
        Element lclInstrm = document.createElement("LclInstrm");
        pmtTpInf.appendChild(lclInstrm);
        
        //Cd
        Element cd2 = document.createElement("Cd");
        cd2.appendChild(document.createTextNode("CORE"));
        lclInstrm.appendChild(cd2);
        
        //SeqTp
        Element seqTp = document.createElement("SeqTp");
        seqTp.appendChild(document.createTextNode("OOFF"));
        pmtTpInf.appendChild(seqTp);
        
        //ReqdColltnDt
        Element reqdColltnDt = document.createElement("ReqdColltnDt");
        reqdColltnDt.appendChild(document.createTextNode(collectDate));
        pmtInf.appendChild(reqdColltnDt);
        
        //Cdtr
        Element cdtr = document.createElement("Cdtr");
        pmtInf.appendChild(cdtr);
        
        //Nm
        Element mn2 = document.createElement("Nm");
        mn2.appendChild(document.createTextNode("Fysio C3"));
        cdtr.appendChild(mn2);
        
        //CdtrAcct
        Element cdtrAcct = document.createElement("CdtrAcct");
        pmtInf.appendChild(cdtrAcct);
        
        //Id
        Element id = document.createElement("Id");
        cdtrAcct.appendChild(id);
        
        //IBAN
        Element iBAN = document.createElement("IBAN");
        iBAN.appendChild(document.createTextNode("NL44RABO0123456789"));
        id.appendChild(iBAN);
        
        //CdtrAgt
        Element cdtrAgt = document.createElement("CdtrAgt");
        pmtInf.appendChild(cdtrAgt);
        
        //FinInstnId
        Element finInstnId = document.createElement("FinInstnId");
        cdtrAgt.appendChild(finInstnId);
        
        //BIC
        Element bIC = document.createElement("BIC");
        bIC.appendChild(document.createTextNode("RABONL2U"));
        finInstnId.appendChild(bIC);
        
        //ChrgBr
        Element chrgBr = document.createElement("ChrgBr");
        chrgBr.appendChild(document.createTextNode("SLEV"));
        pmtInf.appendChild(chrgBr);
        
        //CdtrSchmeId
        Element cdtrSchmeId = document.createElement("CdtrSchmeId");
        pmtInf.appendChild(cdtrSchmeId);
        
        //Id
        Element id2 = document.createElement("Id");
        cdtrSchmeId.appendChild(id2);
        
        //PrvtId
        Element prvtId = document.createElement("PrvtId");
        id2.appendChild(prvtId);
        
        //Othr
        Element othr = document.createElement("Othr");
        prvtId.appendChild(othr);
        
        //Id
        Element id3 = document.createElement("Id");
        id3.appendChild(document.createTextNode("INCASSO1"));
        othr.appendChild(id3);
        
        //SchmeNm
        Element schmeNm = document.createElement("SchmeNm");
        othr.appendChild(schmeNm);
        
        //Prtry
        Element prtry = document.createElement("Prtry");
        prtry.appendChild(document.createTextNode("SEPA"));
        schmeNm.appendChild(prtry);
        
        //DrctDbtTxInf
        Element drctDbtTxInf = document.createElement("DrctDbtTxInf");
        pmtInf.appendChild(drctDbtTxInf);
        
        //PmtId
        Element pmtId = document.createElement("PmtId");
        drctDbtTxInf.appendChild(pmtId);
        
        //EndToEndId
        Element endToEndId = document.createElement("EndToEndId");
        endToEndId.appendChild(document.createTextNode("DDT-938-1962-2777152746"));
        pmtId.appendChild(endToEndId);
        
        //InstdAmt
        Element instdAmt = document.createElement("InstdAmt");
        instdAmt.appendChild(document.createTextNode(totalAmount));
        drctDbtTxInf.appendChild(instdAmt);
        
        //derde attribuut van document
        Attr attr3 = document.createAttribute("Ccy");
        attr3.setValue("EUR");
        instdAmt.setAttributeNode(attr3);
        
        //DrctDbtTx
        Element drctDbtTx = document.createElement("DrctDbtTx");
        drctDbtTxInf.appendChild(drctDbtTx);
        
        //MndtRltdInf
        Element mndtRltdInf = document.createElement("MndtRltdInf");
        drctDbtTx.appendChild(mndtRltdInf);
        
        //MndtId
        Element mndtId = document.createElement("MndtId");
        mndtId.appendChild(document.createTextNode("DDT-938-1962-27771"));
        mndtRltdInf.appendChild(mndtId);
        
        //DtOfSgntr
        Element dtOfSgntr = document.createElement("DtOfSgntr");
        dtOfSgntr.appendChild(document.createTextNode(creationDate));
        mndtRltdInf.appendChild(dtOfSgntr);
        
        //DbtrAgt
        Element dbtrAgt = document.createElement("DbtrAgt");
        drctDbtTxInf.appendChild(dbtrAgt);
        
        //FinInstnId
        Element finInstnId2 = document.createElement("FinInstnId");
        dbtrAgt.appendChild(finInstnId2);
        
        //BIC
        Element bIC2 = document.createElement("BIC");
        bIC2.appendChild(document.createTextNode(clientBIC));
        finInstnId2.appendChild(bIC2);
        
        //Dbtr
        Element dbtr = document.createElement("Dbtr");
        drctDbtTxInf.appendChild(dbtr);
        
        //Nm
        Element nm3 = document.createElement("Nm");
        nm3.appendChild(document.createTextNode(clientName));
        dbtr.appendChild(nm3);
        
        //DbtrAcct
        Element dbtrAcct = document.createElement("DbtrAcct");
        drctDbtTxInf.appendChild(dbtrAcct);
        
        //Id
        Element id4 = document.createElement("Id");
        dbtrAcct.appendChild(id4);
        
        //IBAN
        Element iBAN2 = document.createElement("IBAN");
        iBAN2.appendChild(document.createTextNode(clientIBAN));
        id4.appendChild(iBAN2);
        
        //RmtInf
        Element rmtInf = document.createElement("RmtInf");
        drctDbtTxInf.appendChild(rmtInf);
        
        //Ustrd
        Element ustrd = document.createElement("Ustrd");
        ustrd.appendChild(document.createTextNode(description));
        rmtInf.appendChild(ustrd);
        
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        DOMSource source = new DOMSource(document);
        
        if(teBetalenBedrag > 0){
            StreamResult streamResult = new StreamResult(new File("generatedfiles/sepa/" + fileId + ".xml"));
            transformer.transform(source, streamResult);
        }
        else{
            //er hoeft geen SEPA incasso aangemaakt te worden
        }
    }
}
