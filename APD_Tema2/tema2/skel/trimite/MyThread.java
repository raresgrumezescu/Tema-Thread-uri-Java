import java.lang.*;
import java.io.*;
import java.util.*;

public class MyThread extends Thread {
    public int id;

    public MyThread(int id) {
        this.id = id;
    }


    public void run() {
            try {
                for (int i = id; i < Tema2.taskuri.size(); i += Tema2.nr_workeri) {
                    Task t;
                    t = Tema2.taskuri.get(i);
                    int offset = t.offset;
                    int dimensiune = t.dimensiune;
                    RandomAccessFile file = new RandomAccessFile(t.nume_fisier, "r");
                    file.seek(offset);

                    String seq = "";
                    seq += (char) file.read();

                    if (offset != 0 && Tema2.for_check.contains(seq) == false) {
                        file.seek(offset - 1);
                        seq = "";
                        seq += (char) file.read();
                        if (Tema2.for_check.contains(seq) == false) {

                            while (true) {
                                seq = "";
                                int ch = file.read();
                                seq += (char) ch;
                                if (ch < 0)
                                    break;
                                if (Tema2.for_check.contains(seq) != false) {
                                    break;
                                }
                                offset++;
                                dimensiune--;
                            }

                            t.offset = offset;
                            t.dimensiune = dimensiune;
                        }
                    }

                    offset = t.offset;
                    dimensiune = t.dimensiune;
                    file.seek(offset + dimensiune - 1);

                    seq = "";
                    seq += (char) file.read();
                    if (dimensiune != 0 && Tema2.for_check.contains(seq) == false) {
                        file.seek(offset + dimensiune - 1);
                        seq = "";
                        seq += (char) file.read();
                        if (Tema2.for_check.contains(seq) == false) {
                            while (true) {
                                seq = "";
                                int ch = file.read();
                                    seq += (char) ch;
                                if (ch < 0)
                                    break;
                                if (Tema2.for_check.contains(seq) != false) {
                                    break;
                                }
                                dimensiune++;
                            }

                            t.dimensiune = dimensiune;
                        }
                    }

                    if (t.dimensiune == 0) {
                        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
                        Vector<String> maxWords = new Vector<String>();
                        map.clear();
                        maxWords.clear();
                        Rezultat rez = new Rezultat(t.nume_fisier, map, maxWords);
                        Tema2.rezultate[t.id_task] = rez;

                    } else {
                        String content = "";
                        file.seek(t.offset);

                        for (int j = t.offset; j < t.offset + t.dimensiune; j++) {
                            content += (char) file.read();
                        }

                        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
                        Vector<String> maxWords = new Vector<String>();
                        int maxLength = -20000;
                        StringTokenizer token = new StringTokenizer(content, Tema2.for_check);
                        while (token.hasMoreTokens()) {
                            String cuvant = token.nextToken();
                            int l = cuvant.length();
                            map.putIfAbsent(l, 0);
                            int increment = map.get(l);
                            increment++;
                            map.replace(l, increment);

                            if (cuvant.length() == maxLength)
                                maxWords.add(cuvant);
                            else if (cuvant.length() > maxLength) {
                                maxLength = cuvant.length();
                                maxWords.clear();
                                maxWords.add(cuvant);
                            }
                        }
                        Rezultat rez = new Rezultat(t.nume_fisier, map, maxWords);
                        Tema2.rezultate[t.id_task] = rez;
                    }

                }
            } catch (IOException ex) {
                System.out.println("Something went Wrong");
                ex.printStackTrace();
            }
    }

}