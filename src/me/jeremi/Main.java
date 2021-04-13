package me.jeremi;

import me.jeremi.carte.CarteAuTresor;
import me.jeremi.file.InputParser;
import me.jeremi.file.OutputWriter;

import java.util.Scanner;

/**
 * Permet d'exécuter tout ce qui est demandé pour le test de la carte au trésor.
 *
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
        messageForOutputFile();

    }

    static String getInputFilePath() {
        System.out.println("""
                /////////////////////////////////////////////////////////////////////////////////////
                //              Veuillez renseigner le chemin du fichier de lecture.               //
                /////////////////////////////////////////////////////////////////////////////////////
                """);
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    static String getOutputFilePath() {
        System.out.println("""
                ////////////////////////////////////////////////////////////////////////////////////
                //              Veuillez renseigner le chemin du fichier d'écriture.              //
                ////////////////////////////////////////////////////////////////////////////////////
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

    static void messageForOutputFile() {
        System.out.print("""
                ----------------------------------------------
                Votre fichier est prêt, vous pouvez l'ouvrir !
                -----------------------------------------------
                """);


    }
}
