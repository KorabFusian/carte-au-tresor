package me.jeremi.carte;

import java.util.*;

/**
 * Représente la totalité de la carte au trésor
 * 3 gros éléments : la carte en texte, la liste des aventuriers, la liste des trésors
 * (ce sont les éléments amenés à changer).
 * @see Aventurier
 * @see Tresor
 */
public class CarteAuTresor {

    private List<List<String>> carte = null; // la carte elle-meme
    private List<Aventurier> aventuriers; // liste des aventuriers (pour ne pas se perdre)
    private List<Tresor> tresors; // liste des trésors (pour ne pas se perdre)


    /**
     * Imprime l'état actuel de la carte
     */
    public void print() {
        for (List<String> list : getCarte()) {
            System.out.println(Arrays.toString(list.toArray()));
        }
    }

    /**
     * Initialise une carte vide avec les bonnes dimensions
     * @param params la ligne C venant directement du fichier source (sans les espaces)
     * @throws WrongCarteInputException exception si on a déjà créé la carte
     */
    public void createMap(String params) throws WrongCarteInputException {
        if (getCarte() != null) {
            throw new WrongCarteInputException("2 créations de cartes dans la même ligne");
        }
        setAventuriers(new ArrayList<>());
        setTresors(new ArrayList<>());
        String[] split = params.split("-");
        int x = Integer.parseInt(split[1]);
        int y = Integer.parseInt(split[2]);
        setCarte(new ArrayList<>());
        for (int i = 0; i < y; i++) {
            List<String> line = new ArrayList<>(Collections.nCopies(x, "-"));
            getCarte().add(line);
        }
    }

    /**
     * Ajoute une montagne à la carte
     * @param params la ligne M venant directement du fichier source (sans les espaces)
     * @throws WrongCarteInputException exception si on sort des bords de la carte
     */
    public void addMontagne(String params) throws WrongCarteInputException {
        String[] split = params.split("-");
        int x = Integer.parseInt(split[1]);
        int y = Integer.parseInt(split[2]);
        if (y > getCarte().size() || x > getCarte().get(0).size()) {
            throw new WrongCarteInputException("Création de montagne hors des limites de la carte");
        }
        getCarte().get(y).set(x, "M");
    }

    /**
     * Ajoute un trésor à la carte et crée un objet trésor dans la liste
     * @param params la ligne T venant directement du fichier source (sans les espaces)
     * @throws WrongCarteInputException exception si on sort des bords de la carte ou que le trésor est vide/négatif
     */
    public void addTresor(String params) throws WrongCarteInputException {
        String[] split = params.split("-");
        int x = Integer.parseInt(split[1]);
        int y = Integer.parseInt(split[2]);
        if (y > getCarte().size() || x > getCarte().get(0).size()) {
            throw new WrongCarteInputException("Création de trésor hors des limites de la carte");
        }
        int tresor = Integer.parseInt(split[3]);
        if (tresor < 1) {
            throw new WrongCarteInputException("Trésor ne peut pas être 0 ou négatif");
        }
        getTresors().add(new Tresor(tresor, x, y));
        getCarte().get(y).set(x, "T (" + tresor + ")");
    }

    /**
     * Ajoute un aventurier à la carte et crée un objet aventurier dans la liste
     * @param params la ligne A venant directement du fichier source (sans les espaces)
     * @throws WrongCarteInputException si on sort des bords de la carte ou que l'orientation n'est pas bonne
     */
    public void addAventurier(String params) throws WrongCarteInputException {
        String[] split = params.split("-");
        String nom = split[1];
        int x = Integer.parseInt(split[2]);
        int y = Integer.parseInt(split[3]);
        if (y > getCarte().size() || x > getCarte().get(0).size()) {
            throw new WrongCarteInputException("Création d'aventurier hors des limites de la carte");
        }

        // gestion de l'orientation
        Direction orientation;
        switch (split[4]) {
            case "N" -> orientation = Direction.NORD;
            case "E" -> orientation = Direction.EST;
            case "S" -> orientation = Direction.SUD;
            case "O" -> orientation = Direction.OUEST;
            default -> throw new WrongCarteInputException("Mauvaise orientation d'aventurier");
        }
        String chemin = split[5];
        getCarte().get(y).set(x, "A (" + nom + ")" );
        getAventuriers().add(new Aventurier(nom, x, y, orientation, chemin));
    }


