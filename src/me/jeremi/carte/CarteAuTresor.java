package me.jeremi.carte;

import java.util.*;

public class CarteAuTresor {

    private List<List<String>> carte = null; // la carte elle-meme
    private List<Aventurier> aventuriers; // liste des aventuriers (pour ne pas se perdre)

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

    private List<Tresor> tresors; // liste des trésors (pour ne pas se perdre)


    public void print() {
        for (List<String> list : carte) {
            System.out.println(Arrays.toString(list.toArray()));
        }
        for (Aventurier aventurier : aventuriers
             ) {
            System.out.println(aventurier.toString());
        }
    }

    public void createMap(String params) throws WrongCarteInputException {
        if (carte != null) {
            throw new WrongCarteInputException("2 créations de cartes dans la même ligne");
        }
        aventuriers = new ArrayList<>();
        tresors = new ArrayList<>();
        String[] split = params.split("-");
        int x = Integer.parseInt(split[1]);
        int y = Integer.parseInt(split[2]);
        carte = new ArrayList<>();
        for (int i = 0; i < y; i++) {
            List<String> line = new ArrayList<>(Collections.nCopies(x, "-"));
            carte.add(line);
        }
    }

    public void addMontagne(String params) throws WrongCarteInputException {
        String[] split = params.split("-");
        int x = Integer.parseInt(split[1]);
        int y = Integer.parseInt(split[2]);
        if (y > carte.size() || x > carte.get(0).size()) {
            throw new WrongCarteInputException("Création de montagne hors des limites de la carte");
        }
        carte.get(y).set(x, "M");
    }

    public void addTresor(String params) throws WrongCarteInputException {
        String[] split = params.split("-");
        int x = Integer.parseInt(split[1]);
        int y = Integer.parseInt(split[2]);
        if (y > carte.size() || x > carte.get(0).size()) {
            throw new WrongCarteInputException("Création de trésor hors des limites de la carte");
        }
        int tresor = Integer.parseInt(split[3]);
        if (tresor < 1) {
            throw new WrongCarteInputException("Trésor ne peut pas être 0 ou négatif");
        }
        tresors.add(new Tresor(tresor, x, y));
        carte.get(y).set(x, "T (" + tresor + ")");
    }

    public void addAventurier(String params) throws WrongCarteInputException {
        String[] split = params.split("-");
        String nom = split[1];
        int x = Integer.parseInt(split[2]);
        int y = Integer.parseInt(split[3]);

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
        carte.get(y).set(x, "A (" + nom + ")" );
        aventuriers.add(new Aventurier(nom, x, y, orientation, chemin));
    }

    private Tresor getTresorByPosition(int x, int y) {
        for (Tresor tresor : tresors) {
            if (tresor.getX() == x && tresor.getY() == y) return tresor;
        }
        return null;
    }

    public void mouvementAventuriers() {
        boolean atLeastOneMoveLeft = true;
        while (atLeastOneMoveLeft) {
            atLeastOneMoveLeft = false;

            // on effectue 1 tour pour chaque aventurier
            for (Aventurier aventurier : aventuriers) {
                try {
                    tour(aventurier);
                } catch (InvalidMoveException e) {
                    System.out.println("Erreur durant le mouvement des aventuriers.");
                    e.printStackTrace();
                }
            }

            // vérification qu'au moins 1 d'entre eux doit encore bouger
            for (Aventurier aventurier : aventuriers) {
                if (!aventurier.getCheminRestant().isEmpty()) {
                    atLeastOneMoveLeft = true;
                    break;
                }
            }
        }
    }

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
        // vérifier si obstacle devant
        if (    carte.get(targetY).get(targetX).charAt(0) == 'A' ||
                carte.get(targetY).get(targetX).equals("M")
        ) {
            // si obstacle, on ne bouge pas
            targetX = initialX;
            targetY = initialY;
        } else {
            // si tresor a la position target
            Tresor tresor =  getTresorByPosition(targetX, targetY);
            if (tresor != null && tresor.getTresor() > 0) {
               tresor.decrement();
               aventurier.incrementTresor();
            }
            carte.get(initialY).set(initialX, "-");
            carte.get(targetY).set(targetX, "A (" + aventurier.getNom() + ")");
            tresor = getTresorByPosition(initialX, initialY);
            if (tresor != null && tresor.getTresor() > 0) {
                carte.get(initialY).set(initialX, "T (" + tresor.getTresor() + ")" );
            }
        }

        // On finit par modifier les stats de l'aventurier
        aventurier.advanceToPosition(targetX, targetY);

    }


}
