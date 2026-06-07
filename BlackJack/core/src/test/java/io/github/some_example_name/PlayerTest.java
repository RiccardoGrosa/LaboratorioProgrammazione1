package io.github.some_example_name;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Sottoclasse di supporto per testare la classe astratta
class TestPlayer extends Player {
    public TestPlayer() {
        super();
    }
}

public class PlayerTest {

    @Test
    public void testRiceviCartaEMano() {
        Player player = new TestPlayer();
        assertEquals(0, player.getMano().size());

        player.riceviCarta(new Carta("5", "clubs"));
        assertEquals(1, player.getMano().size());
    }

    @Test
    public void testCalcoloPunteggioNormaleEFigure() {
        Player player = new TestPlayer();
        player.riceviCarta(new Carta("2", "spades"));
        player.riceviCarta(new Carta("jack", "hearts")); // Jack vale 10

        assertEquals(12, player.getPunteggio());
    }

    @Test
    public void testAssoValeUndici() {
        Player player = new TestPlayer();
        player.riceviCarta(new Carta("ace", "diamonds")); // Vale 11
        player.riceviCarta(new Carta("9", "clubs"));

        assertEquals(20, player.getPunteggio());
        assertFalse(player.isSballato());
    }

    @Test
    public void testAssoFletteAUnoPerEvitareSballo() {
        Player player = new TestPlayer();
        player.riceviCarta(new Carta("king", "hearts"));  // 10
        player.riceviCarta(new Carta("10", "spades"));   // 10 -> Totale 20
        player.riceviCarta(new Carta("ace", "clubs"));    // L'asso deve scalare a 1 anziché 11

        assertEquals(21, player.getPunteggio());
        assertFalse(player.isSballato());
    }

    @Test
    public void testGestioneSballato() {
        Player player = new TestPlayer();
        player.riceviCarta(new Carta("10", "hearts"));
        player.riceviCarta(new Carta("king", "diamonds"));
        player.riceviCarta(new Carta("5", "spades")); // 10 + 10 + 5 = 25

        assertTrue(player.isSballato());
    }

    @Test
    public void testIsBlackjack() {
        Player player = new TestPlayer();
        player.riceviCarta(new Carta("ace", "hearts"));
        player.riceviCarta(new Carta("queen", "clubs"));

        assertTrue(player.isBlackjack());
    }
}
