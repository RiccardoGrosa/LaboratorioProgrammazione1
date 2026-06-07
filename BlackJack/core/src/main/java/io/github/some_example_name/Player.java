package io.github.some_example_name;

import java.util.ArrayList;

/**
 * Classe astratta base che definisce le caratteristiche e le azioni comuni di un giocatore.
 * @author Riccardo Grosa
 * @version 05.06.2026
 */
public abstract class Player {
    private ArrayList<Carta> mano;

    /**
     * Inizializza una nuova mano vuota e resetta lo stato del giocatore.
     */
    public Player() {
        mano = new ArrayList<>();
    }

    /**
     * Aggiunge una carta alla mano corrente del giocatore.
     * @param carta la carta da aggiungere alla mano
     */
    public void riceviCarta(Carta carta) {
        mano.add(carta);
    }

    /**
     * Controlla se il giocatore ha fatto Blackjack (21 punti con le prime due carte).
     * @return {@code true} se ha fatto Blackjack, {@code false} altrimenti
     */
    public boolean isBlackjack() {
        return mano.size() == 2 && getPunteggio() == 21;
    }

    /**
     * Verifica se il punteggio complessivo supera la soglia di 21.
     * @return {@code true} se il punteggio è maggiore di 21, {@code false} altrimenti
     */
    public boolean isSballato() {
        if (getPunteggio() > 21){
            return true;
        }
        return false;
    }


    //metodo che fa tutto perchè inizialmente avevo
    //messo la variabile punteggio ma on si riusciva
    //a implementare l'asso variabile
    /**
     * Calcola il punteggio della mano, ottimizzando il valore dell'Asso (1 o 11) per evitare di sballare.
     * @return il punteggio totale calcolato
     */
    public int getPunteggio() {
        int totale = 0;
        int assi = 0;

        for (Carta c : mano) {
            String v = c.getValore();

            switch (v) {
                case "ace":
                    totale += 11;
                    assi++;
                    break;

                case "jack":
                case "queen":
                case "king":
                    totale += 10;
                    break;

                default:
                    try {
                        totale += Integer.parseInt(v);
                    } catch (NumberFormatException e) {
                        System.err.println("ERRORE: Valore carta non riconosciuto: " + v + ". Applico valore default 10...");
                        totale += 10;
                    }
                    break;
            }
        }

        while (totale > 21 && assi > 0) {
            totale -= 10;
            assi--;
        }

        return totale;
    }

    /**
     * Restituisce l'elenco delle carte presenti nella mano del giocatore.
     * @return la lista delle carte in mano
     */
    public ArrayList<Carta> getMano() {
        return mano;
    }

}
