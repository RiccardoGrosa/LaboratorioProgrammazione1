package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Rappresenta una singola carta da gioco con valore, seme e texture.
 * @author Riccardo Grosa
 * @version 05.06.2026
 */
public class Carta {
    private String valore;
    private String seme;
    private Texture tex_card;

    /**
     * Crea una carta con valore e seme specificati e ne carica la relativa texture grafica.
     * @param valore il valore nominale della carta
     * @param seme il seme della carta
     */
    public Carta(String valore, String seme) {
        this.valore = valore;
        this.seme = seme;

        //AI controllo per prevenire errrori nella classe dei test
        if (Gdx.files != null) {
            this.tex_card = new Texture("img/Cards/" + valore + "_of_" + seme + ".png");
        } else {
            this.tex_card = null;
        }
    }

    /**
     * Restituisce il valore nominale della carta.
     * @return il valore della carta
     */
    public String getValore() {
        return valore;
    }

    /**
     * Imposta il valore nominale della carta.
     * @param valore il nuovo valore da assegnare
     */
    public void setValore(String valore) {
        this.valore = valore;
    }

    /**
     * Restituisce il seme della carta.
     * @return il seme della carta
     */
    public String getSeme() {
        return seme;
    }

    /**
     * Imposta il seme della carta.
     * @param seme il nuovo seme da assegnare
     */
    public void setSeme(String seme) {
        this.seme = seme;
    }

    /**
     * Restituisce una descrizione testuale della carta.
     * @return stringa nel formato "valore di seme"
     */
    @Override
    public String toString() {
        return valore + " di " + seme;
    }

    /**
     * Genera il nome del file immagine associato alla carta.
     * @return il nome del file stringa completo di estensione
     */
    public String formatta() {return valore + "_of_" + seme + ".png"; }
}
