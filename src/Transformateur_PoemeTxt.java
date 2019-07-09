import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class Transformateur_PoemeTxt {

    public static void main(String[] str)
    {  }

    static void Transformateur_PoemeTxt(String source, String cible, String nom) throws Exception
    {
        int i=1;
        DocumentBuilder parseur = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        DOMImplementation domimp = parseur.getDOMImplementation();
        DocumentType dtd = domimp.createDocumentType("poema",null,"neruda.dtd");
        Document doc = domimp.createDocument(null,"poema",dtd);
        doc.setXmlStandalone(true);
        Element rac = doc.getDocumentElement();

        try{
            InputStream flux=new FileInputStream(source);
            InputStreamReader lecture=new InputStreamReader(flux);
            BufferedReader buff=new BufferedReader(lecture);
            String ligne;
            while ((ligne=buff.readLine())!=null){

                if(i==1)//traitement du titre
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
        StreamResult res = new StreamResult(new File(cible+nom));
        Transformer tr = TransformerFactory.newInstance().newTransformer();tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        tr.setOutputProperty(OutputKeys.INDENT, "yes");
        tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "neruda.dtd");
        tr.transform(ds, res);
    }



}