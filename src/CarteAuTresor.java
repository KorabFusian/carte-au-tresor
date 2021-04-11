import java.util.*;

public class CarteAuTresor {

    private List<List<String>> carte; // la carte elle-meme
    private List<Aventurier> aventuriers; // liste des aventuriers (pour ne pas se perdre)

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
        aventuriers = new ArrayList<>();
        String[] split = params.split("-");
        int x = Integer.parseInt(split[1]);
        int y = Integer.parseInt(split[2]);
        carte = new ArrayList<>();
        for (int i = 0; i < y; i++) {
            List<String> line = new ArrayList<>(Collections.nCopies(x, "-"));
            carte.add(line);
        }
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
        carte.get(y).set(x, "T");
    }

    public void addAventurier (String params) throws WrongInputException {
        String[] split = params.split("-");
        String nom = split[1];
        int x = Integer.parseInt(split[2]);
        int y = Integer.parseInt(split[3]);

        //gestion de l'orientation
        Direction orientation;
        switch (split[4]) {
            case "N" -> orientation = Direction.NORD;
            case "E" -> orientation = Direction.EST;
            case "S" -> orientation = Direction.SUD;
            case "O" -> orientation = Direction.OUEST;
            default -> throw new WrongInputException("Mauvaise orientation d'aventurier");
        }
        String chemin = split[5];
        carte.get(y).set(x, "A");
        aventuriers.add(new Aventurier(nom, x, y, orientation, chemin));
    }

}
