package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
/** Classe principale: gestisce gli stati di gioco e il rendering.
 * @author Riccardo Grosa
 * @version 05.06.2026
 */
public class MainGame extends ApplicationAdapter {
    //VARIABILI "DI SISTEMA"
    private SpriteBatch batch;
    private BitmapFont font;
    private Texture background;
    private Texture display1;
    private Texture display2;
    private Texture display3;
    private Texture display4;
    private Texture display5;
    private Texture display6;
    private Texture displayBig;

    //VARIABILI HOME
    private Texture homeTex;
    private Rectangle modNormale;
    private Rectangle modDifficile;
    private String mod;

    //VARIABILI INIZIALIZZAZIONE
    private Mazzo mazzo;
    private Banco banco;
    private User user;
    private ArrayList<Texture> userTexture;
    private ArrayList<Texture> bancoTexture;
    private Texture ombra;
    private GameState statoAttuale;
    private float timerStato = 0; // Serve per creare pause tra le azioni

    //VARIABILI SUONI
    private Sound musicaLobby;
    private Sound soundCarta;
    private Sound soundVincita;
    private Sound soundPerdita;
    private Sound soundFiche;

    //VARIABILI PUNTATA
    private int puntataMomentanea;
    private Texture fiche;
    private boolean mostraFiche;

    //VARIABILI BJ
    private boolean thereIsBJ;
    private float bjX, bjY;
    private float bjVX = 0.4f;
    private float bjVY = 0.3f;
    private Rectangle displayBigRect;

    //VARIABILI PAGAMENTO
    private String pagamento;

    /**
     * Inizializza tutte le risorse, i suoni e i dati di gioco.
     */
    @Override
    public void create() {
        //VARIABILI "DI SISTEMA"
        batch = new SpriteBatch();
        font = new BitmapFont();
        background = new Texture("img/tavolo.png");
        display1 = new Texture("img/display.png");
        display2 = new Texture("img/display.png");
        display3 = new Texture("img/display.png");
        display4 = new Texture("img/display.png");
        display5 = new Texture("img/display.png");
        display6 = new Texture("img/display.png");
        displayBig = new Texture("img/displayBig.png");
        thereIsBJ = false;

        //VARIABILI HOME
        homeTex = new Texture("img/homeTex.png");

        modNormale = new Rectangle(268, 163, 393, 130);
        modDifficile = new Rectangle(715, 163, 393, 130);

        //VARIABILI INIZIALIZZAZIONE
        mazzo = new Mazzo();
        banco = new Banco();
        user = new User(100);
        userTexture = new ArrayList<>();
        bancoTexture = new ArrayList<>();
        ombra = new Texture("img/ombra.png");
        statoAttuale = GameState.HOME;
        caricaSoldi();

        //VARIABILI SOUND
        musicaLobby = Gdx.audio.newSound(Gdx.files.internal("sounds/lobby.wav"));
        soundCarta = Gdx.audio.newSound(Gdx.files.internal("sounds/card.wav"));
        soundVincita = Gdx.audio.newSound(Gdx.files.internal("sounds/vincita.mp3"));
        soundPerdita = Gdx.audio.newSound(Gdx.files.internal("sounds/perdita.mp3"));
        soundFiche = Gdx.audio.newSound(Gdx.files.internal("sounds/fiche.wav"));
        musicaLobby.loop(0.5f);

        //VARIABILI PUNTATA
        puntataMomentanea = 0;
        fiche = new Texture("img/fiche.png");
        mostraFiche = false;

        //VARIABILI BJ
        displayBigRect = new Rectangle(145, 631, 162, 61);

        bjX = displayBigRect.x + 10;
        bjY = displayBigRect.y + 10;
    }

    /**
     * Aggiorna lo stato logico del gioco e renderizza la grafica.
     */
    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        float dt = Gdx.graphics.getDeltaTime();

        batch.begin();
        if (statoAttuale != GameState.HOME) {
            batch.draw(background, 0, 0);
            batch.draw(display1, 100, 65, 350, 30);
            batch.draw(display2, 547, 65, 127, 35);
            batch.draw(display3, 735, 65, 127, 35);
            batch.draw(display4, 958, 65, 110, 30);
            batch.draw(display5, 1078, 65, 110, 30);
            batch.draw(display6, 1198, 65, 110, 30);
            batch.draw(displayBig, 130, 630, 191, 67);
            font.getData().setScale(1.7f, 1f);
            if (!thereIsBJ) {
                font.draw(batch, "FICHE: " + user.getSoldi(), 167, 670);
            }
        }


