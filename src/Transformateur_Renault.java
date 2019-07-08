import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Transformateur_Renault extends Transformateur {

    public Transformateur_Renault(String source, String cible, String nom_fichier) {
        super(source, cible, nom_fichier);
    }

    public static void parcourRecursif(Node E, StringBuilder ss) {

        NodeList LN = E.getChildNodes();
        for (int i = 0; i < LN.getLength(); i++) {
            if (LN.item(i).getNodeValue() != null && !LN.item(i).getNodeValue().equals("null"))
                ss.append(LN.item(i).getNodeValue());
            if (E.hasChildNodes())
                parcourRecursif(LN.item(i), ss);
        }
    }



    public Element createElement(Document Doc) {
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder builder;
        final Document document;
        final Element racine2 = Doc.createElement("Concessionaires");
        Doc.appendChild(racine2);
        try {

            builder = factory.newDocumentBuilder();
            document = builder.parse(new File(source));
            final Element racine = document.getDocumentElement();
            StringBuilder ch = new StringBuilder("");
            parcourRecursif(racine, ch);
            String chaine = ch.toString();
            Pattern pattern = Pattern.compile("(\\s)+");
            Pattern pattern2 = Pattern.compile("Succursale .* end post");
            Matcher matcher = pattern.matcher(chaine);
            // check all occurance
            String retour = matcher.replaceAll(" ");
            Matcher matcher2 = pattern2.matcher(retour);
            System.out.println("Chaine traitée :");

            matcher2.find();
            String nch = matcher2.group();
            System.out.println(nch);
            Pattern pattern3 = Pattern.compile("(([A-Za-z]|\\s)*?) Adresse( )?: ((.*?)) Tél( )?: ((\\d+ \\d+ \\d+ \\d+)) (Fax :( )*\\d+ \\d+ \\d+ \\d+)*");
            Matcher matcher3 = pattern3.matcher(nch);
            while (matcher3.find()) {
                System.out.println("MATCHE : ");
                System.out.println("Total : " + matcher3.group());
                System.out.println("Nom : " + matcher3.group(1));
                Element D1 = Doc.createElement("Nom");
                D1.appendChild(Doc.createTextNode(matcher3.group(1)));
                racine2.appendChild(D1);
                System.out.println("Adresse : " + matcher3.group(4));
                Element D2 = Doc.createElement("Adresse");
                D2.appendChild(Doc.createTextNode(matcher3.group(4)));
                racine2.appendChild(D2);
                System.out.println("Téléphone : " + matcher3.group(7));
                Element D3 = Doc.createElement("Num_téléphone");
                D3.appendChild(Doc.createTextNode(matcher3.group(7)));
                racine2.appendChild(D3);
            }

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return racine2;
    }

    @Override
    public void insererDansCible(Document D, Element E) {
        E = createElement(D);
    }

}
