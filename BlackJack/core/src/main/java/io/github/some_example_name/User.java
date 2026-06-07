package io.github.some_example_name;

/**
 * Rappresenta l'utente giocatore umano, tracciandone il saldo e la puntata attuale.
 * @author Riccardo Grosa
 * @version 05.06.2026
 */
public class User extends Player{
    private int puntata;
    private int soldi;

    /**
     * Crea un utente impostando il saldo iniziale di fiches e azzerando la puntata.
     * @param soldi il saldo iniziale di fiches dell'utente
     */
    public User(int soldi) {
        super();
        this.puntata = 0;
        this.soldi = soldi;
    }

    /**
     * Restituisce il saldo attuale delle fiches dell'utente.
     * @return il quantitativo di fiches disponibili
     */
    public int getSoldi() {
        return  soldi;
    }

    /**
     * Imposta il saldo delle fiches dell'utente.
     * @param soldi il nuovo ammontare di fiches da assegnare
     */
    public void setSoldi(int soldi) {
        this.soldi = soldi;
    }

    /**
     * Restituisce il valore della puntata scommessa per la mano in corso.
     * @return l'importo puntato
     */
    public int getPuntata() {
        return puntata;
    }

    /**
     * Imposta il valore della puntata per la mano in corso.
     * @param puntata l'importo da scommettere
     */
    public void setPuntata(int puntata) {
        this.puntata = puntata;
    }
}
