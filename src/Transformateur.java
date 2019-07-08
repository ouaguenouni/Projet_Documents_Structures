import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
import java.io.FileNotFoundException;

public abstract class Transformateur {
    protected String source;
    protected String cible;
    protected String nom;

    protected Transformateur(String source, String cible, String nom_fichier) {
        this.source = source;
        this.cible = cible;
        this.nom = nom_fichier;
    }

    public static Transformateur transformateurBuilder(String source, String cible, String nom_fichier) throws FileNotFoundException {
        Transformateur T = null;
        switch (nom_fichier) {
            case "Renault":
                T = new Transformateur_Renault(source, cible, nom_fichier);
                break;
            case "BoiteDeDialogue":
                T = new Transformateur_BoiteDialogue(source, cible, nom_fichier);
                break;
            case "Fich1":
                T = new Transformateur_Fich1(source, cible, nom_fichier);
                ((Transformateur_FichTxt) T).lireFichier(source);
                break;
            case "Fich2":
                T = new Transformateur_Fich2(source, cible, nom_fichier);
                ((Transformateur_FichTxt) T).lireFichier(source);
                break;

        }
        return T;
    }

    public static void main(String[] str) throws FileNotFoundException {
        Transformateur T = Transformateur.transformateurBuilder("C:\\Users\\Geekzone\\Desktop\\Projet_Documents_Structures\\examen\\examen_bis\\poeme\\fiches\\fiches.txt",
                "C:\\Users\\Geekzone\\Desktop\\Projet_Documents_Structures\\Rez.xml",
                "Fich");
        T.genrerTransformation();
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
            final Document document = builder.newDocument();


            //Transformateur_FichTxt TF = new Transformateur_FichTxt();
            //TF.lireFichier("C:\\Users\\Geekzone\\Desktop\\Projet_Documents_Structures\\examen\\examen_bis\\poeme\\fiches\\fiches.txt");
            //TF.ajouterContenuDocument(document);
            //Transformateur_Renault TR = new Transformateur_Renault();
            //TR.createElement(document);

            Transformateur T = Transformateur.transformateurBuilder(source, cible, nom);
            T.insererDansCible(document, document.createElement("R"));


            final TransformerFactory transformerFactory = TransformerFactory.newInstance();
            final Transformer transformer = transformerFactory.newTransformer();
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