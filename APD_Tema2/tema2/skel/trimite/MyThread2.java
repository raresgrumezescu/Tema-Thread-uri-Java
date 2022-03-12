import java.lang.*;
import java.io.*;
import java.util.*;

public class MyThread2 extends Thread {
    public int id;

    public MyThread2(int id) {
        this.id = id;
    }

    public int fibo(int value) {
        if (value == 0)
            return 1;
        if (value == 1)
            return 1;

        return fibo(value - 1) + fibo(value - 2);
    }

    public void run() {
        TaskAgain tt;
        for (int i = id; i < Tema2.taskuriagain.size(); i += Tema2.nr_workeri) {
            tt = Tema2.taskuriagain.get(i);

            Map<Integer, Integer> map_rez = new HashMap<Integer, Integer>();
            Vector<String> maxWords_rez = new Vector<String>();
            int maxLength = -20000;

            for (HashMap<Integer, Integer> dict : tt.dictionare) {
                for (Map.Entry<Integer, Integer> m : dict.entrySet()) {
                    int key = m.getKey();
                    //int value = m.getValue();
                    map_rez.putIfAbsent(key, 0);
                    int value = map_rez.get(key);
                    value += m.getValue();
                    //System.out.println(m.get(key));
                    map_rez.replace(key, value);
                }
            }
                for (Vector<String> words : tt.maxWords) {
                    if (words.size() > 0) {
                        if (words.firstElement().length() > maxLength) {
                            maxLength = words.firstElement().length();
                            maxWords_rez.clear();
                            maxWords_rez = new Vector<String>(words);
                        } else if (words.firstElement().length() == maxLength) {
                            maxWords_rez.addAll(words);
                        }
                    }
                }


            Task2 task = new Task2(tt.nume_fisier, map_rez, maxWords_rez);

            int S = 0;
            int nr_cuv_total = 0;
            int lungime_maxima = -20000;
            int nr_aparitii_lungime_maxima = 0;
            for (Map.Entry<Integer, Integer> m : task.dictionar.entrySet()) {
                int key = m.getKey(), value = m.getValue();
                S += fibo(key) * value;
                nr_cuv_total += value;
                if (lungime_maxima < key) {
                    lungime_maxima = key;
                    nr_aparitii_lungime_maxima = value;
                }
            }

            double rang = (double) S / nr_cuv_total;
            StringTokenizer token = new StringTokenizer(task.nume_fisier, "/");
            String numeFile = "";
            while (token.hasMoreTokens()) {
                numeFile = token.nextToken();
            }

            String output = "";
            output = numeFile + "," + String.format("%.2f", rang) + "," + lungime_maxima + "," + nr_aparitii_lungime_maxima + "\n";
            Tema2.arr_out.add(new Outputuri(output, rang));

        }
    }
}