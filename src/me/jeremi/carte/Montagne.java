package me.jeremi.carte;

/**
 * Représente une case montagne présente sur la carte.
 * @author Jeremi Friggit(@KorabFusian)
 */
class Montagne {
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

    public int getY() {
        return y;
    }


    public int getX() {
        return x;
    }


}
