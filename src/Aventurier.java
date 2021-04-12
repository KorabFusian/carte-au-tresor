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
        cheminRestant =  getCheminRestant().substring(1);
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

    public void tournerAGauche() {
        switch (orientation) {
            case NORD -> orientation = Direction.OUEST;
            case SUD -> orientation = Direction.EST;
            case EST -> orientation = Direction.NORD;
            case OUEST -> orientation = Direction.SUD;
            default -> throw new IllegalStateException("Unexpected value: " + orientation);
        }
        popCheminRestant();
    }

    public void tournerADroite() {
        switch (orientation) {
            case NORD -> orientation = Direction.EST;
            case SUD -> orientation = Direction.OUEST;
            case EST -> orientation = Direction.SUD;
            case OUEST -> orientation = Direction.NORD;
            default -> throw new IllegalStateException("Unexpected value: " + orientation);
        }
        popCheminRestant();
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
        popCheminRestant();
    }
}
