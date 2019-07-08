import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Transformateur_FichTxt extends Transformateur {

    protected ArrayList<Fiche> contenuFichier = new ArrayList<>();

    protected Transformateur_FichTxt(String source, String cible, String nom_fichier) {
        super(source, cible, nom_fichier);
    }

    public static LinkedList<String> chercherTag(String str) {
        Pattern pattern = Pattern.compile("(\\s)+([A-Z][A-Z])( :|$)");
        Matcher matcher = pattern.matcher(str);
        // check all occurance
        LinkedList<String> AL = new LinkedList<>();

        while (matcher.find()) {
            System.out.print("Start index: " + matcher.start());
            System.out.print(" End index: " + matcher.end() + " ");
            AL.add(matcher.group(2));
        }
        return AL;
    }

    public static String retirerTags(String str) {
        Pattern pattern = Pattern.compile("\\s([A-Z][A-Z])( :|$)");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            str = str.replace(matcher.group(0), "");
        }
        return str;
    }


    public static String formaterListe(LinkedList<String> liste) {
        StringBuilder resultat = new StringBuilder();
        if (liste.size() == 1)
            return liste.get(0) + " : ";
        for (int i = liste.size() - 1; i >= 0; i--) {
            resultat.append(liste.get(i));
            if (i == liste.size() - 1)
                resultat.append(" | ");
            else
                resultat.append(" : ");
        }
        return resultat.toString();
    }

    public abstract Element ajouterContenuDocument(Document document);



    public void lireFichier(String emplacement) throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(emplacement));

        try {
            String strCurrentLine;
            Fiche fich_courante = null;
            String tag_partie = "";
            while ((strCurrentLine = reader.readLine()) != null) {
                if (!strCurrentLine.startsWith(" ")) {
                    System.out.println("====BEGIN====");
                    if (strCurrentLine.startsWith("AR") || strCurrentLine.startsWith("FR") || strCurrentLine.startsWith("PNR")) {
                        tag_partie = strCurrentLine;
                        if (strCurrentLine.startsWith("PNR")) {
                            tag_partie = "PNR";
                            fich_courante = new Fiche();
                            contenuFichier.add(fich_courante);
                        }
                    }
                    switch (tag_partie) {
                        case "PNR":
                            System.out.println("Ajout de " + retirerTags(strCurrentLine) + " en face de la clé " + chercherTag(strCurrentLine));
                            fich_courante.header.put(chercherTag(strCurrentLine), retirerTags(strCurrentLine));
                            break;
                        case "AR":
                            System.out.println("Ajout de " + retirerTags(strCurrentLine) + " en face de la clé " + chercherTag(strCurrentLine));
                            fich_courante.ar_part.put(chercherTag(strCurrentLine), retirerTags(strCurrentLine));
                            break;
                        case "FR":
                            System.out.println("Ajout de " + retirerTags(strCurrentLine) + " en face de la clé " + chercherTag(strCurrentLine));
                            fich_courante.fr_part.put(chercherTag(strCurrentLine), retirerTags(strCurrentLine));
                            break;
                    }
                    System.out.println("Recherche dans la ligne : " + strCurrentLine);

                }
            }
            System.out.println("====Affichage résultats====");
            for (Fiche F : contenuFichier) {
                System.out.println("FICH / ");
                System.out.println(F);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void insererDansCible(Document D, Element E) {
        E = ajouterContenuDocument(D);
    }
}
