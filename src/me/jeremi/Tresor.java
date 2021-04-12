package me.jeremi;

public class Tresor {

    public Tresor(int tresor, int x, int y) {
        this.tresor = tresor;
        this.x = x;
        this.y = y;
    }

    private int tresor;
    private final int x;
    private final int y;

    public int getTresor() {
        return tresor;
    }
    public void decrement() {
        if (tresor > 0) {
            tresor -= 1;
        }
    }

    
    public int getY() {
        return y;
    }


    public int getX() {
        return x;
    }

}
