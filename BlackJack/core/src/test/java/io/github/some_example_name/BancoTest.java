package io.github.some_example_name;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BancoTest {

    @Test
    public void testInizializzazioneBanco() {
        Banco banco = new Banco();

        assertNotNull(banco.getMano());
        assertEquals(0, banco.getMano().size());
        assertEquals(0, banco.getPunteggio());
        assertFalse(banco.isSballato());
    }
}
