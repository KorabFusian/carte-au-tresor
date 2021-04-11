import java.util.*;

public class CarteAuTresor {

    private List<List<String>> carte; // la carte elle-meme
    private List<Aventurier> aventuriers; // liste des aventuriers (pour ne pas se perdre)

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
    public void createMap (String params) {
        String[] split = params.split("-");
        int x = Integer.parseInt(split[1]);
        int y = Integer.parseInt(split[2]);
        create(x,y);
    }

    public void addMountain (String params){
        String[] split = params.split("-");
        int x = Integer.parseInt(split[1]);
        int y = Integer.parseInt(split[2]);
        carte.get(y).set(x, "M");
    }

    public void addTreasure (String params) {
        String[] split = params.split("-");
        int x = Integer.parseInt(split[1]);
        int y = Integer.parseInt(split[2]);
        carte.get(y).set(x, "M");
    }

    public void addAventurier (String params) {
        String[] split = params.split("-");
    }

}
