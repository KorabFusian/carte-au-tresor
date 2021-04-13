package me.jeremi.file;

import me.jeremi.carte.CarteAuTresor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

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
        carte.mouvementAventuriers();
        assertEquals("""
                C - 3 - 4
                M - 1 - 0
                M - 2 - 1
                T - 1 - 3 - 2
                A - Lara - 0 - 3 - S - 3""",outputWriter.getCarteDescription(carte));
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
        File file = new File(path + "out.txt");
        outputWriter.writeCarteToFile(carte, path + "out.txt");
    }

    @Test
    @DisplayName("Writing to a file without specifying the .txt extension should work correctly")
    void writeCarteToFileNoTxtShouldWork() {
        outputWriter.writeCarteToFile(carte, path + "out");
    }
}