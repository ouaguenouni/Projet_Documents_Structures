import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Transformateur_M674 implements Transformable {

    public static boolean Filtrer (Node noeud){
        return (noeud.getNodeName() != "#text");
    }

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
                        Matcher matcher = Pattern.compile("^\\s+"+"(.+)").matcher(a.getNodeValue());

                        if(matcher.find())
                            verso_ele.appendChild((doc.createTextNode(matcher.group(1))));
                        else
                            verso_ele.appendChild((doc.createTextNode(a.getNodeValue())));
                    }
                }
            }
        }

    }
//******************************************************************************************************

 public void transform (String source, String cible, String nom_fichier) throws Exception{
     DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

     factory.setValidating (false);
     factory.setFeature ("http://xml.org/sax/features/namespaces", false);
     factory.setFeature ("http://xml.org/sax/features/validation", false);
     factory.setFeature ("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
     factory.setFeature ("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
     factory.setSchema(null);

     DocumentBuilder parseur = factory.newDocumentBuilder();
     Document document = parseur.parse(source);
     Element liste = document.getDocumentElement();
     //************************Sortie******************************************
     DOMImplementation domimp = parseur.getDOMImplementation();
     DocumentType dtd = domimp.createDocumentType("TEI_S",null,"dom.dtd");
     Document doc = domimp.createDocument(null,"TEI_S",dtd);
     doc.setXmlStandalone(true);
     Element rac = doc.getDocumentElement();

     Element M = (Element) doc.createElement("M674.xml");
     rac.appendChild(M);
     Afficher(liste,  doc, M);

     DOMSource ds = new DOMSource(doc);
     StreamResult res = new StreamResult(new File(cible));
     Transformer tr = TransformerFactory.newInstance().newTransformer();tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
     tr.setOutputProperty(OutputKeys.INDENT, "yes");
     tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "dom.dtd");
     tr.transform(ds, res);
 }
        }