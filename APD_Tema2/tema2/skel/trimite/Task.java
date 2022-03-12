public class Task {
    int id_task;
    int id_fisier;
    String nume_fisier;
    int offset;
    int dimensiune;

    public Task (String nume_fisier, int offset, int dimensiune, int id_task, int id_fisier) {
        this.nume_fisier = nume_fisier;
        this.offset = offset;
        this.dimensiune = dimensiune;
        this.id_task = id_task;
        this.id_fisier = id_fisier;
    }

    public String toString() {
        String str = "";
        str += this.nume_fisier;
        str += " ";
        str += this.offset;
        str += " ";
        str += this.dimensiune;
        str += " ";
        str += this.id_task;
        str += "\n";
        return str;
    }
}