/**
 *
 */
public class Aventurier {

    private final String nom;
    private int x;
    private int y;
    private Direction orientation;
    private final String chemin; // le chemin original
    private String cheminRestant; // le chemin qu'il reste à faire
    private int tresor;

    public Aventurier(String nom, int x, int y, Direction orientation, String chemin) {
        this.nom = nom;
        this.x = x;
        this.y = y;
        this.orientation = orientation;
        this.tresor = 0;
        this.chemin = chemin;
        this.cheminRestant = chemin;
    }

    public String popCheminRestant() {
        cheminRestant = getCheminRestant().substring(1);
        return cheminRestant;
    }

    public String getNom() {
        return nom;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public Direction getOrientation() {
        return orientation;
    }

    public void setOrientation(Direction orientation) {
        this.orientation = orientation;
    }

    public String getChemin() {
        return chemin;
    }

    public int getTresor() {
        return tresor;
    }

    public void incrementTresor() {
        this.tresor += 1;
    }

    public String getCheminRestant() {
        return cheminRestant;
    }
}