        switch (statoAttuale) {
            case HOME:
                batch.draw(homeTex, 0, 0);
                if (Gdx.input.justTouched()) {
                    float x = Gdx.input.getX();
                    float y = Gdx.graphics.getHeight() - Gdx.input.getY(); //AI

                    if (modNormale.contains(x, y)) {
                        mod = "normale";
                        statoAttuale = GameState.PUNTATA;
                    } else if (modDifficile.contains(x, y)) {
                        mod = "difficile";
                        statoAttuale = GameState.PUNTATA;
                    }
                }
                break;


            case PUNTATA:
                timerStato += dt;
                if (timerStato > 1) {
                    font.getData().setScale(1.7f, 1f);
                    font.draw(batch, "Fai la tua puntata", 180, 86);
                    font.draw(batch, "" + puntataMomentanea, 1003, 86);
                    font.draw(batch, "_", 603, 95);
                    font.draw(batch, "+", 795, 88);
                    font.draw(batch, "Start", 1223, 86);

                    Rectangle rPiu = new Rectangle(735, 65, 127, 35);
                    Rectangle rMeno = new Rectangle(547, 65, 127, 35);
                    Rectangle rStart = new Rectangle(1198, 65, 110, 30);

                    if (Gdx.input.justTouched()) {
                        float x = Gdx.input.getX();
                        float y = Gdx.graphics.getHeight() - Gdx.input.getY(); //AI

                        if (rMeno.contains(x, y)) {
                            if (puntataMomentanea > 0) {
                                puntataMomentanea -= 5;
                            }
                        } else if (rPiu.contains(x, y)) {
                            if (puntataMomentanea < user.getSoldi()) {
                                puntataMomentanea += 5;
                            }
                        } else if (rStart.contains(x, y)) {
                            user.setPuntata(puntataMomentanea);
                            user.setSoldi(user.getSoldi() - puntataMomentanea);
                            salvaSoldi();
                            if (puntataMomentanea != 0) {
                                mostraFiche = true;
                            }

                            statoAttuale = GameState.PRIMA_CARTA_USER;
                            timerStato = 0;
                            puntataMomentanea = 0;
                        }
                    }
                }

                break;

            case PRIMA_CARTA_USER:
                timerStato += dt;
                if (timerStato > 1) {
                    Carta cartaTemp = mazzo.pescaCarta();
                    user.riceviCarta(cartaTemp);
                    soundCarta.play();
                    userTexture.add(new Texture("img/Cards/" + cartaTemp.formatta()));
                    renderCarte();
                    statoAttuale = GameState.PRIMA_CARTA_BANCO;
                    timerStato = 0;
                }
                break;

            case PRIMA_CARTA_BANCO:
                timerStato += dt;
                if (timerStato > 1) {
                    Carta cartaTemp = mazzo.pescaCarta();
                    soundCarta.play();
                    banco.riceviCarta(cartaTemp);
                    bancoTexture.add(new Texture("img/Cards/back.jpg"));
                    statoAttuale = GameState.SECONDA_CARTA_USER;
                    timerStato = 0;
                }
                renderCarte();
                break;

            case SECONDA_CARTA_USER:
                timerStato += dt;

                if (timerStato > 1) {
                    Carta cartaTemp = mazzo.pescaCarta();
                    soundCarta.play();
                    user.riceviCarta(cartaTemp);
                    userTexture.add(new Texture("img/Cards/" + cartaTemp.formatta()));
                    statoAttuale = GameState.SECONDA_CARTA_BANCO;
                    timerStato = 0;
                }
                renderCarte();
                break;

            case SECONDA_CARTA_BANCO:
                timerStato += dt;

                if (timerStato > 1) {
                    Carta cartaTemp = mazzo.pescaCarta();
                    soundCarta.play();
                    banco.riceviCarta(cartaTemp);
                    bancoTexture.add(new Texture("img/Cards/" + cartaTemp.formatta()));

                    if (user.isBlackjack() || banco.isBlackjack()) {
                        String prima_banco = banco.getMano().get(0).formatta();
                        bancoTexture.set(0, new Texture("img/Cards/" + prima_banco));
                        thereIsBJ = true;

                        if (user.isBlackjack() && banco.isBlackjack()) {
                            statoAttuale = GameState.PAREGGIO;
                        } else if (user.isBlackjack()) {
                            statoAttuale = GameState.VINCE_USER;
                        } else {
                            statoAttuale = GameState.VINCE_BANCO;
                        }

                    } else {
                        statoAttuale = GameState.SCELTA_USER;
                    }
                    timerStato = 0;
                }
                renderCarte();
                break;

            case SCELTA_USER:
                timerStato += dt;

                if (timerStato > 1) {
                    font.draw(batch, "Carta", 575, 88);
                    font.draw(batch, "Stai", 775, 88);
                    font.draw(batch, "Hai " + user.getPunteggio(), 238, 86);
                    Rectangle rStai = new Rectangle(735, 65, 127, 35);
                    Rectangle rCarta = new Rectangle(547, 65, 127, 35);

                    if (Gdx.input.justTouched()) {
                        float x = Gdx.input.getX();
                        float y = Gdx.graphics.getHeight() - Gdx.input.getY(); //AI

                        if (rCarta.contains(x, y)) {
                            timerStato = 0;
                            statoAttuale = GameState.CARTA_USER;
                        } else if (rStai.contains(x, y)) {
                            timerStato = 0;
                            statoAttuale = GameState.SCELTA_BANCO;
                        }

                    }
                }
                renderCarte();
                break;

            case CARTA_USER:
                timerStato += dt;

                if (timerStato > 1) {
                    Carta cartaTemp = mazzo.pescaCarta();
                    user.riceviCarta(cartaTemp);
                    soundCarta.play();
                    userTexture.add(new Texture("img/Cards/" + cartaTemp.formatta()));
                    if (user.isSballato()) {
                        String prima_banco = banco.getMano().get(0).formatta();
                        // consiglio da AI: Libera la memoria della vecchia texture (il retro della carta)
                        bancoTexture.get(0).dispose();
                        bancoTexture.set(0, new Texture("img/Cards/" + prima_banco));
                        soundPerdita.play();
                        statoAttuale = GameState.VINCE_BANCO;
                    } else {
                        statoAttuale = GameState.SCELTA_USER;
                    }
                    timerStato = 0;
                }
                renderCarte();
                break;

            case SCELTA_BANCO:
                timerStato += dt;
                if (timerStato > 0.5) {
                    String prima_banco = banco.getMano().get(0).formatta();
                    bancoTexture.set(0, new Texture("img/Cards/" + prima_banco));
                    batch.draw(ombra, 650 - 2, 263, 72, 102);
                    batch.draw(bancoTexture.get(0), 650, 265, 68, 98);
                }

                if (timerStato > 1) {
                    if (mod.equals("difficile")) {
                        if (banco.getPunteggio() < user.getPunteggio()) {
                            statoAttuale = GameState.CARTA_BANCO;
                        } else if (banco.getPunteggio() == user.getPunteggio()) {
                            statoAttuale = GameState.PAREGGIO;
                        } else {
                            soundPerdita.play();
                            statoAttuale = GameState.VINCE_BANCO;
                        }
                    } else {
                        if (banco.getPunteggio() < 17) {
                            statoAttuale = GameState.CARTA_BANCO;
                        } else {
                            if (banco.getPunteggio() < user.getPunteggio()) {
                                soundVincita.play();
                                statoAttuale = GameState.VINCE_USER;
                            } else if (banco.getPunteggio() == user.getPunteggio()) {
                                statoAttuale = GameState.PAREGGIO;
                            } else {
                                soundPerdita.play();
                                statoAttuale = GameState.VINCE_BANCO;
                            }
                        }
                    }

                    timerStato = 0;
                }
                renderCarte();
                break;

            case CARTA_BANCO:
                timerStato += dt;

                if (timerStato > 1) {
                    Carta cartaTemp = mazzo.pescaCarta();
                    banco.riceviCarta(cartaTemp);
                    soundCarta.play();
                    bancoTexture.add(new Texture("img/Cards/" + cartaTemp.formatta()));
                    timerStato = 0;
                    if (banco.isSballato()) {
                        soundVincita.play();
                        statoAttuale = GameState.VINCE_USER;
                        timerStato = 0;
                    } else {
                        statoAttuale = GameState.SCELTA_BANCO;
                        timerStato = 0;
                    }
                }
                renderCarte();
                break;

            case VINCE_BANCO:
                timerStato += dt;
                if (timerStato > 1) {
                    renderCarte();
                    font.getData().setScale(1.5f, 1f);
                    font.draw(batch, "HAI PERSO!", 215, 86);
                    continuazioneGioco(dt);
                }
                pagamento = "vinceBanco";
                renderCarte();
                break;


            case VINCE_USER:
                timerStato += dt;
                if (timerStato > 1) {
                    font.getData().setScale(1.5f, 1f);
                    font.draw(batch, "HAI VINTO!", 220, 86);
                    continuazioneGioco(dt);
                }
                pagamento = "vinceUser";
                renderCarte();
                break;

            case PAREGGIO:
                timerStato += dt;
                if (timerStato > 1) {
                    font.getData().setScale(1.5f, 1f);
                    font.draw(batch, "HAI PAREGGIATO!", 190, 86);
                    continuazioneGioco(dt);
                }
                pagamento = "pareggio";
                renderCarte();
                break;
        }

