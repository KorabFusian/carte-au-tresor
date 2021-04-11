/**
 *
 */
public class Aventurier {

    private final String nom;
    private int x;
    private int y;
    private Direction orientation;
    private String chemin;
    private int tresor;

    public Aventurier(String nom, int x, int y, Direction orientation) {
        this.nom = nom;
        this.x = x;
        this.y = y;
        this.orientation = orientation;
        this.tresor = 0;
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
}
