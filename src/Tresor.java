public class Tresor {

    public Tresor(int tresor, int x, int y) {
        this.tresor = tresor;
        this.x = x;
        this.y = y;
    }

    private int tresor;
    private int x;
    private int y;

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

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }
}
