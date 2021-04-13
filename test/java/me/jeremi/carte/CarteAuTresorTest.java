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
    @DisplayName("Adding a Montagne should give it the right position")
    void addMontagneShouldBeRight() {
        carte.addMontagne("M-1-0");
        carte.addMontagne("M-2-1");
        assertEquals("M", carte.getCarte().get(1).get(2), "Carte should display an M at position (1,0)");
        assertEquals("M", carte.getCarte().get(0).get(1), "Carte should display an M at position (2,1)");
        assertEquals(1,carte.getMontagnes().get(0).getX(), "The first mountain in the list should have x = 1");
        assertEquals(0,carte.getMontagnes().get(0).getY(), "The first mountain in the list should have y = 0");
        assertEquals(2,carte.getMontagnes().get(1).getX(), "The second mountain in the list should have x = 2");
        assertEquals(1,carte.getMontagnes().get(1).getY(), "The second mountain in the list should have y = 1");
    }

    @Test
    @DisplayName("Adding a Montagne out of bounds should throw an IllegalArgumentException")
    void addMontagneOutOfBoundsShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> carte.addMontagne("M-8-7"));
    }

    @Test
    @DisplayName("Adding a Tresor should give it the right position and amount")
    void addTresorShouldBeRight() {
        carte.addTresor("T-0-3-2");
        carte.addTresor("T-1-3-3");

        // Carte display
        assertEquals("T (2)", carte.getCarte().get(3).get(0),
                "Carte should display a T ({amount}) at position (0,3)");
        assertEquals("T (3)", carte.getCarte().get(3).get(1),
                "Carte should display a T ({amount}) at position (2,1)");

        // First Tresor should be added to list tresors
        assertEquals(0,carte.getTresors().get(0).getX(),
                "The first treasure in the list should have x = 1");
        assertEquals(3,carte.getTresors().get(0).getY(),
                "The first treasure in the list should have y = 0");
        assertEquals(2, carte.getTresors().get(0).getTresor(),
                "The first treasure in the list should have 2 treasures");

        // Second Tresor should be added to list tresors
        assertEquals(1,carte.getTresors().get(1).getX(),
                "The second treasure in the list should have x = 2");
        assertEquals(3,carte.getTresors().get(1).getY(),
                "The second treasure in the list should have y = 1");
        assertEquals(3, carte.getTresors().get(1).getTresor(),
                "The second treasure in the list should have 3 treasures");
    }

    @Test
    @DisplayName("Adding a Tresor with illegal parameters should throw an IllegalArgumentException")
    void addIllegalTresorShouldThrowException() {
        // Out of bounds
        assertThrows(IllegalArgumentException.class, () -> carte.addTresor("T-8-7-2"),
                "Adding a Tresor out of bounds should throw an IllegalArgumentException");

        // Illegal amount of treasure
        assertThrows(IllegalArgumentException.class, () -> carte.addTresor("T-0-3-0"),
                "Adding a Tresor with no amount of treasure should throw an IllegalArgumentException");
    }

    @Test
    @DisplayName("Adding an Aventurier should give it the right parameters")
    void addAventurierShouldBeRight() {
        carte.addAventurier("A-Lara-1-1-S-AAGGA");
        // Carte display
        assertEquals("A (Lara)", carte.getCarte().get(1).get(1),
                "Carte should display an A ({name}) at position (1,1)");

        // Aventurier should be added to list aventuriers
        assertEquals("Lara", carte.getAventuriers().get(0).getNom());
        assertEquals(1, carte.getAventuriers().get(0).getX());
        assertEquals(1, carte.getAventuriers().get(0).getY());
        assertEquals(Direction.SUD, carte.getAventuriers().get(0).getOrientation());
        assertEquals("AAGGA", carte.getAventuriers().get(0).getCheminRestant());
    }

    @Test
    @DisplayName("Adding an Aventurier with illegal parameters should throw an IllegalArgumentException")
    void addIllegalAventurierShouldThrowException() {
        // Out of bounds
        assertThrows(IllegalArgumentException.class, () -> carte.addAventurier("A-Lara-9-3-S-AAGGA"),
                "Adding an Aventurier out of bounds should throw an IllegalArgumentException");

        // Unsupported orientation
        assertThrows(IllegalArgumentException.class, () -> carte.addAventurier("A-Lara-1-3-J-AAGGA"),
                "Adding an Aventurier with an unsupported orientation should throw an IllegalArgumentException");
    }

    @Test
    @DisplayName("Moving all Aventuriers should execute all turns right")
    void mouvementAventuriersShouldWork() {
        carte.addAventurier("A-Lara-0-1-E-AGA");
        carte.addAventurier("A-Keanu-0-2-E-AADA");

        carte.mouvementAventuriers();

        // Carte display
        assertEquals("A (Lara)", carte.getCarte().get(0).get(1),
                "Carte should display an A (Lara) at position (1,0)");
        assertEquals("A (Keanu)", carte.getCarte().get(3).get(2),
                "Carte should display an A (Keanu) at position (2,3)");

        // First Aventurier's position and orientation (1,0,N) should update correctly
        assertEquals(1, carte.getAventuriers().get(0).getX());
        assertEquals(0, carte.getAventuriers().get(0).getY());
        assertEquals(Direction.NORD, carte.getAventuriers().get(0).getOrientation());

        // Second Aventurier's position and orientation (2,3,S) should update correctly
        assertEquals(2, carte.getAventuriers().get(1).getX());
        assertEquals(3, carte.getAventuriers().get(1).getY());
        assertEquals(Direction.SUD, carte.getAventuriers().get(1).getOrientation());
    }

    @Test
    @DisplayName("Moving an Aventurier into a Montagne should not move them")
    void mouvementAventuriersMontagneShouldStop() {
        carte.addMontagne("M-1-2");
        carte.addAventurier("A-Climber-1-1-S-A");

        carte.mouvementAventuriers();

        // Aventurier shouldn't have moved (1,1,S)
        assertEquals(1, carte.getAventuriers().get(0).getX());
        assertEquals(1, carte.getAventuriers().get(0).getY());
        assertEquals(Direction.SUD, carte.getAventuriers().get(0).getOrientation());

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

        carte.addMontagne("M-1-0");
        carte.addMontagne("M-2-1");
        carte.addTresor("T-0-3-2");
        carte.addTresor("T-1-3-3");
        carte.addAventurier("A-Lara-1-1-S-AADADAGGA");

        assertEquals("""
                -        M        -       \040
                -        A (Lara) M       \040
                -        -        -       \040
                T (2)    T (3)    -       \040
                """, carte.toString(),
                "toString should update the space between each box when adding a long name");


        carte.addAventurier("A-Indiana-1-2-S-AADADA");

        assertEquals("""
                -           M           -          \040
                -           A (Lara)    M          \040
                -           A (Indiana) -          \040
                T (2)       T (3)       -          \040
                """, carte.toString(),
                "toString should update the space between each box when adding a long name");

        carte.mouvementAventuriers();
    }
}