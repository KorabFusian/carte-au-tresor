package me.jeremi.carte;

import java.util.*;

/**
 * Gère la carte au trésor et ses règles. Pour créer la carte elle-même, il faut
 * appeler la méthode createMap avec la ligne du fichier d'input qui commence
 * par C. Un seul objet CarteAuTresor gère une seule carte : si on veut en créer
 * plusieurs, il faut créer un objet CarteAuTresor par carte à créer.
 *
 * @author Jeremi Friggit(@KorabFusian)
 * @see Montagne
 * @see Tresor
 * @see Aventurier
 */
public class CarteAuTresor {

    private List<List<String>> carte = Collections.emptyList(); // les String à représentés sur la carte
    private List<Montagne> montagnes = Collections.emptyList();
    private List<Tresor> tresors = Collections.emptyList();
    private List<Aventurier> aventuriers = Collections.emptyList();

    /**
     * Initialise une carte vide avec les bonnes dimensions
     *
     * @param params la ligne commençant par C venant directement du fichier source
     *               (sans les espaces)
     * @throws IllegalArgumentException exception si on a déjà créé la carte
     */
    public void createMap(String params) throws IllegalArgumentException {
        // Si on a déjà une carte, on a un problème
        if (!getCarte().isEmpty()) {
            throw new IllegalArgumentException("2 créations de cartes dans le même fichier");
        }

        // Initialiser les listes
        setAventuriers(new ArrayList<>());
        setTresors(new ArrayList<>());
        setMontagnes(new ArrayList<>());

        // Récupérer les dimensions
        String[] split = params.split("-");
        int x = Integer.parseInt(split[1]);
        int y = Integer.parseInt(split[2]);

        // Pas de dimensions nulles ou négatives
        if (x < 1 || y < 1) {
            throw new IllegalArgumentException("2 créations de cartes dans la même ligne");
        }
        setCarte(new ArrayList<>());

        // On remplit la carte d'éléments vides
        for (int i = 0; i < y; i++) {
            List<String> line = new ArrayList<>(Collections.nCopies(x, "-"));
            getCarte().add(line);
        }
    }

    /**
     * Ajoute une montagne à la carte et crée l'objet Montagne correspondant dans la
     * liste montagnes
     *
     * @param params la ligne commençant par M venant directement du fichier source
     *               (sans les espaces)
     * @throws IllegalArgumentException exception si on ne peut pas mettre de
     *                                  montagne ici
     */
    public void addMontagne(String params) throws IllegalArgumentException {
        if (getCarte().isEmpty()) {
            throw new IllegalArgumentException("Pas de carte dans laquelle créer une montagne");
        }
        String[] split = params.split("-");
        int x = Integer.parseInt(split[1]);
        int y = Integer.parseInt(split[2]);
        if (y > getCarte().size() || x > getCarte().get(0).size()) {
            throw new IllegalArgumentException("Création de montagne hors des limites de la carte");
        }
        if (getMontagneByPosition(x, y) != null || getTresorByPosition(x, y) != null
                || getAventurierByPosition(x, y) != null) { // si la case n'est pas vide (déjà une montagne, un trésor
                                                            // ou un aventurier)
            throw new IllegalArgumentException("Création de montagne sur une case déjà occupée");
        }

        getMontagnes().add(new Montagne(x, y));
        getCarte().get(y).set(x, "M");
    }

    /**
     * Ajoute un trésor à la carte et crée l'objet Tresor correspondant dans la
     * liste tresors
     *
     * @param params la ligne commençant par T venant directement du fichier source
     *               (sans les espaces)
     * @throws IllegalArgumentException exception si on ne peut pas mettre de trésor
     *                                  ici ou qu'il est vide/négatif
     */
    public void addTresor(String params) throws IllegalArgumentException {
        if (getCarte().isEmpty()) {
            throw new IllegalArgumentException("Pas de carte dans laquelle créer un trésor");
        }
        String[] split = params.split("-");
        int x = Integer.parseInt(split[1]);
        int y = Integer.parseInt(split[2]);
        if (y > getCarte().size() || x > getCarte().get(0).size()) {
            throw new IllegalArgumentException("Création de trésor hors des limites de la carte");
        }
        if (getMontagneByPosition(x, y) != null || getTresorByPosition(x, y) != null
                || getAventurierByPosition(x, y) != null) { // si la case n'est pas vide (déjà une montagne, un trésor
                                                            // ou un aventurier)
            throw new IllegalArgumentException("Création de trésor sur une case déjà occupée");
        }

        int tresor = Integer.parseInt(split[3]);
        if (tresor < 1) {
            throw new IllegalArgumentException("Trésor ne peut pas être 0 ou négatif");
        }

        getTresors().add(new Tresor(x, y, tresor));
        getCarte().get(y).set(x, "T (" + tresor + ")");
    }

