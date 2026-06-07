package io.github.some_example_name;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testInizializzazioneSoldiEPuntata() {
        User user = new User(150);

        assertEquals(150, user.getSoldi());
        assertEquals(0, user.getPuntata());
    }

    @Test
    public void testModificaSoldiEPuntata() {
        User user = new User(100);

        user.setPuntata(25);
        user.setSoldi(user.getSoldi() - user.getPuntata());

        assertEquals(25, user.getPuntata());
        assertEquals(75, user.getSoldi());
    }
}
