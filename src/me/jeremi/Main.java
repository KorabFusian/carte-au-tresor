package me.jeremi;

import me.jeremi.carte.CarteAuTresor;
import me.jeremi.file.InputParser;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

    	InputParser inputParser = new InputParser();
	    CarteAuTresor carte = inputParser.parseFile(getInputFilePath());
	    consoleCarte(carte);
	    carte.mouvementAventuriers();
	    consoleCarte(carte);
    }

    static String getInputFilePath() {
    	System.out.println("""
				=====================================================================================
									Veuillez renseigner le fichier de lecture.
				=====================================================================================
				""");
    	Scanner sc = new Scanner(System.in);
		return sc.nextLine();
	}

	static void consoleCarte(CarteAuTresor carte) {
    	String str = """
    
				=====================================================================================
													ETAT DE LA CARTE
				=====================================================================================
				""" + carte.toString();
    	System.out.println(str);
	}
}
