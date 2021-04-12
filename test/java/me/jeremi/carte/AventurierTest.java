package me.jeremi.carte;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class AventurierTest {

    Aventurier aventurier;

    @BeforeEach
    void setUp() {
        aventurier = new Aventurier("Lara", 2, 3, Direction.NORD, "AAGADA");
    }

    @Test
    @DisplayName("Chemin should remove wrong characters when constructed")
    void cheminShouldRemoveWhitespacesOnConstruct() {
        aventurier = new Aventurier("Lara", 2, 3, Direction.NORD, "AB ffa iojK,AGA DA A");
        assertEquals("AAGADAA", aventurier.getCheminRestant(), "Wrong characters should be removed on chemin when constructing");
    }

    @Test
    @DisplayName("Chemin should remove wrong characters when set")
    void cheminShouldRemoveWhitespacesOnSet() {
        aventurier.setCheminRestant("AB ffa iojK,AGA DA A");
        assertEquals("AAGADAA", aventurier.getCheminRestant(), "Wrong characters should be removed on chemin when setting");
    }

    @Test
    @DisplayName("Pop should remove the first character of cheminRestant")
    void shouldPopCheminRestant() {

        aventurier.popCheminRestant();
        assertEquals("AGADA",aventurier.getCheminRestant(),"The first character in cheminRestant should be removed");
    }

    @Test
    @DisplayName("cheminRestant should be empty when popped at 1 char left")
    void shouldPopCheminOneToEmpty() {
        aventurier.setCheminRestant("A");
        aventurier.popCheminRestant();
        assertEquals("",aventurier.getCheminRestant(),"cheminRestant should be empty when popped at 1");

    }

    @Test
    @DisplayName("cheminRestant should stay empty when popped while empty")
    void shouldPopCheminEmptyToEmpty() {
        aventurier.setCheminRestant("");
        aventurier.popCheminRestant();
        assertEquals("",aventurier.getCheminRestant(),"cheminRestant should be empty when popped empty");

    }

    @Test
    @DisplayName("Advancing should set a new position")
    void advanceShouldSetPosition() {
        aventurier.advanceToPosition(1, 1);
        assertEquals(1, aventurier.getX(), "X position should have changed to 1");
        assertEquals(1, aventurier.getY(), "Y position should have changed to 1");
    }

    @Test
    @DisplayName("Advancing should pop cheminRestant")
    void advanceShouldPopChemin() {
        aventurier.advanceToPosition(1, 1);
        assertEquals("AGADA", aventurier.getCheminRestant());
    }

    @Test
    @DisplayName("The orientation character should be returned right")
    void orientationCharShouldBeRight() {
        assertEquals('N', aventurier.getOrientationChar(), "Orientation char should be N when orienting NORD");
        aventurier.setOrientation(Direction.EST);
        assertEquals('E', aventurier.getOrientationChar(), "Orientation char should be E when orienting EST");
        aventurier.setOrientation(Direction.OUEST);
        assertEquals('O', aventurier.getOrientationChar(), "Orientation char should be O when orienting OUEST");
        aventurier.setOrientation(Direction.SUD);
        assertEquals('S', aventurier.getOrientationChar(), "Orientation char should be S when orienting SUD");
    }

    @Test
    @DisplayName("Incrementing tresor by 1 should work")
    void incrementingTresor() {
        aventurier.incrementTresor();
        assertEquals(1, aventurier.getTresor(), "Tresor should be 1 after being incremented once");
        for (int i = 0; i < 99; i++) {
            aventurier.incrementTresor();
        }
        assertEquals(100, aventurier.getTresor(), "Tresor should be 100 after being incremented 100 times");
    }

    @Test
    @DisplayName("Turning left should bring you to the right direction")
    void tournerAGaucheShouldWork() {
        aventurier.tournerAGauche();
        assertEquals(Direction.OUEST, aventurier.getOrientation(), "Orientation should be OUEST after turning left once");
        aventurier.tournerAGauche();
        assertEquals(Direction.SUD, aventurier.getOrientation(), "Orientation should be SUD after turning left twice");
        aventurier.tournerAGauche();
        assertEquals(Direction.EST, aventurier.getOrientation(), "Orientation should be EST after turning left 3 times");
        aventurier.tournerAGauche();
        assertEquals(Direction.NORD, aventurier.getOrientation(), "Orientation should be NORD after turning left 4 times");
    }

    @Test
    @DisplayName("Turning right should bring you to the right direction")
    void tournerADroiteShouldWork() {
        aventurier.tournerADroite();
        assertEquals(Direction.EST, aventurier.getOrientation(), "Orientation should be EST after turning right once");
        aventurier.tournerADroite();
        assertEquals(Direction.SUD, aventurier.getOrientation(), "Orientation should be SUD after turning right twice");
        aventurier.tournerADroite();
        assertEquals(Direction.OUEST, aventurier.getOrientation(), "Orientation should be EST after turning right 3 times");
        aventurier.tournerADroite();
        assertEquals(Direction.NORD, aventurier.getOrientation(), "Orientation should be NORD after turning right 4 times");
    }

    @Test
    @DisplayName("toString should return the right string format")
    void testToString() {
        aventurier = new Aventurier("Lara", 2, 3, Direction.NORD, "AAGADA");
        assertEquals("A - Lara - 2 - 3 - N - 0", aventurier.toString(), "The string should be \"A - Lara - 2 - 3 - N - 0\"");
    }
}