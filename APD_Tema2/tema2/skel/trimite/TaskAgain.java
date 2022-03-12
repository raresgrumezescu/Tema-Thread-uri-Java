import java.util.*;
import java.io.*;
import java.lang.*;

public class TaskAgain{
    String nume_fisier;
    Vector<HashMap<Integer, Integer>> dictionare = new Vector<HashMap<Integer, Integer>>();
    Vector<Vector<String>> maxWords = new Vector<Vector<String>>();

    public TaskAgain (String nume_fisier, Vector<HashMap<Integer, Integer>> dictionare, Vector<Vector<String>> maxWords) {
        this.nume_fisier = nume_fisier;
        this.dictionare = dictionare;
        this.maxWords = maxWords;
    }
}