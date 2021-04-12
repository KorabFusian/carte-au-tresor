package me.jeremi;

import me.jeremi.carte.CarteAuTresor;
import me.jeremi.file.InputParser;

public class Main {

    public static void main(String[] args) {

    	InputParser inputParser = new InputParser();
	    CarteAuTresor carte = inputParser.parseFile("exemple.txt");
	    carte.mouvementAventuriers();
	    carte.print();
    }
}
