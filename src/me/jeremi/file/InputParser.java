package me.jeremi.file;
import me.jeremi.carte.CarteAuTresor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * InputParser permet de lire un fichier texte pour obtenir les informations d'une carte.
 */
public class InputParser {

    /**
     * Traverse le fichier d'entrée pour l'interpréter ligne par ligne
     *
     * @param filename le nom du fichier d'entrée
     * @return carteInput la carte avant tout mouvement d'aventurier
     */
    public CarteAuTresor parseFile(String filename) {
        // On crée une instance de carte qui aura tout ce qui est spécifié dans le fichier
        CarteAuTresor carteInput  = new CarteAuTresor();
        try {
            File txtInput = new File(filename);
            Scanner lineReader = new Scanner(txtInput);
            while (lineReader.hasNextLine()) {
                String line = lineReader.nextLine();

                // On retire les espaces blancs pour être sûr de récupérer le bon premier caractère
                // et pour pouvoir parse chaque paramètre sans espace
                line = line.replaceAll("\\s", "");
                if (line.charAt(0) != '#') {   // skip comments
                    parseLine(line, carteInput);
                }
            }
            lineReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Fichier d'entrée introuvable.");
            e.printStackTrace();
        }
        return carteInput;
    }

    void parseLine(String line, CarteAuTresor carteInput) {
        try {
            switch (line.charAt(0)) {
                case 'C' -> carteInput.createMap(line);
                case 'M' -> carteInput.addMontagne(line);
                case 'T' -> carteInput.addTresor(line);
                case 'A' -> carteInput.addAventurier(line);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
