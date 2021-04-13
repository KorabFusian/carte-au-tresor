package me.jeremi.carte;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour la classe CarteAuTresor.
 * 
 * @author Jeremi Friggit (@KorabFusian)
 * @see CarteAuTresor
 */
class CarteAuTresorTest {

    CarteAuTresor carte;

    @BeforeEach
    void setUp() {
        carte = new CarteAuTresor();
        carte.createMap("C-3-4");
    }

    // region Creation and filling Carte tests
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
        assertEquals(1, carte.getMontagnes().get(0).getX(), "The first mountain in the list should have x = 1");
        assertEquals(0, carte.getMontagnes().get(0).getY(), "The first mountain in the list should have y = 0");
        assertEquals(2, carte.getMontagnes().get(1).getX(), "The second mountain in the list should have x = 2");
        assertEquals(1, carte.getMontagnes().get(1).getY(), "The second mountain in the list should have y = 1");
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
        assertEquals("T (2)", carte.getCarte().get(3).get(0), "Carte should display a T ({amount}) at position (0,3)");
        assertEquals("T (3)", carte.getCarte().get(3).get(1), "Carte should display a T ({amount}) at position (2,1)");

        // First Tresor should be added to list tresors
        assertEquals(0, carte.getTresors().get(0).getX(), "The first treasure in the list should have x = 1");
        assertEquals(3, carte.getTresors().get(0).getY(), "The first treasure in the list should have y = 0");
        assertEquals(2, carte.getTresors().get(0).getTresor(),
                "The first treasure in the list should have 2 treasures");

        // Second Tresor should be added to list tresors
        assertEquals(1, carte.getTresors().get(1).getX(), "The second treasure in the list should have x = 2");
        assertEquals(3, carte.getTresors().get(1).getY(), "The second treasure in the list should have y = 1");
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
    @DisplayName("Adding an item on an empty map should throw  an IllegalArgumentException")
    void addOnNoCarte() {
        CarteAuTresor empty = new CarteAuTresor();

        // Shouldn't be able to add any element
        assertThrows(IllegalArgumentException.class, () -> empty.addAventurier("A-Perdu-0-0-S-GAGA"));
        assertThrows(IllegalArgumentException.class, () -> empty.addTresor("T-0-0-1"));
        assertThrows(IllegalArgumentException.class, () -> empty.addMontagne("M-0-0"));
    }

    @Test
    @DisplayName("Adding an item over another should throw an IllegalArgumentException")
    void addOverSomethingShouldThrowException() {

        // Real items
        carte.addAventurier("A-Ronflex-0-0-S-GDGDGD");
        carte.addTresor("T-0-1-1");
        carte.addMontagne("M-0-2");

        // Adding an Aventurier over these shouldn't work
        assertThrows(IllegalArgumentException.class, () -> carte.addAventurier("A-Exclu-0-0-S-AAGGA"));
        assertThrows(IllegalArgumentException.class, () -> carte.addAventurier("A-Exclu-0-1-S-AAGGA"));
        assertThrows(IllegalArgumentException.class, () -> carte.addAventurier("A-Exclu-0-2-S-AAGGA"));

        // Adding a Tresor over them shouldn't work
        assertThrows(IllegalArgumentException.class, () -> carte.addTresor("T-0-0-1"));
        assertThrows(IllegalArgumentException.class, () -> carte.addTresor("T-0-1-1"));
        assertThrows(IllegalArgumentException.class, () -> carte.addTresor("T-0-2-1"));

        // Adding a Montagne over them shouldn't work
        assertThrows(IllegalArgumentException.class, () -> carte.addMontagne("M-0-0"));
        assertThrows(IllegalArgumentException.class, () -> carte.addMontagne("M-0-1"));
        assertThrows(IllegalArgumentException.class, () -> carte.addMontagne("M-0-2"));
    }
    // endregion

    // region Movement tests
    @Test
    @DisplayName("Moving all Aventuriers should execute all turns correctly")
    void mouvementAventuriersShouldWork() {
        carte.addAventurier("A-Lara-0-1-E-AGA");
        carte.addAventurier("A-Keanu-0-2-E-AADA");

        carte.mouvementAventuriers();

        // Carte display
        assertEquals("A (Lara)", carte.getCarte().get(0).get(1), "Carte should display an A (Lara) at position (1,0)");
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
    @DisplayName("Moving an Aventurier into a Montagne should stop them")
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
    @DisplayName("Moving an Aventurier out of bounds should stop them")
    void mouvementAventuriersBoundaryShouldStop() {
        carte.addAventurier("A-Climber-2-3-S-AGA");

        carte.mouvementAventuriers();

        // Aventurier should only have turned left (1,1,E)
        assertEquals(2, carte.getAventuriers().get(0).getX());
        assertEquals(3, carte.getAventuriers().get(0).getY());
        assertEquals(Direction.EST, carte.getAventuriers().get(0).getOrientation());

    }

    @Test
    @DisplayName("A conflict in the movement of Aventuriers should prioritize the first one created")
    void mouvementAventuriersConflictShouldPrioritizeFirst() {
        carte.addAventurier("A-Usain-1-1-S-A");
        carte.addAventurier("A-Ramoloss-0-2-E-A");

        carte.mouvementAventuriers();

        // Usain should have moved (1,2,S)
        assertEquals(1, carte.getAventuriers().get(0).getX());
        assertEquals(2, carte.getAventuriers().get(0).getY());
        assertEquals(Direction.SUD, carte.getAventuriers().get(0).getOrientation());

        // Ramoloss should not have moved (0,2,E)
        assertEquals(0, carte.getAventuriers().get(1).getX());
        assertEquals(2, carte.getAventuriers().get(1).getY());
        assertEquals(Direction.EST, carte.getAventuriers().get(1).getOrientation());

    }

    @Test
    @DisplayName("Moving with no Aventuriers should do nothing")
    void mouvementNothingShouldDoNothing() {
        CarteAuTresor secondCarte = new CarteAuTresor();
        secondCarte.createMap("C-3-4");
        carte.mouvementAventuriers();
        assertEquals(secondCarte.toString(), carte.toString());
    }
    // endregion

    // region Util tests
    @Test
    @DisplayName("toString should return the correct result")
    void toStringShouldWork() {
        assertEquals("""
                - - -\s
                - - -\s
                - - -\s
                - - -\s
                """, carte.toString());

        carte.addMontagne("M-1-0");
        carte.addMontagne("M-2-1");
        carte.addTresor("T-0-3-2");
        carte.addTresor("T-1-3-3");
        carte.addAventurier("A-Lara-1-1-S-AADADAGGA");

        assertEquals("""
                -        M        -       \s
                -        A (Lara) M       \s
                -        -        -       \s
                T (2)    T (3)    -       \s
                """, carte.toString(), "toString should update the space between each box when adding a long name");

        carte.addAventurier("A-Indiana-1-2-S-AADADA");

        assertEquals("""
                -           M           -          \s
                -           A (Lara)    M          \s
                -           A (Indiana) -          \s
                T (2)       T (3)       -          \s
                """, carte.toString(), "toString should update the space between each box when adding a long name");

        carte.mouvementAventuriers();

        assertEquals("""
                -           M           -          \s
                A (Lara)    -           M          \s
                A (Indiana) -           -          \s
                T (1)       T (2)       -          \s
                """, carte.toString(),
                "toString should have the right elements in the right place after moving Aventuriers");
    }
    // endregion
}