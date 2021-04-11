import java.util.*;

public class CarteAuTresor {
    private List<List<String>> carte; // la carte elle-meme

    public void create(int x, int y) {
        carte = new ArrayList<>(y);
        for (int i = 0; i < y; i++) {
            List<String> line = new ArrayList<>(Collections.nCopies(x, "-"));
            carte.add(line);
        }
    }

    public void add(String square, int x, int y) {
       carte.get(y).set(x, square);
    }

    public void print()
    {
        for (List<String> list : carte) {
            System.out.println(Arrays.toString(list.toArray()));
        }
    }

}
