package me.jeremi.carte;

public class Tresor {
    private int tresor;
    private final int x;
    private final int y;

    public Tresor(int tresor, int x, int y) {
        this.tresor = Math.max(tresor, 0);
        this.x = x;
        this.y = y;
    }
    public void decrement() {
            setTresor(getTresor() - 1);
    }
    //region Accessors

    public int getTresor() {
        return tresor;
    }

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