    /**
     * Effectue tous les mouvements d'aventuriers tour par tour.
     * Tous les aventuriers effectuent 1 mouvement par tour.
     */
    public void mouvementAventuriers() {
        boolean atLeastOneMoveLeft = true;
        while (atLeastOneMoveLeft) {
            atLeastOneMoveLeft = false;

            // on effectue 1 tour pour chaque aventurier
            for (Aventurier aventurier : getAventuriers()) {
                try {
                    tour(aventurier);
                } catch (InvalidMoveException e) {
                    System.out.println("Erreur durant le mouvement des aventuriers.");
                    e.printStackTrace();
                }
            }

            // vérification qu'au moins 1 d'entre eux doit encore bouger
            for (Aventurier aventurier : getAventuriers()) {
                if (!aventurier.getCheminRestant().isEmpty()) {
                    atLeastOneMoveLeft = true;
                    break;
                }
            }
        }
    }

    /**
     * Effectue le tour d'1 aventurier
     * @param aventurier l'aventurier à déplacer
     * @throws InvalidMoveException exception si la commande ded mouvement est invalide (pas A/G/D)
     */
    private void tour(Aventurier aventurier) throws InvalidMoveException {

        // Si l'aventurier a fini de bouger on n'y touche plus
        if (aventurier.getCheminRestant().isEmpty()) {
            return;
        }

        switch (aventurier.getCheminRestant().charAt(0)) {

            case 'G' -> aventurier.tournerAGauche();

            case 'D' -> aventurier.tournerADroite();

            case 'A' -> avancerAventurier(aventurier);

            default -> throw new InvalidMoveException("Commande de mouvement invalide: " + aventurier.getCheminRestant().charAt(0));
        }

    }

    /**
     * Renvoie le trésor qui se trouve à une certaine position
     * @param x la coordonnée horizontale du trésor
     * @param y la coordonnée verticale du trésor
     * @return le trésor correspondant
     */
    private Tresor getTresorByPosition(int x, int y) {
        for (Tresor tresor : getTresors()) {
            if (tresor.getX() == x && tresor.getY() == y) return tresor;
        }
        return null;
    }

    /**
     * Fait avancer un aventurier dans la direction vers laquelle il est tourné.
     * @param aventurier l'aventurier à déplacer
     * @throws InvalidMoveException exception si l'orientation n'est pas N,S,E,O
     */
    private void avancerAventurier (Aventurier aventurier) throws InvalidMoveException {

        int initialX = aventurier.getX();
        int initialY = aventurier.getY();
        int targetX;
        int targetY;

        // on set la position cible en fct de l'orientation
        switch(aventurier.getOrientation()) {
            case NORD -> {
                targetX = initialX;
                targetY = initialY - 1;
            }
            case SUD -> {
                targetX = initialX;
                targetY = initialY + 1;
            }
            case EST -> {
                targetX = initialX + 1;
                targetY = initialY;
            }
            case OUEST -> {
                targetX = initialX - 1;
                targetY = initialY;
            }
            default -> throw new InvalidMoveException("Orientation invalide: " + aventurier.getOrientation());
        }

        // On reste dans les limites de la carte
        targetX = Math.min(targetX, getCarte().get(0).size());
        targetX = Math.max(targetX, 0);
        targetY = Math.min(targetY, getCarte().size());
        targetY = Math.max(targetY, 0);

        // Vérifier si obstacle devant
        if (    getCarte().get(targetY).get(targetX).charAt(0) == 'A' ||
                getCarte().get(targetY).get(targetX).equals("M")
        ) {
            // Si obstacle il y a, on ne bouge pas
            targetX = initialX;
            targetY = initialY;
        } else {
            // On vérifie si on arrive sur un trésor
            Tresor tresor =  getTresorByPosition(targetX, targetY);
            if (tresor != null && tresor.getTresor() > 0) {
               tresor.decrement();
               aventurier.incrementTresor();
            }
            getCarte().get(initialY).set(initialX, "-");
            getCarte().get(targetY).set(targetX, "A (" + aventurier.getNom() + ")");
            tresor = getTresorByPosition(initialX, initialY);
            if (tresor != null && tresor.getTresor() > 0) {
                getCarte().get(initialY).set(initialX, "T (" + tresor.getTresor() + ")" );
            }
        }

        // On finit par modifier les stats de l'aventurier
        aventurier.advanceToPosition(targetX, targetY);

    }

    //region Accessors
    public List<List<String>> getCarte() {
        return carte;
    }

    public void setCarte(List<List<String>> carte) {
        this.carte = carte;
    }

    public List<Aventurier> getAventuriers() {
        return aventuriers;
    }

    public void setAventuriers(List<Aventurier> aventuriers) {
        this.aventuriers = aventuriers;
    }

    public List<Tresor> getTresors() {
        return tresors;
    }

    public void setTresors(List<Tresor> tresors) {
        this.tresors = tresors;
    }
    //endregion

}
