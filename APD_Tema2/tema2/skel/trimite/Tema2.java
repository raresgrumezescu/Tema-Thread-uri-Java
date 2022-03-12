import java.io.*;
import java.util.*;


public class Tema2 {

    public static String for_check = ";:/?˜\\.,><‘[]{}()!@#$%ˆ&-   +’=*| \t\n\r'";
    public static Rezultat[] rezultate;
    public static BufferedWriter buffer;
    public static String outFile;
    public static int nr_workeri;
    public static Vector<Task> taskuri;
    public static Vector<TaskAgain> taskuriagain;
    public static Vector<Outputuri> arr_out;

    public static void main(String[] args) {
        if (args.length < 3) {
            System.err.println("Usage: Tema2 <workers> <in_file> <out_file>");
            return;
        }
        BufferedReader reader;
        Vector<String> fisiere = new Vector<String>();
        nr_workeri = Integer.parseInt(args[0]);
        taskuri = new Vector<Task>();
        outFile = args[2];

        try {
            reader = new BufferedReader(new FileReader(args[1]));
            buffer = new BufferedWriter(new FileWriter(Tema2.outFile));
            String line = reader.readLine();

            while (line != null) {
                fisiere.add(line);
                line = reader.readLine();
            }
            reader.close();

            int dimensiune_fragment = 0, nr_documente = 0, id = 0;
            int id_file = 0;

            for (int i = 0; i < fisiere.size(); i++) {
                if (i == 0) {
                    dimensiune_fragment = Integer.parseInt(fisiere.get(i));
                    continue;
                }
                if (i == 1) {
                    nr_documente = Integer.parseInt(fisiere.get(i));
                    continue;
                }

                String fname = fisiere.get(i);
                FileReader fr = new FileReader(fname);

                int j, count = 0, offset = 0;
                while ((j = fr.read()) != -1) {
                    count++;
                }

                while (count >= 0) {
                    count -= dimensiune_fragment;
                    if (count >= 0)
                        taskuri.add(new Task(fname, offset, dimensiune_fragment, id++, id_file));
                    else
                        taskuri.add(new Task(fname, offset, count + dimensiune_fragment, id++, id_file));
                    offset += dimensiune_fragment;
                }

                id_file++;
            }

            Thread[] t = new Thread[nr_workeri];

            rezultate = new Rezultat[taskuri.size()];

            for (int i = 0; i < nr_workeri; ++i) {
                t[i] = new MyThread(i);
                t[i].start();
            }

            for (int i = 0; i < nr_workeri; ++i) {
                try {
                    t[i].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            int from = 0;
            taskuriagain = new Vector<TaskAgain>();

            for (int i = 0; i < nr_documente; ++i) {
                Vector<Rezultat> tasks_wi = new Vector<Rezultat>();

                String actual_file = rezultate[from].nume_fisier;

                for (int j = from; j < taskuri.size(); j++) {
                    if (rezultate[j].nume_fisier.equals(actual_file) == false) {
                        from = j;
                        actual_file = rezultate[j].nume_fisier;
                        break;
                    }
                    tasks_wi.add(rezultate[j]);
                }

                Vector<HashMap<Integer, Integer>> dictionare = new Vector<HashMap<Integer, Integer>>();
                Vector<Vector<String>> maxWords = new Vector<Vector<String>>();

                for (int j = 0; j < tasks_wi.size(); j++) {
                    dictionare.add(tasks_wi.get(j).dictionar);
                    maxWords.add(tasks_wi.get(j).maxWords);
                }

                TaskAgain taskagain = new TaskAgain(tasks_wi.get(0).nume_fisier, dictionare, maxWords);
                taskuriagain.add(taskagain);

            }

            arr_out = new Vector<Outputuri>();

            for (int i = 0; i < nr_workeri; i++) {
                t[i] = new MyThread2(i);
                t[i].start();
            }

            for (int i = 0; i < nr_workeri; ++i) {
                try {
                    t[i].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Collections.sort(arr_out, new SortByRank());

            for (int i = 0; i < arr_out.size(); i++) {
                buffer.write(arr_out.get(i).output);
            }



            buffer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
