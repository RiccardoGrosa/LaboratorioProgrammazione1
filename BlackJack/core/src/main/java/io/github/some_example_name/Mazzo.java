package io.github.some_example_name;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Gestisce un mazzo di 52 carte da gioco, permettendo l'inizializzazione e il pescaggio.
 * @author Riccardo Grosa
 * @version 05.06.2026
 */
public class Mazzo {
    private ArrayList<Carta> mazzo = new ArrayList<>();

    // i 2 Array sono generati dalla AI
    private static final ArrayList<String> VALORI = new ArrayList<>();
    static {                //serve a farlo una volta all'avvio del programma
        VALORI.add("ace");
        VALORI.add("2");
        VALORI.add("3");
        VALORI.add("4");
        VALORI.add("5");
        VALORI.add("6");
        VALORI.add("7");
        VALORI.add("8");
        VALORI.add("9");
        VALORI.add("10");
        VALORI.add("jack");
        VALORI.add("queen");
        VALORI.add("king");
    }

    private static final ArrayList<String> SEMI = new ArrayList<>();
    static {
        SEMI.add("clubs");
        SEMI.add("spades");
        SEMI.add("hearts");
        SEMI.add("diamonds");
    }

    /**
     * Crea un mazzo completo combinando tutti i valori e i semi, per poi mescolarlo.
     */
    public Mazzo() {
        for (String valore : VALORI){
            for (String seme : SEMI){
                Carta c = new Carta(valore, seme);
                mazzo.add(c);
            }
        }
        Collections.shuffle(mazzo);     //Metodo dato da AI
    }

    /**
     * Rimuove e restituisce la prima carta del mazzo, rigenerandolo se si esaurisce.
     * @return la carta pescata dal mazzo
     */
    public Carta pescaCarta() {
        try {
            return mazzo.remove(0);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Mazzo finito! Ricreo il mazzo...");
            for (String valore : VALORI) {
                for (String seme : SEMI) {
                    mazzo.add(new Carta(valore, seme));
                }
            }
            Collections.shuffle(mazzo);
            return mazzo.remove(0);
        }
    }
}
