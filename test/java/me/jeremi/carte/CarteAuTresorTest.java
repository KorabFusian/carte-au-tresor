package me.jeremi.carte;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarteAuTresorTest {

    CarteAuTresor carte;


    @BeforeEach
    void setUp() {
         carte = new CarteAuTresor();
         carte.createMap("C-3-4");
    }

    @Test
    @DisplayName("toString override should return the right result")
    // Vérifie aussi la bonne création de la carte. Pratique !
    void toStringShouldWork() {
        assertEquals("""
                - - -\040
                - - -\040
                - - -\040
                - - -\040
                """, carte.toString());
    }

    @Test
    @DisplayName("Creating two maps should throw an IllegalArgumentException")
    void createMapTwiceShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> carte.createMap("C-2-3"));
    }

    @Test
    @DisplayName("Creating a map with illegal dimensions should throw an IllegalArgumentException")
    void createMapWithWrongSizeShouldThrowException() {
        CarteAuTresor zeroX = new CarteAuTresor();
        CarteAuTresor zeroY = new CarteAuTresor();
        assertThrows(IllegalArgumentException.class, () -> zeroX.createMap("C-0-4"));
        assertThrows(IllegalArgumentException.class, () -> zeroY.createMap("C-3-0"));
    }

    @Test
    void addMontagne() {
    }

    @Test
    void addTresor() {
    }

    @Test
    void addAventurier() {
    }

    @Test
    void mouvementAventuriers() {
    }
}