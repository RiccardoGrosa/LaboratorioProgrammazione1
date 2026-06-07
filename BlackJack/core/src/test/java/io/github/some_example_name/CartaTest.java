package io.github.some_example_name;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CartaTest {

    @Test
    public void testCostruttoreEGetters() {
        Carta carta = new Carta("ace", "spades");

        assertEquals("ace", carta.getValore());
        assertEquals("spades", carta.getSeme());
    }

    @Test
    public void testFormatta() {
        Carta carta = new Carta("10", "hearts");
        assertEquals("10_of_hearts.png", carta.formatta());
    }

    @Test
    public void testToString() {
        Carta carta = new Carta("king", "diamonds");
        assertEquals("king di diamonds", carta.toString());
    }

    @Test
    public void testSetters() {
        Carta carta = new Carta("2", "clubs");
        carta.setValore("jack");
        carta.setSeme("hearts");

        assertEquals("jack", carta.getValore());
        assertEquals("hearts", carta.getSeme());
    }
}
