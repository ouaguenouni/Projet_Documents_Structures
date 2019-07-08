import java.util.HashMap;
import java.util.LinkedList;

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
