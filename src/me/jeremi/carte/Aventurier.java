package me.jeremi.carte;

import org.junit.jupiter.api.Test;

/**
 * Représente un aventurier présent sur la carte.
 * @author Jeremi Friggit(@KorabFusian)
 */
class Aventurier {

    private final String nom;
    private int x;
    private int y;
    private Direction orientation;
    /**
     * Représente le chemin restant, pas le chemin original (qui n'est pas stocké)
     */
    private String cheminRestant;
    private int tresor;

    /**
     * Constructeur d'aventurier
     * @param nom le nom de l'aventurier
     * @param x sa position sur l'axe horizontal
     * @param y sa position sur l'axe vertical
     * @param orientation son orientation (de l'enum me.jeremi.carte.Direction, pas le caractère)
     * @param chemin la chaîne de caractères qui correspond au chemin à parcourir
     * @see Direction
     */
    public Aventurier(String nom, int x, int y, Direction orientation, String chemin) {
        this.nom = nom;
        this.x = x;
        this.y = y;
        this.orientation = orientation;
        this.tresor = 0;
        this.cheminRestant = chemin.replaceAll("[^ADG]", ""); // security wrong characters removal
    }

    /**
     * Enlève la première lettre du chemin restant
     * Si le chemin restant est vide, il devient un empty string ""
     */
    public void popCheminRestant() {
        if(!getCheminRestant().isEmpty()) {
            setCheminRestant(getCheminRestant().substring(1));
        }
    }

    /**
     * Update la position de l'aventurier et enlève un mouvement à la séquence restante.
     * Cette fonction est appelée à chaque mouvement de l'aventurier sur la carte.
     * @param x la nouvelle position de l'aventurier sur l'axe horizontal
     * @param y la nouvelle position de l'aventurier sur l'axe vertical
     */
    public void advanceToPosition(int x, int y) {
        setX(x);
        setY(y);
        popCheminRestant();
    }

    /**
     * Renvoie le premier caractère de l'orientation de l'aventurier
     * (N pour Nord, O pour Ouest, etc.)
     * Fonction appelée par la fonction toString pour écrire la ligne de l'aventurier dans le bon format
     * @return le caractère qui correspond à l'orientation
     * @see Direction
     */
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
        }
        // nécessaire pour compiler, jamais atteint
        return 'n';
    }

    /**
     * Incrémente le nombre de trésors ramassé par cet aventurier
     */
    public void incrementTresor() {
        setTresor(getTresor() + 1);
    }

    /**
     * Fait tourner l'orientation de l'aventurier de 90 degrés vers la gauche
     * Si il était tourné vers le nord il va à l'ouest, etc.
     */
    public void tournerAGauche() {
        switch (getOrientation()) {
        case NORD -> setOrientation(Direction.OUEST);
        case SUD -> setOrientation(Direction.EST);
        case EST -> setOrientation(Direction.NORD);
        case OUEST -> setOrientation(Direction.SUD);
        }
        popCheminRestant();
    }

    /**
     * Fait tourner l'orientation de l'aventurier de 90 degrés vers la droite
     * Si il était tourné vers le nord il va à l'est, etc.
     */
    public void tournerADroite() {
        switch (getOrientation()) {
        case NORD -> setOrientation(Direction.EST);
        case SUD -> setOrientation(Direction.OUEST);
        case EST -> setOrientation(Direction.SUD);
        case OUEST -> setOrientation(Direction.NORD);
        }
        popCheminRestant();
    }

    /**
     * Renvoie la chaîne de caractères correspondant à cet aventurier,
     * formatée pour le fichier de sortie.
     * Le format en question :
     * {A comme me.jeremi.carte.Aventurier} - {Nom de l’aventurier} - {Axe horizontal} - {Axe
     *  vertical} - {Orientation} - {Nb. trésors ramassés}
     * @return la String créée
     */
    @Override
    public String toString() {
        return "A - " + getNom() + " - " + getX() + " - " + getY() + " - " + getOrientationChar() + " - " + getTresor();

    }



    //region Accessors

    public String getNom() {
        return nom;
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Direction getOrientation() {
        return orientation;
    }

    public String getCheminRestant() {
        return cheminRestant;
    }

    public int getTresor() {
        return tresor;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setCheminRestant(String cheminRestant) {
        this.cheminRestant = cheminRestant.replaceAll("[^ADG]", "");
    }

    public void setTresor(int tresor) {
        this.tresor = tresor;
    }

    public void setOrientation(Direction orientation) {
        this.orientation = orientation;
    }

    //endregion

}
