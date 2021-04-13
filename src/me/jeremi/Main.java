package me.jeremi;

import me.jeremi.carte.CarteAuTresor;
import me.jeremi.file.InputParser;

public class Main {

    public static void main(String[] args) {

    	InputParser inputParser = new InputParser();
	    CarteAuTresor carte = inputParser.parseFile("");
		System.out.println(carte);
	    carte.mouvementAventuriers();
	    System.out.println(carte);
    }
}
