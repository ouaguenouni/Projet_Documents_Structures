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
        File Sortie = new File("fichiers-de-sortie");
        //Donc la source ça serra source.getAbsolutePath ensuite faut manipuler le chemin de la source pour générer le chemin de la cible
        switch (source.getName())
        {
            case "fiches.txt":
                //Ici il faut construire deux lignes dans chaque dictionnaire fiches.txt1 fiches.txt2
                source_cible.put(source.getAbsolutePath()+"fiches.txt1","fiches1.xml");
                source_cible.put("fiches.txt2","fiches2.xml");
                break;
            case "M674.txt":
                source_cible.put("M674.xml","sortie1.xml");
                break;
            case "boitedialog.txt":
                source_cible.put("boitedialog.fxml","javafx.xml");
                break;
            case "renault.txt":
                source_cible.put("renault.html","renault.xml");
                break;
            case "M457.txt":
                source_cible.put("M457.xml","sortie2.xml");
                break;
            case "poeme.txt":
                source_cible.put("poeme.txt","neruda.xml");
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
        Transformable TM = new Transformateur_M457();
        TM.transform("C:\\Users\\mohamed\\Desktop\\PFE\\Projet_Documents_Structures\\examen\\M457.xml",
                "C:\\Users\\mohamed\\Desktop\\PFE\\Projet_Documents_Structures\\Rez.xml",
                "M457.xml");
    }

    }


