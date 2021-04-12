package me.jeremi.carte;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TresorTest {

    @Test
    @DisplayName("Decrementing should reduce treasure could by exactly 1")
    void decrementingShouldWork() {
        Tresor tresor = new Tresor(4, 2, 3);
        tresor.decrement();
        assertEquals(3, tresor.getTresor(), "Tresor should be 3 after 1 decrement from 4");
        tresor.decrement();
        assertEquals(2, tresor.getTresor(), "Tresor should be 2 after 2 decrements from 4");
        tresor.decrement();
        assertEquals(1, tresor.getTresor(), "Tresor should be 1 after 3 decrements from 4");
        tresor.decrement();
        assertEquals(0, tresor.getTresor(), "Tresor should be 0 after 4 decrements from 4");
    }

    @Test
    @DisplayName("Decrementing should stop changing when it reaches 0")
    void decrementingShouldCapAtZero() {
        Tresor tresor = new Tresor(0, 2, 3);
        tresor.decrement();
        assertEquals(0, tresor.getTresor(), "Tresor should be 0 after decrementing from 0");
    }
}