    /**
     * Ajoute un aventurier à la carte et crée l'objet Aventurier correspondant dans
     * la liste aventuriers
     *
     * @param params la ligne commençant par A venant directement du fichier source
     *               (sans les espaces)
     * @throws IllegalArgumentException si on ne peut pas créer d'aventurier ou si
     *                                  l'orientation n'est pas bonne
     */
    public void addAventurier(String params) throws IllegalArgumentException {
        if (getCarte().isEmpty()) {
            throw new IllegalArgumentException("Pas de carte dans laquelle créer un aventurier");
        }
        String[] split = params.split("-");
        String nom = split[1];
        int x = Integer.parseInt(split[2]);
        int y = Integer.parseInt(split[3]);
        if (y > getCarte().size() || x > getCarte().get(0).size()) {
            throw new IllegalArgumentException("Création d'aventurier hors des limites de la carte");
        }
        if (getMontagneByPosition(x, y) != null || getTresorByPosition(x, y) != null
                || getAventurierByPosition(x, y) != null) { // si la case n'est pas vide (déjà une montagne, un trésor
                                                            // ou un aventurier)
            throw new IllegalArgumentException("Création d'aventurier sur une case déjà occupée");
        }

        // gestion de l'orientation
        Direction orientation;
        switch (split[4]) {
        case "N" -> orientation = Direction.NORD;
        case "E" -> orientation = Direction.EST;
        case "S" -> orientation = Direction.SUD;
        case "O" -> orientation = Direction.OUEST;
        default -> throw new IllegalArgumentException("Mauvaise orientation d'aventurier");
        }
        String chemin = split[5];
        getCarte().get(y).set(x, "A (" + nom + ")");
        getAventuriers().add(new Aventurier(nom, x, y, orientation, chemin));
    }

    /**
     * Effectue tous les mouvements d'aventuriers tour par tour. Tous les
     * aventuriers effectuent 1 mouvement par tour.
     */
    public void mouvementAventuriers() {
        // Si pas d'aventuriers à faire bouger, on ne fait rien
        if (getAventuriers().isEmpty())
            return;

        boolean atLeastOneMoveLeft;
        do {
            atLeastOneMoveLeft = false;

            // on effectue 1 tour pour chaque aventurier
            for (Aventurier aventurier : getAventuriers()) {
                tour(aventurier);
            }

            // vérification qu'au moins 1 d'entre eux doit encore bouger
            for (Aventurier aventurier : getAventuriers()) {
                if (!aventurier.getCheminRestant().isEmpty()) {
                    atLeastOneMoveLeft = true;
                    break;
                }
            }
        } while (atLeastOneMoveLeft);
    }

    /**
     * Effectue le tour d'1 aventurier
     *
     * @param aventurier l'aventurier à déplacer
     */
    private void tour(Aventurier aventurier) {

        // Si l'aventurier a fini de bouger on n'y touche plus
        if (aventurier.getCheminRestant().isEmpty()) {
            return;
        }

        /*
         * Le chemin restant des aventuriers contient UNIQUEMENT les caractères A/G/D
         * (voir le setter), donc pas besoin de gérer autre chose
         */
        switch (aventurier.getCheminRestant().charAt(0)) {

        case 'G' -> aventurier.tournerAGauche();

        case 'D' -> aventurier.tournerADroite();

        case 'A' -> avancerAventurier(aventurier);
        }

    }

