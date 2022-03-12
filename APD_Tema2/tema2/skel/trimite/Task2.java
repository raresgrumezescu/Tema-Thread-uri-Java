import java.util.*;
import java.io.*;
import java.lang.*;

public class Task2 {
    String nume_fisier;
    Map<Integer, Integer> dictionar = new HashMap<Integer, Integer>();
    Vector<String> maxWords = new Vector<String>();

    public Task2 (String nume_fisier, Map<Integer, Integer> dictionar, Vector<String> maxWords) {
        this.nume_fisier = nume_fisier;
        this.dictionar = dictionar;
        this.maxWords = maxWords;
    }

    public String toString() {
        String str = "";
        str += nume_fisier;
        str += " dictionar: {";
        for(Map.Entry m : dictionar.entrySet()){
            str += m.getKey();
            str += " : ";
            str += m.getValue();
            str += ";";
        }
        str += "} maxWords: (";
        for (String s : maxWords) {
            str += s;
            str += " ; ";
        }
        str += ")  ";
        str += "\n";

        return str;
    }

}