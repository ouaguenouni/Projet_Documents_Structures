import javax.xml.transform.TransformerException;
import java.io.FileNotFoundException;

public interface Transformable {
    void transform(String source, String cible, String nom_fichier) throws Exception;

}
