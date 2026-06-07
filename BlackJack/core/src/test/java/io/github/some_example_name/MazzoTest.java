package io.github.some_example_name;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MazzoTest {

    @Test
    public void testInizializzazioneMazzoCompleto() {
        Mazzo mazzo = new Mazzo();
        int contatoreCarte = 0;

        // Un mazzo da poker standard ha esattamente 52 carte
        for (int i = 0; i < 52; i++) {
            Carta c = mazzo.pescaCarta();
            assertNotNull(c, "La carta non dovrebbe essere nulla");
            contatoreCarte++;
        }
        assertEquals(52, contatoreCarte);
    }

    @Test
    public void testAutorigenerazioneMazzoFinito() {
        Mazzo mazzo = new Mazzo();

        // Svuotiamo completamente il mazzo pescando 52 carte
        for (int i = 0; i < 52; i++) {
            mazzo.pescaCarta();
        }

        // La 53esima carta forzerà l'IndexOutOfBoundsException.
        // Il blocco catch deve rigenerare il mazzo e manderà una nuova carta senza crashare.
        Carta cartaExtra = mazzo.pescaCarta();
        assertNotNull(cartaExtra, "Il mazzo avrebbe dovuto rigenerarsi da solo");
    }
}
