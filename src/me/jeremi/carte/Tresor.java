package me.jeremi.carte;

/**
 * Représente une case trésor présent sur la carte.
 * @author Jeremi Friggit(@KorabFusian)
 */
class Tresor {
    private int tresor;
    private final int x;
    private final int y;

    /**
     * Constructeur de trésor
     * @param tresor le nombre de trésors sur la case (toujours positif)
     * @param x la coordonnée horizontale du trésor
     * @param y la coordonnée verticale du trésor
     */
    public Tresor(int x, int y, int tresor) {
        this.tresor = Math.max(tresor, 0);
        this.x = x;
        this.y = y;
    }

    /**
     * Decrémente le nombre de trésors de cette case de 1.
     */
    public void decrement() {
            setTresor(getTresor() - 1);
    }

    @Override
    public String toString() {
        return "T - " + getX() + " - " + getY() + " - " + getTresor();
    }

    //region Accessors

    public int getTresor() {
        return tresor;
    }

    /**
     * Set le nombre de trésors, toujours positif (0 si négatif)
     * @param tresor le nouveau nombre de trésors
     */
    public void setTresor(int tresor) {
        this.tresor = Math.max(tresor, 0);
    }

    public int getY() {
        return y;
    }


    public int getX() {
        return x;
    }

    //endregion
}
