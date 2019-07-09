import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

public abstract class Transformateur implements Transformable {

    //Liste des fichiers pris en charge par mon transformateur

    public static List<String> noms_fichs_pris = Arrays.asList("fiches.txt",  "boitedialog.fxml","renault.html");

    protected String source;
    protected String cible;
    protected String nom;
    protected Transformer transformer;

    @Override
    public void transform(String source, String cible, String nom_fichier) throws FileNotFoundException {
        Transformateur T = Transformateur.transformateurBuilder(source,
                cible,
                nom_fichier);
        T.genrerTransformation();


    }

    protected Transformateur(String source, String cible, String nom_fichier) {
        final TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        this.source = source;
        this.cible = cible;
        this.nom = nom_fichier;
    }

    public static Transformateur transformateurBuilder(String source, String cible, String nom_fichier) throws FileNotFoundException {
        Transformateur T = null;
        switch (nom_fichier) {
            case "renault.html":
                T = new Transformateur_Renault(source, cible, nom_fichier);
                break;
            case "boitedialog.fxml":
                T = new Transformateur_BoiteDialogue(source, cible, nom_fichier);
                break;
            case "fiches.txt":
                if(cible.equals("fiches1.xml"))
                {
                    T = new Transformateur_Fich1(source, cible, nom_fichier);
                    ((Transformateur_FichTxt) T).lireFichier(source);
                }
                else
                {
                    T = new Transformateur_Fich2(source, cible, nom_fichier);
                    ((Transformateur_FichTxt) T).lireFichier(source);
                }
                break;
        }
        return T;
    }

    public static void main(String[] str) throws FileNotFoundException {

    }

    public void setSourceCible(String source, String cible) {
        this.source = source;
        this.cible = cible;
    }

    public abstract void insererDansCible(Document D, Element E);


    public void genrerTransformation() {
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {

            final DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation DMIP = builder.getDOMImplementation();

            final Document document = DMIP.createDocument(null,null,null);


            //Transformateur_FichTxt TF = new Transformateur_FichTxt();
            //TF.lireFichier("C:\\Users\\Geekzone\\Desktop\\Projet_Documents_Structures\\examen\\examen_bis\\poeme\\fiches\\fiches.txt");
            //TF.ajouterContenuDocument(document);
            //Transformateur_Renault TR = new Transformateur_Renault();
            //TR.createElement(document);

            Transformateur T = Transformateur.transformateurBuilder(source, cible, nom);
            T.insererDansCible(document, document.createElement("R"));
            DOMImplementation domImpl = document.getImplementation();
            if(cible.endsWith("fiches1.xml")) {
                DocumentType doctype = domImpl.createDocumentType("doctype",
                        null,
                        "fiche.dtd");
                transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());
            }
            final DOMSource source = new DOMSource(document);

            final StreamResult sortie = new StreamResult(new File(cible));

            transformer.setOutputProperty(OutputKeys.VERSION, "1.0");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, sortie);
        } catch (ParserConfigurationException | TransformerException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
