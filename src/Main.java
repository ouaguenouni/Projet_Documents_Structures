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
                    pretraitementFichier(s);
            }
        } else {
            System.err.println("Nom de repertoire invalide");
        }
    }

    //Ici on va essayer de remplir le dictionnaires (Source -> Cible)
    public static HashMap <String, String> source_cible = new HashMap<>();





    public static void pretraitementFichier(String source){
        //Ici il reste a construire pour chaque source sa destination
        File Sortie = new File(source);
        String[] parts = source.split("\\\\");
        String nom = parts[parts.length-1];
        System.out.println(nom);
        //Donc la source ça serra source.getAbsolutePath ensuite faut manipuler le chemin de la source pour générer le chemin de la cible
        switch (nom)
        {
            case "fiches.txt":
                //Ici il faut construire deux lignes dans chaque dictionnaire fiches.txt1 fiches.txt2
                source_cible.put(source,"fiches1.xml");
                source_cible.put(source+"?","fiches2.xml");
                break;
            case "M674.xml":
                source_cible.put(source,"sortie1.xml");
                break;
            case "boitedialog.fxml":
                source_cible.put(source,"javafx.xml");
                break;
            case "renault.txt":
                source_cible.put(source,"renault.xml");
                break;
            case "M457.xml":
                source_cible.put(source,"sortie2.xml");
                break;
            case "poeme.txt":
                source_cible.put(source,"neruda.xml");
                break;
        }

    }


    //Ensuite on applique les fonctions
    public void genererCible(File source) throws FileNotFoundException {
        String cible = source_cible.get(source.getAbsolutePath());
        Transformable T = Transformateur.transformateurBuilder(source.getAbsolutePath(),cible,source.getName());
    }

    public static void main(String[] args) throws Exception {
       // Main.Parcour(new File("C:\\Users\\mohamed\\Desktop\\PFE\\Projet_Documents_Structures\\examen"));
        Main.Parcour(new File("C:\\GITHUB\\Projet_Documents_Structures\\examen"));
        for (String s:source_cible.keySet()){
            String[] parts = s.replace("?","").split("\\\\");
            String nom = parts[parts.length-1];
            Transformable T = Fonction_Builder.transformableBuilder(s.replace("?",""),source_cible.get(s),nom);
            T.transform( s.replace("?",""),source_cible.get(s),nom);
            System.out.println("Fin de transformation de : " + s.replace("?","")+" : "+source_cible.get(s));
        }
    }

    }


