package me.jeremi;

import me.jeremi.carte.CarteAuTresor;
import me.jeremi.file.InputParser;
import me.jeremi.file.OutputWriter;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Permet d'exécuter tout ce qui est demandé pour le test de la carte au trésor.
 * @author Jeremi Friggit (@KorabFusian)
 */
public class Main {

    public static void main(String[] args) {

    	InputParser inputParser = new InputParser();
    	OutputWriter outputWriter = new OutputWriter();
	    CarteAuTresor carte = inputParser.parseFile(getInputFilePath());
		String outPath = getOutputFilePath();
	    consoleCarte(carte);
	    carte.mouvementAventuriers();
	    consoleCarte(carte);
		outputWriter.writeCarteToFile(carte, outPath);
		openOutputFile(outPath);

    }

    static String getInputFilePath() {
    	System.out.println("""
				=====================================================================================
								Veuillez renseigner le chemin du fichier de lecture.
				=====================================================================================
				""");
    	Scanner sc = new Scanner(System.in);
		return sc.nextLine();
	}

	static String getOutputFilePath() {
		System.out.println("""
				=====================================================================================
								Veuillez renseigner le chemin du fichier d'écriture.
				=====================================================================================
				""");
		Scanner sc = new Scanner(System.in);
		return sc.nextLine();
	}

	static void consoleCarte(CarteAuTresor carte) {
    	System.out.print("""
    
				=====================================================================================
													ETAT DE LA CARTE
				=====================================================================================
				""" + carte.toString());
	}

	static void openOutputFile(String pathname) {
    	File file = new File(pathname);
		//first check if Desktop is supported by Platform or not
		if(!Desktop.isDesktopSupported()){
			System.out.println("Votre fichier est prêt, vous pouvez l'ouvrir !");
			return;
		}

		Desktop desktop = Desktop.getDesktop();
		if(file.exists()) {
			try {
				desktop.open(file);
			} catch (IOException e) {
				System.out.println("""
						Une exception est survenue lors de l'ouverture du fichier.
						Ne vous inquiétez pas, ce fichier a quand même bien été créé.
						Vous pouvez toujours l'ouvrir manuellement !
						""");
				e.printStackTrace();
			}
		}

	}
}
