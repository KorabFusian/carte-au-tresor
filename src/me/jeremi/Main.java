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
        String inPath = getInputFilePath();
        CarteAuTresor carte = inputParser.parseFile(inPath);
        consoleCarte(carte);
        carte.mouvementAventuriers();
        consoleCarte(carte);
        outputWriter.writeCarteToFile(carte, constructOutputPath(inPath));
        messageForOutputFile();

    }

    private static String getInputFilePath() {
        System.out.println("""
                /////////////////////////////////////////////////////////////////////////////////////
                //              Veuillez renseigner le chemin du fichier de lecture.               //
                /////////////////////////////////////////////////////////////////////////////////////
                """);
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    /**
     * Construit le chemin du fichier de sortie en fonction de l'entrée.
     * Les 2  fichiers sont donc toujours dans le même dossier et
     * le chemin d'output est simplement le chemin d'input + " - RESULTAT".
     * @param inPath le chemin d'entrée
     * @return le chemin de sortie.
     */
    private static String constructOutputPath(String inPath) {
        // On enlève l'extension au cas où
        String outPath = inPath.replaceAll(".txt", "");
        outPath += " - RESULTAT.txt";
        return outPath;
    }

    private static void consoleCarte(CarteAuTresor carte) {
        System.out.print("""

                =====================================================================================
                                                   ETAT DE LA CARTE
                =====================================================================================
                """ + carte.toString());
    }

    private static void messageForOutputFile() {
        System.out.print("""
                ----------------------------------------------
                Votre fichier est prêt, vous pouvez l'ouvrir !
                -----------------------------------------------
                """);

    }
}
