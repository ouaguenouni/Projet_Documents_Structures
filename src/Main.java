import jdk.jfr.TransitionFrom;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.HashMap;

public class Main {

    public static boolean Filtrer (Node noeud){
        return (noeud.getNodeName() != "#text");
    }

    static void poeme() throws Exception
    {
        int i=1;
        DocumentBuilder parseur = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        DOMImplementation domimp = parseur.getDOMImplementation();
        DocumentType dtd = domimp.createDocumentType("poema",null,"neruda.dtd");
        Document doc = domimp.createDocument(null,"poema",dtd);
        doc.setXmlStandalone(true);
        Element rac = doc.getDocumentElement();

        try{
            InputStream flux=new FileInputStream("");
            InputStreamReader lecture=new InputStreamReader(flux);
            BufferedReader buff=new BufferedReader(lecture);
            String ligne;
            while ((ligne=buff.readLine())!=null){

                if(i==1)
                {
                    Element verso_ele = (Element) doc.createElement("titulo");
                    rac.appendChild(verso_ele);
                    verso_ele.appendChild((doc.createTextNode(ligne)));
                    i++;
                }
                else
                {
                    if( ligne.length() > 0)
                    {
                        Element estrofa_ele =  doc.createElement("estrofa");
                        rac.appendChild(estrofa_ele);
                        while( ligne !=null && ligne.length() > 0) //verso
                        {
                            Element verso_ele = doc.createElement("verso");
                            rac.appendChild(verso_ele);
                            verso_ele.appendChild((doc.createTextNode(ligne)));
                            estrofa_ele.appendChild(verso_ele);
                            ligne=buff.readLine();
                        }
                    }
                }
            }
            buff.close();
        }
        catch (Exception e){
            System.out.println(e.toString());
        }

        DOMSource ds = new DOMSource(doc);
        StreamResult res = new StreamResult(new File("neruda.xml"));
        Transformer tr = TransformerFactory.newInstance().newTransformer();tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");//spÃ©cification de l'encodage de sortie
        tr.setOutputProperty(OutputKeys.INDENT, "yes");
        tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "neruda.dtd");
        tr.transform(ds, res);
    }

    //******************************************************************************************************
    public static void Afficher(Node noeud, Document doc, Element rac) throws Exception
    {
        Element el = (Element) noeud;
        if(el.getChildNodes().getLength() >0)
        {
            for (int cpt =0; cpt<el.getChildNodes().getLength(); cpt++)
            {
                Node a =el.getChildNodes().item(cpt);
                if(Filtrer(a))
                {
                    Afficher(a, doc , rac);
                }
                else
                {
                    if(a.getParentNode().getNodeName() == "p" && a.getNodeValue().trim().length()>0)
                    {
                        Element verso_ele = (Element) doc.createElement("texte");
                        rac.appendChild(verso_ele);
                        verso_ele.appendChild((doc.createTextNode(a.getNodeValue().trim())));
                    }
                }
            }
        }

    }

    //***********************************************************************
    static void m674() throws Exception {
        String xmlFile = "";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder parseur = factory.newDocumentBuilder();
        Document document = parseur.parse(xmlFile);
        Element liste = document.getDocumentElement();
        DOMImplementation domimp = parseur.getDOMImplementation();
        DocumentType dtd = domimp.createDocumentType("TEI_S",null,"dom.dtd");
        Document doc = domimp.createDocument(null,"TEI_S",dtd);
        doc.setXmlStandalone(true);
        Element rac = doc.getDocumentElement();

        Element M = (Element) doc.createElement("M674.xml");
        rac.appendChild(M);
        Afficher(liste,  doc, M);

        DOMSource ds = new DOMSource(doc);
        StreamResult res = new StreamResult(new File("sortie1.xml"));
        Transformer tr = TransformerFactory.newInstance().newTransformer();tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");//spÃ©cification de l'encodage de sortie
        tr.setOutputProperty(OutputKeys.INDENT, "yes");
        tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "dom.dtd");
        tr.transform(ds, res);
    }

    static void Parcour(File repertoire)
    {

        String liste[] = repertoire.list();

        if (liste != null) {
            for (int i = 0; i < liste.length; i++) {

                String s = repertoire+"\\"+liste[i];
                File fils = new File(s);
                if(fils.isDirectory())
                {
                    Parcour(fils);
                }
                else
                    System.out.println(liste[i]);
            }
        } else {
            System.err.println("Nom de repertoire invalide");
        }
    }

    //Ici on va essayer de remplir le dictionnaires (Source -> Cible)
    public HashMap <String, String> source_cible = new HashMap<>();

    public void pretraitementFichier(File source){
        //Ici il reste a construire pour chaque source sa destination
        //Donc la source ça serra source.getAbsolutePath ensuite faut manipuler le chemin de la source pour générer le chemin de la cible
        switch (source.getName())
        {
            case "fiches.txt":
                //Ici il faut construire deux lignes dans chaque dictionnaire fiches.txt1 fiches.txt2
                break;
            case "M674.txt":
                break;
            case "boitedialog.txt":
                break;
            case "renault.txt":
                break;
            case "M457.txt":
                break;
            case "poeme.txt":
                break;
        }

    }


    //Ensuite on applique les fonctions
    public void genererCible(File source) throws FileNotFoundException {
        String cible = source_cible.get(source.getAbsolutePath());
        Transformable T = Transformateur.transformateurBuilder(source.getAbsolutePath(),cible,source.getName());
    }

    public static void main(String[] args) throws Exception {
        Parcour(new File("C:\\Users\\mohamed\\Desktop\\PFE\\Projet_Documents_Structures\\examen"));
        //poeme();
        //m674();

    }

    }


