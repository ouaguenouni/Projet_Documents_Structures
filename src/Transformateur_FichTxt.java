import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    public void lireFichier(String emplacement) throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(emplacement));
        Path source = Paths.get(emplacement);
        InputStream input = null;
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
