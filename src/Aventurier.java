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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setCheminRestant(String cheminRestant) {
        this.cheminRestant = cheminRestant;
    }

    public void setTresor(int tresor) {
        this.tresor = tresor;
    }

    public Aventurier(String nom, int x, int y, Direction orientation, String chemin) {
        this.nom = nom;
        this.x = x;
        this.y = y;
        this.orientation = orientation;
        this.tresor = 0;
        this.chemin = chemin;
        this.cheminRestant = chemin;
    }

    public void setOrientation(Direction orientation) {
        this.orientation = orientation;
    }

    public void popCheminRestant() {
        setCheminRestant(getCheminRestant().substring(1));
    }

    public String getNom() {
        return nom;
    }

    public int getY() {
        return y;
    }


    public int getX() {
        return x;
    }


    public Direction getOrientation() {
        return orientation;
    }

    public char getOrientationChar() {
        switch (getOrientation()) {
            case NORD -> {
                return 'N';
            }
            case SUD -> {
                return 'S';
            }
            case EST -> {
                return 'E';
            }
            case OUEST -> {
                return 'O';
            }
            default -> throw new IllegalStateException("Unexpected orientation: " + getOrientation());
        }
    }

    public int getTresor() {
        return tresor;
    }

    public void incrementTresor() {
        setTresor(getTresor() + 1);
    }

    public String getCheminRestant() {
        return cheminRestant;
    }

    public void tournerAGauche() {
        switch (getOrientation()) {
            case NORD -> setOrientation(Direction.OUEST);
            case SUD -> setOrientation(Direction.EST);
            case EST -> setOrientation(Direction.NORD);
            case OUEST -> setOrientation(Direction.SUD);
            default -> throw new IllegalStateException("Unexpected orientation: " + getDirection());
        }
        popCheminRestant();
    }

    private Direction getDirection() {
        return orientation;
    }

    public void tournerADroite() {
        switch (getOrientation()) {
            case NORD -> setOrientation(Direction.EST);
            case SUD -> setOrientation(Direction.OUEST);
            case EST -> setOrientation(Direction.SUD);
            case OUEST -> setOrientation(Direction.NORD);
            default -> throw new IllegalStateException("Unexpected value: " + getOrientation());
        }
        popCheminRestant();
    }

    public void setPos(int x, int y) {
        setX(x);
        setY(y);
        popCheminRestant();
    }

    @Override
    public String toString () {
        // {A comme Aventurier} - {Nom de l’aventurier} - {Axe horizontal} - {Axe vertical} - {Orientation} - {Nb. trésors ramassés}
        return "A - " +
                getNom() + " - " +
                getX() + " - " +
                getY() + " - " +
                getOrientationChar() + " - " +
                getTresor();

    }


}
