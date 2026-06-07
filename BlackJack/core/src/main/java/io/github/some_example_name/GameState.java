package io.github.some_example_name;

/**
 * Stati di gioco per la gestione della macchina a stati del Blackjack.
 * @author Riccardo Grosa
 * @version 05.06.2026
 */
public enum GameState {
    /** Schermata principale del menu. */
    HOME,

    /** Fase di scommessa iniziale delle fiches. */
    PUNTATA,

    /** Distribuzione della prima carta al giocatore. */
    PRIMA_CARTA_USER,

    /** Distribuzione della seconda carta al giocatore. */
    SECONDA_CARTA_USER,

    /** Distribuzione della prima carta (coperta) al banco. */
    PRIMA_CARTA_BANCO,

    /** Distribuzione della seconda carta (scoperta) al banco. */
    SECONDA_CARTA_BANCO,

    /** Fase di decisione del giocatore (carta o stai). */
    SCELTA_USER,

    /** Il giocatore pesca una carta aggiuntiva. */
    CARTA_USER,

    /** Il banco pesca una carta aggiuntiva. */
    CARTA_BANCO,

    /** Fase di decisione e intelligenza artificiale del banco. */
    SCELTA_BANCO,

    /** Il giocatore supera il punteggio di 21. */
    SBALLATO_USER,

    /** Il banco supera il punteggio di 21. */
    SBALLATO_BANCO,

    /** Fase di pesca automatica del banco. */
    PESCA_BANCO,

    /** Stato di fine partita: vittoria del banco. */
    VINCE_BANCO,

    /** Stato di fine partita: vittoria del giocatore. */
    VINCE_USER,

    /** Stato di fine partita: pareggio. */
    PAREGGIO,

    /** Schermata finale di scelta tra nuova mano, restart o uscita. */
    CONTINUAZIONE_GIOCO
}
