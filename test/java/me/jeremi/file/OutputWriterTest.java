package me.jeremi.file;

import me.jeremi.carte.CarteAuTresor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour la classe OutputWriter.
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
    }

    // createFile should create a file if it does not exist
    @Test
    @DisplayName("openFile should create a file if it does not exist")
    void openFileShouldCreateIfNotExists() {
        File file = new File(path + "1.txt");

        if(file.delete()) {
            outputWriter.openFile(path + "1.txt");
            assertTrue(file.exists());
        }
        else {
            fail("Could not delete file.");
        }
    }
}