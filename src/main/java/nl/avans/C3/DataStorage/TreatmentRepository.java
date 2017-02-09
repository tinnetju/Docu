/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.avans.C3.DataStorage;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import nl.avans.C3.Domain.Treatment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 *
 * @author Stefan
 */
@Repository
public class TreatmentRepository implements TreatmentRepositoryIF {   
    String behandelingNaam = null;
    String aantalSessies = null;
    String sessieDuur = null;
    double tariefBehandeling = 0;
    
    public void readXML(int behandelCode) throws ParserConfigurationException, SAXException, IOException{
        File xmlFile = new File("behandelcodes.xml");
    
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(xmlFile);

        NodeList list = document.getElementsByTagName("behandeling");
        
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;
                
                if(Integer.parseInt(element.getAttribute("behandelcode")) == behandelCode){
                    behandelingNaam = element.getElementsByTagName("behandelingnaam").item(0).getTextContent();
                    aantalSessies = element.getElementsByTagName("aantalsessies").item(0).getTextContent();
                    sessieDuur = element.getElementsByTagName("sessieduur").item(0).getTextContent();
                    tariefBehandeling = Double.parseDouble(element.getElementsByTagName("tariefbehandeling").item(0).getTextContent().replaceAll(",","."));
                }
            }
        }
    }
    
    @Override
    public Treatment getTreatment(int behandelCode) {
        try {
            readXML(behandelCode);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(TreatmentRepository.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(TreatmentRepository.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TreatmentRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Treatment treatment = new Treatment(behandelCode, behandelingNaam, aantalSessies, sessieDuur, tariefBehandeling);
        
        return treatment;
    }
}
