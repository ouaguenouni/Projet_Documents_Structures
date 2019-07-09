import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class Transformateur_BoiteDialogue extends Transformateur {

    protected Transformateur_BoiteDialogue(String source, String cible, String nom_fichier) {
        super(source, cible, nom_fichier);
    }

    public static void afficherAttributsNode(Node N) {
        NamedNodeMap Attr = N.getAttributes();
        if (Attr != null)
            for (int k = 0; k < Attr.getLength(); k++) {
                System.out.println(Attr.item(k).getNodeValue());
            }
    }

    public static void ajouterAttributsElement(Node N, Element E, Document D) {
        NamedNodeMap Attr = N.getAttributes();
        if (Attr != null)
            for (int k = 0; k < Attr.getLength(); k++) {
                Element Elem = D.createElement("texte");
                System.out.println(Attr.item(k).getNodeValue());
                Node TEXT = D.createTextNode(Attr.item(k).getNodeValue());
                Node attribut = D.createAttribute(Attr.item(k).getNodeName());
                ((Attr) attribut).setValue("x");
                Elem.appendChild(TEXT);
                Elem.setAttribute(Attr.item(k).getNodeName(), "x");
                E.appendChild(Elem);
            }
    }

    public static void parcourRecursif(Node E, Element Elem, Document D) {
        ajouterAttributsElement(E, Elem, D);
        NodeList LN = E.getChildNodes();
        for (int i = 0; i < LN.getLength(); i++) {
            if (E.hasChildNodes()) {
                parcourRecursif(LN.item(i), Elem, D);
            }
        }
    }

    public Element addDocument(Document D) throws ParserConfigurationException, IOException, SAXException {
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder builder;
        final Document document;
        builder = factory.newDocumentBuilder();
        document = builder.parse(new File(source));
        final Element racine = document.getDocumentElement();
        Element E = D.createElement("Racine");
        E.setAttribute("xmlns:fx", "http://javafx.com/fxml");
        D.appendChild(E);
        parcourRecursif(racine, E, D);
        return E;
    }


    @Override
    public void insererDansCible(Document D, Element E) {
        try {
            E = addDocument(D);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }
}
