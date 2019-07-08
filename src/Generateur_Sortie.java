import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class Generateur_Sortie {


    public static void main(String str[]) throws IOException, SAXException {
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            final DocumentBuilder builder = factory.newDocumentBuilder();
            final Document document = builder.newDocument();
            //Transformateur_FichTxt TF = new Transformateur_FichTxt();
            //TF.lireFichier("C:\\Users\\Geekzone\\Desktop\\Projet_Documents_Structures\\examen\\examen_bis\\poeme\\fiches\\fiches.txt");
            //TF.ajouterContenuDocument(document);
            //Transformateur_Renault TR = new Transformateur_Renault();
            //TR.createElement(document);
            //Transformateur_BoiteDialogue TBD = new Transformateur_BoiteDialogue();
            //TBD.addDocument(document);
            final TransformerFactory transformerFactory = TransformerFactory.newInstance();
            final Transformer transformer = transformerFactory.newTransformer();
            final DOMSource source = new DOMSource(document);
            final StreamResult sortie = new StreamResult(new File("C:\\Users\\Geekzone\\Desktop\\Projet_Documents_Structures\\Rez.xml"));
            transformer.setOutputProperty(OutputKeys.VERSION, "1.0");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, sortie);
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

}
