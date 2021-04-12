package me.jeremi.file;/*
 * FileParser
 * Version 1.0 (2021-04-11)
 *
 * Ouvre un fichier .txt et le transforme en map utilisable
 *
 *
 * Created by Jeremi Friggit (KorabFusian)
 */

import me.jeremi.carte.CarteAuTresor;
import me.jeremi.carte.WrongCarteInputException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class InputParser {

    public CarteAuTresor getCarteInput() {
        return carteInput;
    }

    private final CarteAuTresor carteInput;

    public InputParser() {
        carteInput = new CarteAuTresor();
    }

    /**
     * Traverse le fichier d'entrée pour l'interpréter ligne par ligne
     *
     * @param filename le nom du fichier d'entrée
     * @return carteInput la carte avant tout mouvement d'aventurier
     */
    public CarteAuTresor parseFile(String filename) {
        try {
            File txtInput = new File(filename);
            Scanner lineReader = new Scanner(txtInput);
            while (lineReader.hasNextLine()) {
                String line = lineReader.nextLine();
                line = line.replaceAll("\\s", ""); // enlever les whitespace
                if (line.charAt(0) != '#') {   // skip comments
                    parseLine(line, getCarteInput());
                }
            }
            lineReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Erreur durant la lecture du fichier d'entrée.");
            e.printStackTrace();
        }
        return getCarteInput();
    }

    public void parseLine(String line, CarteAuTresor carteInput) {
        try {
            switch (line.charAt(0)) {
                case 'C' -> carteInput.createMap(line);
                case 'M' -> carteInput.addMontagne(line);
                case 'T' -> carteInput.addTresor(line);
                case 'A' -> carteInput.addAventurier(line);
            }
        } catch (WrongCarteInputException e) {
            e.printStackTrace();
        }
    }


}
