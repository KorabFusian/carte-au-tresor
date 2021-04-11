/*
 * FileParser
 * Version 1.0 (2021-04-11)
 *
 * Ouvre un fichier .txt et le transforme en map utilisable
 *
 *
 * Created by Jeremi Friggit (KorabFusian)
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class InputParser {

    private final CarteAuTresor carteInput = new CarteAuTresor();

    public CarteAuTresor parseFile(String filename) {
        try {
            File txtInput = new File(filename);
            Scanner lineReader = new Scanner(txtInput);
            String prevLine = null;                 // pour vérifier que la ligne C est la première (pas de previous line)
            while (lineReader.hasNextLine()) {
                String line = lineReader.nextLine();
                line = line.replaceAll("\\s", "");      // remove whitespace
                if(line.charAt(0) == 'C' && prevLine != null){
                    throw new WrongInputException("Creation de carte après la première ligne");
                }
                if (line.charAt(0) != '#') {                                // skip comments
                    parseLine(line, carteInput);
                    System.out.println(line);
                    prevLine = line;
                }
            }
            lineReader.close();
        } catch (FileNotFoundException | WrongInputException e) {
            System.out.println("Erreur durant la lecture du fichier d'entrée.");
            e.printStackTrace();
        }
            return carteInput;
    }

    public void parseLine (String line, CarteAuTresor carteInput) {
        //parse a line to create the action that needs to be done
        //throw invalid line exception if not
        switch (line.charAt(0)) {
            case 'C' -> createMap(line, carteInput);
            case 'M' -> addMountain(line, carteInput);
            case 'T' -> addTreasure(line, carteInput);
        }

    }

    private void createMap (String line, CarteAuTresor carteInput) {
        String[] params = line.split("-");
        int x = Integer.parseInt(params[1]);
        int y = Integer.parseInt(params[2]);
        carteInput.create(x,y);
    }

    private void addMountain (String line, CarteAuTresor carteInput){

    }

    private void addTreasure (String line, CarteAuTresor carteInput) {

    }
}
