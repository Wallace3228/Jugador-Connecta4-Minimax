package edu.epsevg.prop.lab.c4;

/**
 * Implementació del jugador automàtic amb l'algorisme MiniMax,
 * amb suport per a poda Alfa-Beta.
 * @author alexmatillasantos - alexsaiztalavera
 */

import static java.lang.Math.random;
import java.util.Random;

public class JugadorMiniMax implements Jugador, IAuto {
    // Nom del jugador
    private String nom;
    // Color del jugador (1 per al propi jugador, -1 per al rival)
    private int colorJugador = 1;
    // Profunditat màxima que l'algorisme explorarà
    private int profunditat;
    // Comptador de nodes processats (ajuda a mesurar l'eficiència)
    private int contadorNodes;
    // Indicador per decidir si s'aplica poda Alfa-Beta
    private boolean defpoda = true;

    // Matriu heurística que dona valor a cada posició del tauler
    public int[][] taulaHeuristica = {
        {3, 4, 5, 7, 7, 5, 4, 3},
        {4, 6, 8,10,10, 8, 6, 4},
        {5, 8,11,13,13,11, 8, 5},
        {7,10,13,16,16,13,10, 7},
        {7,10,13,16,16,13,10, 7},
        {5, 8,11,13,13,11, 8, 5},
        {4, 6, 8,10,10, 8, 6, 4},
        {3, 4, 5, 7, 7, 5, 4, 3}
    };

    /**
     * Constructor del jugador automàtic.
     * @param profunditat profunditat màxima de l'algorisme.
     * @param poda indica si es fa servir poda Alfa-Beta.
     */
    public JugadorMiniMax(int profunditat, boolean poda) {
        nom = "JugadorMinimax";
        this.profunditat = profunditat;
        contadorNodes = 0;
        this.defpoda = poda; // Configura si s'utilitzarà poda
    }

    /**
     * Retorna la columna en la qual el jugador posarà la seva fitxa.
     * Depenent de la configuració, executa Minimax amb o sense poda.
     * @param tauler_actual estat actual del tauler.
     * @param color color de la fitxa del jugador (1 o -1).
     */
    public int moviment(Tauler tauler_actual, int color) {
        colorJugador = color;
        int columnaSeg;
        // Selecció de l'algorisme segons la configuració de `defpoda`
        if (defpoda) {
            columnaSeg = minimaxPoda(tauler_actual, profunditat);
        } else {
            columnaSeg = minimaxSensePoda(tauler_actual, profunditat);
        }
        System.out.println("Nodes Recorreguts = " + contadorNodes);
        return columnaSeg;
    }

    /**
     * Retorna el nom del jugador.
     */
    public String nom() {
        return nom;
    }

    /**
     * Implementació de l'algorisme Minimax amb poda Alfa-Beta.
     * @param tauler estat actual del tauler.
     * @param profunditat profunditat màxima de l'algorisme.
     * @return columna amb el millor moviment.
     */
    public int minimaxPoda(Tauler tauler, int profunditat) {
        int millorValor = Integer.MIN_VALUE;
        int millorColumna = 0;
        int alpha = Integer.MIN_VALUE; // Valor inicial d'Alpha
        int beta = Integer.MAX_VALUE; // Valor inicial de Beta

        for (int i = 0; i < 8; i++) {
            if (tauler.movpossible(i)) {
                // Simula un moviment posant una fitxa a la columna `i`
                Tauler moviment = new Tauler(tauler);
                moviment.afegeix(i, colorJugador);
                // Calcula el valor del moviment simulant el torn de l'oponent
                int nouValor = movMinMaxPoda(moviment, i, profunditat - 1, alpha, beta, 0);
                // Actualitza el millor moviment si s'ha trobat un valor més alt
                if (nouValor > millorValor) {
                    millorValor = nouValor;
                    millorColumna = i;
                }
            }
        }
        return millorColumna;
    }

    /**
     * Implementació de l'algorisme Minimax sense poda Alfa-Beta.
     * @param tauler estat actual del tauler.
     * @param profunditat profunditat màxima de l'algorisme.
     * @return columna amb el millor moviment.
     */
    public int minimaxSensePoda(Tauler tauler, int profunditat) {
        int millorValor = Integer.MIN_VALUE;
        int millorColumna = 0;

        for (int i = 0; i < 8; i++) {
            if (tauler.movpossible(i)) {
                // Simula un moviment posant una fitxa a la columna `i`
                Tauler moviment = new Tauler(tauler);
                moviment.afegeix(i, colorJugador);
                // Calcula el valor del moviment sense aplicar poda
                int nouValor = movMinMaxSensePoda(moviment, i, profunditat - 1, 0);
                if (nouValor > millorValor) {
                    millorValor = nouValor;
                    millorColumna = i;
                }
            }
        }
        return millorColumna;
    }

