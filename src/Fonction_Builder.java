import java.io.FileNotFoundException;

public class Fonction_Builder {
    //Ici on programme un builder de transformables

    public static Transformable transformableBuilder(String source,String cible, String nom) throws FileNotFoundException {
        Transformable T = null;
        if(Transformateur.noms_fichs_pris.contains(nom))
        {
            return Transformateur.transformateurBuilder(source,cible,nom);
        }
        else
        {
            //Mettre les differents cas de nom et les classes contenant les méthodes Transformer qui leurs sont adapté
            //de la méthode (par exemple case "m457.xml" tu instancie un objet de type 'Classe qui gère m457" et tu le retourne
            // (fait attention a la signature)
            //La méthode : transform(String source, String cible, String nom_fichier) throws FileNotFoundException
            //Faut aussi que ta classe implémente Transformable
            switch(nom){
                case "M674.xml":
                    T = new Transformateur_M674();
                    break;
                case "M457.xml":
                    T = new Transformateur_M457();
                    break;
                case "poeme.txt":
                    T = new Transformateur_PoemeTxt();
                    break;
            }
        }
        return T;
    }
}
