package me.jeremi.carte;

/**
 * Représente une case montagne présente sur la carte.
 * @author Jeremi Friggit(@KorabFusian)
 */
public class Montagne {
    /*
     * Cette classe est la moins complexe des classes présentes sur la carte.
     * Elle existe surtout par cohérence avec le reste de la carte, et pour générer la string
     * correspondant à une montagne.
     */
    private final int x;
    private final int y;

    /**
     * Constructeur de montagne
     * @param x la coordonnée horizontale de la montagne
     * @param y la coordonnée verticale de la montagne
     */
    public Montagne(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "M - " + getX() + " - " + getY();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
