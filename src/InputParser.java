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

    public void parseFile (String filename) {
        try {
            File txtInput = new File(filename);
            Scanner lineReader = new Scanner(txtInput);
            while (lineReader.hasNextLine()) {
                String data = lineReader.nextLine();
                data = data.replaceAll("\\s",""); //remove whitespace
                if(data.charAt(0) != '#')
                    System.out.println(data);
            }
            lineReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
