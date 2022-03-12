import java.io.*;
import java.lang.*;
import java.util.*;

class SortByRank implements Comparator<Outputuri> {

    @Override
    public int compare(Outputuri a, Outputuri b)
    {
        return Double.compare(b.rank, a.rank);
    }
}