    /**
     * Funció recursiva de Minimax amb poda Alfa-Beta.
     * @param tauler estat actual del joc.
     * @param ultimaColumna columna on s'ha fet l'últim moviment.
     * @param profunditat profunditat restant.
     * @param alpha valor actual d'Alpha per a la poda.
     * @param beta valor actual de Beta per a la poda.
     * @param mov indica si és el torn del jugador (1) o de l'oponent (0).
     */
    public int movMinMaxPoda(Tauler tauler, int ultimaColumna, int profunditat, int alpha, int beta, int mov) {
        // Casos base: solució o límit de profunditat
        if (tauler.solucio(ultimaColumna, colorJugador)) 
            return 1000000; // El jugador guanya
        else if (tauler.solucio(ultimaColumna, colorJugador * -1)) 
            return -1000000; // L'oponent guanya
        else if (profunditat == 0 || !tauler.espotmoure()) 
            return heuristica(tauler); // Valor heurístic si no hi ha més moviments

        if (mov == 1) { // Torn del jugador
            int millorValor = Integer.MIN_VALUE;
            for (int i = 0; i < 8; i++) {
                if (tauler.movpossible(i)) {
                    Tauler moviment = new Tauler(tauler);
                    moviment.afegeix(i, colorJugador);
                    millorValor = Math.max(millorValor, movMinMaxPoda(moviment, i, profunditat - 1, alpha, beta, 0));
                    alpha = Math.max(alpha, millorValor);
                    if (alpha >= beta) break; // Poda Beta
                }
            }
            return millorValor;
        } else { // Torn de l'oponent
            int pitjorValor = Integer.MAX_VALUE;
            for (int i = 0; i < 8; i++) {
                if (tauler.movpossible(i)) {
                    Tauler moviment = new Tauler(tauler);
                    moviment.afegeix(i, colorJugador * -1);
                    pitjorValor = Math.min(pitjorValor, movMinMaxPoda(moviment, i, profunditat - 1, alpha, beta, 1));
                    beta = Math.min(beta, pitjorValor);
                    if (alpha >= beta) break; // Poda Alpha
                }
            }
            return pitjorValor;
        }
    }

    /**
     * Funció recursiva de Minimax sense poda.
     * @param tauler estat actual del joc.
     * @param ultimaColumna columna on s'ha fet l'últim moviment.
     * @param profunditat profunditat restant.
     * @param mov indica si és el torn del jugador (1) o de l'oponent (0).
     */
    public int movMinMaxSensePoda(Tauler tauler, int ultimaColumna, int profunditat, int mov) {
        // Casos base: solució o límit de profunditat
        if (tauler.solucio(ultimaColumna, colorJugador)) 
            return 1000000; // El jugador guanya
        else if (tauler.solucio(ultimaColumna, colorJugador * -1)) 
            return -1000000; // L'oponent guanya
        else if (profunditat == 0 || !tauler.espotmoure()) 
            return heuristica(tauler); // Valor heurístic si no hi ha més moviments

        if (mov == 1) { // Torn del jugador
            int millorValor = Integer.MIN_VALUE;
            for (int i = 0; i < 8; i++) {
                if (tauler.movpossible(i)) {
                    Tauler moviment = new Tauler(tauler);
                    moviment.afegeix(i, colorJugador);
                    millorValor = Math.max(millorValor, movMinMaxSensePoda(moviment, i, profunditat - 1, 0));
                }
            }
            return millorValor;
        } else { // Torn de l'oponent
            int pitjorValor = Integer.MAX_VALUE;
            for (int i = 0; i < 8; i++) {
                if (tauler.movpossible(i)) {
                    Tauler moviment = new Tauler(tauler);
                    moviment.afegeix(i, colorJugador * -1);
                    pitjorValor = Math.min(pitjorValor, movMinMaxSensePoda(moviment, i, profunditat - 1, 1));
                }
            }
            return pitjorValor;
        }
    }

    /**
     * Calcula el valor heurístic d'un estat del tauler.
     * @param tauler estat del tauler.
     * @return valor heurístic calculat.
     */
    public int heuristica(Tauler tauler) {
        contadorNodes++; // Incrementa el comptador de nodes processats
        int valorHeuristic = 0;
        // Calcula el valor de cada posició del tauler
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (tauler.getColor(i, j) == colorJugador) {
                    valorHeuristic += taulaHeuristica[i][j];
                } else if (tauler.getColor(i, j) == colorJugador * -1) {
                    valorHeuristic -= taulaHeuristica[i][j];
                }
            }
        }
        return valorHeuristic;
    }
}