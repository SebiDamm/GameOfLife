package com.example.gameoflife;

import java.util.Arrays;

/**
 * m x n Spielbrett für GameOfLife.
 * Es wird der aktuelle Stand des Spielbretts gespeichert.
 */
public class Spielbrett {

    private Zelle[][] board;

    /**
     * Konstruktor für Spielbrett
     *
     * @param length Länge des Spielbretts
     * @param height Höhe des Spielbretts
     */
    public Spielbrett(int length, int height) {
        board = new Zelle[height][length];

        for (int y = 0; y < board.length; y++) { //i = Spalte
            for (int x = 0; x < board[y].length; x++) { //j = Zeile
                board[y][x] = new Zelle();
            }
        }
    }

    /**
     * Erzeugt eine lebende Zelle an der Stelle (x,y).
     *
     * @param x x-Koordinate für die lebende Zelle
     * @param y y-Koordinate für die lebende Zelle
     * @throws IndexOutOfBoundsException falls die Koordinaten außerhalb des Spielfeldes liegen
     */
    public void createLife(int x, int y) throws IndexOutOfBoundsException {
        if (y < 0 || y >= board.length) {
            throw new IndexOutOfBoundsException("Spalte out of bounds");
        }
        if (x < 0 || x >= board[y].length) {
            throw new IndexOutOfBoundsException("Zeile out of bounds");
        }
        board[y][x].comeToLife();
    }

    /**
     * Erzeugt eine tote Zelle an der Stelle (x,y).
     *
     * @param x x-Koordinate für die tote Zelle
     * @param y y-Koordinate für die tote Zelle
     * @throws IndexOutOfBoundsException falls die Koordinaten außerhalb des Spielfeldes liegen
     */
    public void deleteLife(int x, int y) throws IndexOutOfBoundsException {
        if (y < 0 || y >= board.length) {
            throw new IndexOutOfBoundsException("Spalte out of bounds");
        }
        if (x < 0 || x >= board[y].length) {
            throw new IndexOutOfBoundsException("Zeile out of bounds");
        }
        board[y][x].kill();

    }

    /**
     * Zählt die Anzahl der Nachbarn von der Zelle an der Stelle (x,y).
     *
     * @param x x-Koordinate der Zelle
     * @param y y-Koordinate der Zelle
     * @return Anzahl an Nachbarn
     */
    public int countNeighbours(int x, int y) {
        int counter = 0;
        for (int dy = -1; dy < 1; dy++) {
            for (int dx = -1; dx < 1; dx++) {
                // Überprüft ob Index in bounds ist
                if (y + dy >= 0 && y + dy < board.length && x + dx >= 0 && x + dx < board[y].length) {
                    if (board[y + dy][x + dx].isAlive() && (dy != 0 || dx != 0)) {
                        counter++;
                    }
                }
            }
        }
        return counter;
    }

    /**
     * Erzeugt die nächste Generation von GameOfLife.
     */
    public void nextGeneration() {
        Zelle[][] board2 = new Zelle[board.length][board[1].length];

        for (int y = 0; y < board2.length; y++) { //i = Spalte
            for (int x = 0; x < board2[y].length; x++) { //j = Zeile
                board2[y][x] = new Zelle();
            }
        }

        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[x].length; y++) {

                if (board[y][x].isAlive()) {
                    switch (countNeighbours(x, y)) {
                        case 0, 1, 4, 5, 6, 7, 8 -> board2[y][x].kill();
                        default -> board2[y][x].comeToLife();
                    }
                } else {
                    if (countNeighbours(x, y) == 3) {
                        board2[y][x].comeToLife();
                    }
                }
            }
        }
        board = board2;
    }

    /**
     * Gibt das Spielbrett grafisch aus (momentan Console).
     */
    public void printBoard() {
        StringBuilder output = new StringBuilder();
        for (Zelle[] zellenRow : board) {
            Arrays.stream(zellenRow).forEach(zelle -> output.append("+-")); // ("+-")
            output.append("+\n"); // ("+\n")
            Arrays.stream(zellenRow).forEach(zelle -> {
                output.append("|");
                if (zelle.isAlive()) {
                    output.append("#");
                } else {
                    output.append(" ");
                }
            });
            output.append("|\n");
        }
        Arrays.stream(board[board.length - 1]).forEach(zelle -> output.append("+-")); // ("+-")
        output.append("+\n"); // ("+\n")
        System.out.println(output);
    }
}
