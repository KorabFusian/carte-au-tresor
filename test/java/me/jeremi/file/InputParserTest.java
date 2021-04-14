package me.jeremi.file;

import me.jeremi.carte.CarteAuTresor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour la classe InputParser.
 * 
 * @author Jeremi Friggit (@KorabFusian)
 * @see InputParser
 */
class InputParserTest {

    InputParser inputParser;
    String path = "test/resources/input/";

    @BeforeEach
    void setUp() {
        inputParser = new InputParser();
    }

    @Test
    @DisplayName("Parsing a line should act out the corresponding action")
    void parseLineShouldWork() {
        CarteAuTresor carte = new CarteAuTresor();
        inputParser.parseLine("C-5-7", carte);
        inputParser.parseLine("M-2-4", carte);
        inputParser.parseLine("T-4-3-3", carte);
        inputParser.parseLine("A-Fraise-2-1-S-TAGADA", carte);

        // Carte is created with the right proportions
        assertEquals(7, carte.getCarte().size());
        assertEquals(5, carte.getCarte().get(0).size());

        // All objects are created, no list is empty
        assertFalse(carte.getMontagnes().isEmpty());
        assertFalse(carte.getAventuriers().isEmpty());
        assertFalse(carte.getTresors().isEmpty());
    }

    @Test
    @DisplayName("Parsing a file should create the corresponding CarteAuTresor")
    void parseFileShouldWork() {
        CarteAuTresor carte = inputParser.parseFile(path + "exemple.txt");

        // Carte is created right
        assertEquals(3, carte.getCarte().get(0).size(), "Carte X size should be 3");
        assertEquals(4, carte.getCarte().size(), "Carte Y size should be 4");

        // Objects are created right
        assertEquals("M - 1 - 0", carte.getMontagnes().get(0).toString(), "Montagne 1's toString should be right");
        assertEquals("M - 2 - 1", carte.getMontagnes().get(1).toString(), "Montagne 1's toString should be right");
        assertEquals("T - 0 - 3 - 2", carte.getTresors().get(0).toString(), "Tresor 1's toString should be right");
        assertEquals("T - 1 - 3 - 3", carte.getTresors().get(1).toString(), "Tresor 2's toString should be right");
        assertEquals("A - Lara - 1 - 1 - S - 0", carte.getAventuriers().get(0).toString(),
                "Aventurier's toString should be right");
    }

    @Test
    @DisplayName("Parsing a file should add .txt to the pathname if it is not the case")
    void parseFileShouldOpenWithTxt() {
        CarteAuTresor carte = inputParser.parseFile(path + "exemple");

        // Carte is created right
        assertEquals(3, carte.getCarte().get(0).size(), "Carte X size should be 3");
        assertEquals(4, carte.getCarte().size(), "Carte Y size should be 4");
    }

    @Test
    @DisplayName("Parsing a file with spaces in front of lines should still work")
    void parseFileWithSpacesShouldWork() {
        CarteAuTresor carte = inputParser.parseFile(path + "random-spaces.txt");

        // Carte is created right
        assertEquals(3, carte.getCarte().get(0).size(), "Carte X size should be 3");
        assertEquals(4, carte.getCarte().size(), "Carte Y size should be 4");

        // Objects are created right
        assertEquals("M - 1 - 0", carte.getMontagnes().get(0).toString(), "Montagne 1's toString should be right");
        assertEquals("M - 2 - 1", carte.getMontagnes().get(1).toString(), "Montagne 1's toString should be right");
        assertEquals("T - 0 - 3 - 2", carte.getTresors().get(0).toString(), "Tresor 1's toString should be right");
        assertEquals("T - 1 - 3 - 3", carte.getTresors().get(1).toString(), "Tresor 2's toString should be right");
        assertEquals("A - Lara - 1 - 1 - S - 0", carte.getAventuriers().get(0).toString(),
                "Aventurier's toString should be right");
    }

    @Test
    @DisplayName("Parsing a file with nothing or only comments should give a newly initialized, empty CarteAuTresor")
    void parseCommentFileShouldReturnEmpty() {
        CarteAuTresor carte = inputParser.parseFile(path + "comments-only.txt");

        // All lists should be empty
        assertEquals(Collections.EMPTY_LIST, carte.getCarte());
        assertEquals(Collections.EMPTY_LIST, carte.getMontagnes());
        assertEquals(Collections.EMPTY_LIST, carte.getTresors());
        assertEquals(Collections.EMPTY_LIST, carte.getAventuriers());
    }
}