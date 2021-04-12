import java.util.*;

public class CarteAuTresor {

    private List<List<String>> carte; // la carte elle-meme
    private List<Aventurier> aventuriers; // liste des aventuriers (pour ne pas se perdre)
    private List<Tresor> tresors; // liste des trésors (pour ne pas se perdre)


    public void print() {
        for (List<String> list : carte) {
            System.out.println(Arrays.toString(list.toArray()));
        }
    }

    public void createMap(String params) {
        aventuriers = new ArrayList<>();
        tresors = new ArrayList<>();
        String[] split = params.split("-");
        int x = Integer.parseInt(split[1]);
        int y = Integer.parseInt(split[2]);
        carte = new ArrayList<>();
        for (int i = 0; i < y; i++) {
            List<String> line = new ArrayList<>(Collections.nCopies(x, "-"));
            carte.add(line);
        }
    }

    public void addMontagne(String params) {
        String[] split = params.split("-");
        int x = Integer.parseInt(split[1]);
        int y = Integer.parseInt(split[2]);
        carte.get(y).set(x, "M");
    }

    public void addTresor(String params) {
        String[] split = params.split("-");
        int x = Integer.parseInt(split[1]);
        int y = Integer.parseInt(split[2]);
        int tresor = Integer.parseInt(split[3]);
        tresors.add(new Tresor(tresor, x, y));
        carte.get(y).set(x, "T");
    }

    public void addAventurier(String params) throws WrongInputException {
        String[] split = params.split("-");
        String nom = split[1];
        int x = Integer.parseInt(split[2]);
        int y = Integer.parseInt(split[3]);

        // gestion de l'orientation
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

    private Tresor getTresorByPosition(int x, int y) {
        for (Tresor tresor : tresors) {
            if (tresor.getX() == x && tresor.getY() == y) return tresor;
        }
        return null;
    }

    private Aventurier getAventurierByPosition(int x, int y) {
        for (Aventurier aventurier : aventuriers) {
            if (aventurier.getX() == x && aventurier.getY() == y) return aventurier;
        }
        return null;
    }

}