        batch.end();
    }

    //DISPOSE FATTO CON AI PER TROVARE TUTTE LE VARIABILI SU CUI FARE DISPOSE
    /**
     * Rilascia dalla memoria tutte le risorse allocate.
     */
    @Override
    public void dispose() {
        // VARIABILI DI SISTEMA / GRAFICA
        if (batch != null) batch.dispose();
        if (font != null) font.dispose();
        if (background != null) background.dispose();
        if (homeTex != null) homeTex.dispose();
        if (ombra != null) ombra.dispose();
        if (fiche != null) fiche.dispose();

        // TEXTURE DEI DISPLAY
        if (display1 != null) display1.dispose();
        if (display2 != null) display2.dispose();
        if (display3 != null) display3.dispose();
        if (display4 != null) display4.dispose();
        if (display5 != null) display5.dispose();
        if (display6 != null) display6.dispose();
        if (displayBig != null) displayBig.dispose();

        // TEXTURE DELLE CARTE ANCORA IN MEMORIA
        if (userTexture != null) {
            for (Texture t : userTexture) {
                if (t != null) t.dispose();
            }
            userTexture.clear();
        }

        if (bancoTexture != null) {
            for (Texture t : bancoTexture) {
                if (t != null) t.dispose();
            }
            bancoTexture.clear();
        }

        // FILE AUDIO
        if (musicaLobby != null) musicaLobby.dispose();
        if (soundCarta != null) soundCarta.dispose();
        if (soundVincita != null) soundVincita.dispose();
        if (soundPerdita != null) soundPerdita.dispose();
        if (soundFiche != null) soundFiche.dispose();
        salvaSoldi();
    }

    /**
     * Disegna a schermo le carte in mano al giocatore e al banco.
     */
    private void renderCarte() {
        int xUser = 650;
        int xBanco = 650;

        if (mostraFiche) {
            batch.draw(fiche, 675, 180, 55, 55);
        }

        for (int i = 0; i < userTexture.size(); i++) {
            batch.draw(ombra, xUser - 2, 263, 72, 102);
            batch.draw(userTexture.get(i), xUser, 265, 68, 98);
            xUser += 31;
        }

        for (int i = 0; i < bancoTexture.size(); i++) {
            batch.draw(ombra, xBanco - 2, 508, 72, 102);
            batch.draw(bancoTexture.get(i), xBanco, 510, 68, 98);
            xBanco += 31;
        }
    }

    /**
     * Mostra i punteggi finali e gestisce la transizione alla mano successiva.
     * * @param dt il delta time fornito dal motore grafico
     */
    public void continuazioneGioco(float dt) {
        font.getData().setScale(1.3f, 1f);
        if (user.isBlackjack()) {
            font.draw(batch, "Tu hai BJ", 571, 88);
        } else {
            font.draw(batch, "Tu hai " + user.getPunteggio(), 575, 88);
        }
        if (banco.isBlackjack()) {
            font.draw(batch, "Banco ha BJ", 745, 88);
        } else {
            font.draw(batch, "Banco ha " + banco.getPunteggio(), 747, 88);
        }

        //COLLISIONI
        if (thereIsBJ) {
            font.getData().setScale(1.4f, 1f);
            font.draw(batch, "BLACKJACK!", bjX, bjY + 20);

            float textWidth = 120;
            float textHeight = 25;

            bjX += bjVX;
            bjY += bjVY;

            // collisione destra/sinistra
            if (bjX <= displayBigRect.x) {
                bjX = displayBigRect.x;
                bjVX *= -1;
            }
            if (bjX + textWidth >= displayBigRect.x + displayBigRect.width) {
                bjX = displayBigRect.x + displayBigRect.width - textWidth;
                bjVX *= -1;
            }

            // collisione alto/basso
            if (bjY <= displayBigRect.y) {
                bjY = displayBigRect.y;
                bjVY *= -1;
            }
            if (bjY + textHeight >= displayBigRect.y + displayBigRect.height) {
                bjY = displayBigRect.y + displayBigRect.height - textHeight;
                bjVY *= -1;
            }
        }

        font.getData().setScale(1f, 1f);
        font.draw(batch, "Nuova mano", 974, 86);
        font.draw(batch, "Restart", 1112, 86);
        font.draw(batch, "Esci", 1242, 86);
        Rectangle rSceltaEsci = new Rectangle(1198, 65, 110, 30);
        Rectangle rSceltaRestart = new Rectangle(1078, 65, 110, 30);
        Rectangle rSceltaNuovaMano = new Rectangle(958, 65, 110, 30);

        if (Gdx.input.justTouched()) {
            float x = Gdx.input.getX();
            float y = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (rSceltaNuovaMano.contains(x, y)) {
                pagaRisultato();
                resetPartita(false);

            } else if (rSceltaRestart.contains(x, y)) {
                resetPartita(true);

            } else if (rSceltaEsci.contains(x, y)) {
                Gdx.app.exit();
            }
        }
    }

    /**
     * Calcola e accredita il saldo delle fiches in base all'esito.
     */
    private void pagaRisultato() {
        if (pagamento.equals("vinceUser")) {
            user.setSoldi(user.getSoldi() + user.getPuntata() * 2);
            soundFiche.play();
        }
        else if (pagamento.equals("pareggio")) {
            user.setSoldi(user.getSoldi() + user.getPuntata());
            soundFiche.play();
        }
        salvaSoldi();
    }

    /**
     * Salva in locale il saldo attuale dell'utente.
     */
    private void salvaSoldi() {
        Gdx.files.local("save.txt")
            .writeString(String.valueOf(user.getSoldi()), false);
    }

    /**
     * Carica il saldo dell'utente da file locale.
     */
    private void caricaSoldi() {
        FileHandle file = Gdx.files.local("save.txt");

        if (file.exists()) {
            try {
                user.setSoldi(Integer.parseInt(file.readString()));
            } catch (Exception e) {
                System.out.println("Caricamento dei soldi fallito! Assegno 100 fiche...");
                user.setSoldi(100);
            }
        }
    }

    //AI
    /**
     * Svuota le mani e resetta il tavolo per un nuovo turno o un riavvio totale.
     * * @param restartCompleto {@code true} per resettare tutto e tornare alla HOME, {@code false} per mantenere i fondi attuali
     */
    private void resetPartita(boolean restartCompleto) {

        // pulizia texture
        for (Texture t : userTexture) {
            if (t != null) t.dispose();
        }
        for (Texture t : bancoTexture) {
            if (t != null) t.dispose();
        }

        userTexture.clear();
        bancoTexture.clear();

        // reset comuni
        mazzo = new Mazzo();
        banco = new Banco();

        puntataMomentanea = 0;
        mostraFiche = false;

        thereIsBJ = false;
        pagamento = null;

        timerStato = 0;

        if (restartCompleto) {
            user = new User(100);
            mod = null;
            statoAttuale = GameState.HOME;
        } else {
            user = new User(user.getSoldi());
            statoAttuale = GameState.PUNTATA;
        }
    }
}