    /**
     * Fait avancer un aventurier dans la direction vers laquelle il est tourné.
     *
     * @param aventurier l'aventurier à déplacer
     */
    private void avancerAventurier(Aventurier aventurier) {

        int initialX = aventurier.getX();
        int initialY = aventurier.getY();
        int targetX = initialX;
        int targetY = initialY;

        // on set la position cible en fct de l'orientation
        switch (aventurier.getOrientation()) {
        case NORD -> targetY = initialY - 1;
        case SUD -> targetY = initialY + 1;
        case EST -> targetX = initialX + 1;
        case OUEST -> targetX = initialX - 1;
        }

        // On reste dans les limites de la carte
        targetX = Math.min(targetX, getCarte().get(0).size() - 1);
        targetX = Math.max(targetX, 0);
        targetY = Math.min(targetY, getCarte().size() - 1);
        targetY = Math.max(targetY, 0);

        // Vérifier si obstacle à l'arrivée (Montagne ou Aventurier)
        if (getMontagneByPosition(targetX, targetY) != null || getAventurierByPosition(targetX, targetY) != null) {
            // Si obstacle il y a, on ne bouge pas
            targetX = initialX;
            targetY = initialY;
        } else {
            // On vérifie si on arrive sur un trésor
            Tresor tresor = getTresorByPosition(targetX, targetY);
            if (tresor != null && tresor.getTresor() > 0) {
                tresor.decrement();
                aventurier.incrementTresor();
            }
            // Reset la position initiale
            getCarte().get(initialY).set(initialX, "-");
            // Replacer l'aventurier
            getCarte().get(targetY).set(targetX, "A (" + aventurier.getNom() + ")");
            tresor = getTresorByPosition(initialX, initialY);
            if (tresor != null && tresor.getTresor() > 0) {
                getCarte().get(initialY).set(initialX, "T (" + tresor.getTresor() + ")");
            }
        }

        // On finit par modifier les stats de l'aventurier
        aventurier.advanceToPosition(targetX, targetY);

    }

    /**
     * Renvoie le trésor qui se trouve à une certaine position
     *
     * @param x la coordonnée horizontale du trésor
     * @param y la coordonnée verticale du trésor
     * @return le trésor correspondant
     */
    private Tresor getTresorByPosition(int x, int y) {
        for (Tresor tresor : getTresors()) {
            if (tresor.getX() == x && tresor.getY() == y)
                return tresor;
        }
        return null;
    }

    /**
     * Renvoie le trésor qui se trouve à une certaine position
     *
     * @param x la coordonnée horizontale du trésor
     * @param y la coordonnée verticale du trésor
     * @return le trésor correspondant
     */
    private Aventurier getAventurierByPosition(int x, int y) {
        for (Aventurier aventurier : getAventuriers()) {
            if (aventurier.getX() == x && aventurier.getY() == y)
                return aventurier;
        }
        return null;
    }

    /**
     * Renvoie le trésor qui se trouve à une certaine position
     *
     * @param x la coordonnée horizontale du trésor
     * @param y la coordonnée verticale du trésor
     * @return le trésor correspondant
     */
    private Montagne getMontagneByPosition(int x, int y) {

        for (Montagne montagne : getMontagnes()) {
            if (montagne.getX() == x && montagne.getY() == y)
                return montagne;
        }
        return null;
    }

    /**
     * Renvoie la taille des cases pour écriture par toString. Si aucun trésor ni
     * aventurier, c'est la taille minimum (2 pour avoir un espace) Sinon c'est le
     * nom/chiffre le plus long + 5
     * 
     * @return la taille des cases
     */
    private int getCaseSize() {
        int longest = -5;

        // On parcourt les trésors
        for (Tresor tresor : getTresors()) {
            // si un trésor a le plus de chiffres
            if (String.valueOf(tresor.getTresor()).length() > longest)
                longest = String.valueOf(tresor.getTresor()).length();
        }

        // On parcourt les aventuriers
        for (Aventurier aventurier : getAventuriers()) {
            // Si le nom d'un aventurier est le plus long
            if (aventurier.getNom().length() > longest)
                longest = aventurier.getNom().length();
        }

        // longest + 5 pour avoir la place pour pour avoir la place d'écrire
        // les caractères 'A/T', '(', ' ', ')', ' '
        return Math.max(longest + 5, 2);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        String format = "%-" + getCaseSize() + "s";
        for (List<String> line : getCarte()) {
            for (String box : line) {
                str.append(String.format(format, box));
            }
            str.append("\n");
        }
        return str.toString();
    }

    // region Accessors
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

    public List<Montagne> getMontagnes() {
        return montagnes;
    }

    public void setMontagnes(List<Montagne> montagnes) {
        this.montagnes = montagnes;
    }
    // endregion

}
