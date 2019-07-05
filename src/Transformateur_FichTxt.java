import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Fiche {
    HashMap<LinkedList<String>, String> header = new HashMap<>();
    HashMap<LinkedList<String>, String> ar_part = new HashMap<>();
    HashMap<LinkedList<String>, String> fr_part = new HashMap<>();

    @Override
    public String toString() {
        StringBuilder ch = new StringBuilder();
        ch.append("HEADER : \n");
        for (LinkedList<String> key : header.keySet()) {
            ch.append(key).append(" : ").append(header.get(key)).append("\n");
        }
        ch.append("AR : \n");
        for (LinkedList<String> key : ar_part.keySet()) {
            ch.append(key).append(" : ").append(ar_part.get(key)).append("\n");
        }
        ch.append("FR : \n");
        for (LinkedList<String> key : fr_part.keySet()) {
            ch.append(key).append(" : ").append(fr_part.get(key)).append("\n");
        }
        return ch.toString();
    }
}

public class Transformateur_FichTxt {
    private File fichier;
    private ArrayList<Fiche> contenuFichier = new ArrayList<>();
    private List<String> tags = Arrays.asList("BE", "TY", "DO", "SD", "AU", "VE", "DF", "PH", "RF");

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

    public static void main(String[] args) throws FileNotFoundException {
        Transformateur_FichTxt TF = new Transformateur_FichTxt();
        TF.lireFichier("C:\\Users\\Geekzone\\Desktop\\Projet_Documents_Structures\\examen\\examen_bis\\poeme\\fiches\\fiches.txt");
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

    public Element ajouterContenuDocument(Document document) {
        final Element racine = document.createElement("FICHES");
        document.appendChild(racine);
        int cpt = 0;
        for (Fiche F : contenuFichier) {
            cpt++;
            Element fiche_element = document.createElement("FICHE");
            fiche_element.setAttribute("id", cpt + "");
            for (LinkedList<String> clefs : F.header.keySet()) {
                Element balise = document.createElement(clefs.getLast());
                balise.appendChild(document.createTextNode(formaterListe(clefs) + " " + F.header.get(clefs)));
                fiche_element.appendChild(balise);

            }
            Element langue_element_ar = document.createElement("Langue");
            langue_element_ar.setAttribute("id", "AR");

            Element langue_element_fr = document.createElement("Langue");
            langue_element_fr.setAttribute("id", "FR");

            fiche_element.appendChild(langue_element_ar);
            fiche_element.appendChild(langue_element_fr);

            for (LinkedList<String> clefs : F.ar_part.keySet()) {
                if (clefs.size() == 0)
                    continue;
                Element b = document.createElement(clefs.getLast());
                b.appendChild(document.createTextNode(formaterListe(clefs) + " " + F.ar_part.get(clefs)));
                langue_element_ar.appendChild(b);
            }

            fiche_element.appendChild(langue_element_fr);
            for (LinkedList<String> clefs : F.fr_part.keySet()) {
                if (clefs.size() == 0)
                    continue;
                Element b = document.createElement(clefs.getLast());
                b.appendChild(document.createTextNode(formaterListe(clefs) + " " + F.fr_part.get(clefs)));
                langue_element_fr.appendChild(b);
            }
            racine.appendChild(fiche_element);
        }

        return racine;
    }

    public Element ajouterContenuDocument1(Document document) {
        final Element racine = document.createElement("FICHES");
        document.appendChild(racine);
        int cpt = 0;
        for (Fiche F : contenuFichier) {
            cpt++;
            Element fiche_element = document.createElement("FICHE");
            LinkedList<Element> ajouts_ulterieurs = new LinkedList<>();
            fiche_element.setAttribute("id", cpt + "");
            for (LinkedList<String> clefs : F.header.keySet()) {
                Element balise = document.createElement(clefs.getLast());
                balise.appendChild(document.createTextNode(formaterListe(clefs) + " " + F.header.get(clefs)));
                if (!clefs.contains("DO") && !clefs.contains("SD")) {

                    fiche_element.appendChild(balise);
                } else {
                    ajouts_ulterieurs.add(balise);
                }
            }
            Element langue_element_ar = document.createElement("Langue");
            langue_element_ar.setAttribute("id", "AR");

            Element langue_element_fr = document.createElement("Langue");
            langue_element_fr.setAttribute("id", "FR");

            fiche_element.appendChild(langue_element_ar);
            fiche_element.appendChild(langue_element_fr);

            for (Element b : ajouts_ulterieurs) {
                langue_element_ar.appendChild(b);
                langue_element_fr.appendChild(b);
            }

            for (LinkedList<String> clefs : F.ar_part.keySet()) {
                if (clefs.size() == 0)
                    continue;
                Element b = document.createElement(clefs.getLast());
                b.appendChild(document.createTextNode(formaterListe(clefs) + " " + F.ar_part.get(clefs)));
                langue_element_ar.appendChild(b);
            }

            fiche_element.appendChild(langue_element_fr);
            for (LinkedList<String> clefs : F.fr_part.keySet()) {
                if (clefs.size() == 0)
                    continue;
                Element b = document.createElement(clefs.getLast());
                b.appendChild(document.createTextNode(formaterListe(clefs) + " " + F.fr_part.get(clefs)));
                langue_element_fr.appendChild(b);
            }
            racine.appendChild(fiche_element);
        }

        return racine;
    }

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


}
