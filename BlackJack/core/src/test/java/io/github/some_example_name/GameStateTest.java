package io.github.some_example_name;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameStateTest {

    @Test
    public void testStatiPrincipaliEsistono() {
        assertNotNull(GameState.valueOf("HOME"));
        assertNotNull(GameState.valueOf("PUNTATA"));
        assertNotNull(GameState.valueOf("SCELTA_USER"));
        assertNotNull(GameState.valueOf("VINCE_USER"));
        assertNotNull(GameState.valueOf("VINCE_BANCO"));
    }
}
