package me.jeremi.file;

import me.jeremi.carte.CarteAuTresor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour la classe OutputWriter.
 *
 * @author Jeremi Friggit (@KorabFusian)
 * @see OutputWriter
 */
class OutputWriterTest {

    CarteAuTresor carte;
    OutputWriter outputWriter;
    String path = "test/resources/output/";

    @BeforeEach
    void setUp() {
        InputParser inputParser = new InputParser();
        carte = inputParser.parseFile("test/resources/input/exemple");
        outputWriter = new OutputWriter();
    }

    @Test
    @DisplayName("CarteAuTresor's description should be generated correctly")
    void shouldGetCorrectDescriptions() {
        assertEquals("""
                C - 3 - 4
                M - 1 - 0
                M - 2 - 1
                T - 0 - 3 - 2
                T - 1 - 3 - 3
                A - Lara - 1 - 1 - S - 0""", outputWriter.describeCarteAuTresor((carte)));
        carte.mouvementAventuriers();
        assertEquals("""
                C - 3 - 4
                M - 1 - 0
                M - 2 - 1
                T - 1 - 3 - 2
                A - Lara - 0 - 3 - S - 3""",outputWriter.describeCarteAuTresor(carte));
    }

    @Test
    @DisplayName("Writing to a file that doesn't exist should create it")
    void writeCarteToFileShouldCreateFileIfNotExists() {
        File file = new File(path + "out.txt");
        boolean deleted = file.delete();
        outputWriter.writeCarteToFile(carte, path + "out.txt");
        assertTrue(file.exists());
    }

    @Test
    @DisplayName("Writing a CarteAuTresor to a file should work correctly")
    void writeCarteToFileShouldWork() {
        outputWriter.writeCarteToFile(carte, path + "out.txt");
        StringBuilder str = new StringBuilder();

        // Lire le fichier qui vient d'être écrit
        try {
            File file = new File(path + "out.txt");
            Scanner lineReader = new Scanner(file);

            while (lineReader.hasNextLine()) {
                str.append(lineReader.nextLine()).append("\n"); // On a un \n en trop à la fin, pas grave
            }
            lineReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Fichier d'entrée introuvable.");
            e.printStackTrace();
        }
        assertEquals("""
                C - 3 - 4
                M - 1 - 0
                M - 2 - 1
                T - 0 - 3 - 2
                T - 1 - 3 - 3
                A - Lara - 1 - 1 - S - 0
                """, str.toString());
    }

    @Test
    @DisplayName("Writing to a file without specifying the .txt extension should work correctly")
    void writeCarteToFileNoTxtShouldWork() {
        outputWriter.writeCarteToFile(carte, path + "out");

        StringBuilder str = new StringBuilder();

        // Lire le fichier
        try {
            File file = new File(path + "out.txt");
            Scanner lineReader = new Scanner(file);

            while (lineReader.hasNextLine()) {
                str.append(lineReader.nextLine()).append("\n"); // pareil, \n en trop à la fin, pas grave
            }
            lineReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Fichier d'entrée introuvable.");
            e.printStackTrace();
        }
        assertEquals("""
                C - 3 - 4
                M - 1 - 0
                M - 2 - 1
                T - 0 - 3 - 2
                T - 1 - 3 - 3
                A - Lara - 1 - 1 - S - 0
                """, str.toString());
    }
}