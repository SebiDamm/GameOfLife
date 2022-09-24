package com.example.gameoflife;

import java.util.Arrays;
import java.util.Random;

/**
 * m x n Spielbrett für GameOfLife.
 * Es wird der aktuelle Stand des Spielbretts gespeichert.
 */
public class Board {

    private Cell[][] board;

    public Board() {
        this(100, 100);
    }

    /**
     * Konstruktor für Spielbrett
     *
     * @param width  Länge des Spielbretts
     * @param height Höhe des Spielbretts
     */
    public Board(int width, int height) {
        this.board = new Cell[height][width];

        for (int y = 0; y < this.board.length; y++) { //i = Spalte
            for (int x = 0; x < this.board[y].length; x++) { //j = Zeile
                this.board[y][x] = new Cell();
            }
        }
    }

    public Cell[][] getBoard() {
        return board;
    }

    /**
     * Erzeugt eine lebende Zelle an der Stelle (x,y).
     *
     * @param x x-Koordinate für die lebende Zelle
     * @param y y-Koordinate für die lebende Zelle
     * @throws IndexOutOfBoundsException falls die Koordinaten außerhalb des Spielfeldes liegen
     */
    public void createLife(int x, int y) throws IndexOutOfBoundsException {
        if (y < 0 || y >= this.board.length) {
            throw new IndexOutOfBoundsException("Spalte out of bounds");
        }
        if (x < 0 || x >= this.board[y].length) {
            throw new IndexOutOfBoundsException("Zeile out of bounds");
        }
        this.board[y][x].comeToLife();
    }

    /**
     * Erzeugt eine tote Zelle an der Stelle (x,y).
     *
     * @param x x-Koordinate für die tote Zelle
     * @param y y-Koordinate für die tote Zelle
     * @throws IndexOutOfBoundsException falls die Koordinaten außerhalb des Spielfeldes liegen
     */
    public void deleteLife(int x, int y) throws IndexOutOfBoundsException {
        if (y < 0 || y >= this.board.length) {
            throw new IndexOutOfBoundsException("Spalte out of bounds");
        }
        if (x < 0 || x >= this.board[y].length) {
            throw new IndexOutOfBoundsException("Zeile out of bounds");
        }
        this.board[y][x].kill();
    }

    /**
     * Erzeugt eine tote Zelle an der Stelle (x,y).
     *
     * @param x x-Koordinate für die tote Zelle
     * @param y y-Koordinate für die tote Zelle
     * @throws IndexOutOfBoundsException falls die Koordinaten außerhalb des Spielfeldes liegen
     */
    public boolean isCellAlive(int x, int y) {
        if (y < 0 || y >= this.board.length) {
            throw new IndexOutOfBoundsException("Spalte out of bounds");
        }
        if (x < 0 || x >= this.board[y].length) {
            throw new IndexOutOfBoundsException("Zeile out of bounds");
        }
        return this.board[y][x].isAlive();
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
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                // Überprüft ob Index in bounds ist
                if (y + dy >= 0 && y + dy < this.board.length && x + dx >= 0 && x + dx < this.board[y].length) {
                    if (this.board[y + dy][x + dx].isAlive() && (dy != 0 || dx != 0)) {
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
        Cell[][] board2 = new Cell[this.board.length][this.board[0].length];

        for (int y = 0; y < board2.length; y++) { //i = Spalte
            for (int x = 0; x < board2[y].length; x++) { //j = Zeile
                board2[y][x] = new Cell();
            }
        }

        for (int y = 0; y < this.board.length; y++) {
            for (int x = 0; x < this.board[y].length; x++) {

                if (this.board[y][x].isAlive()) {
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
        this.board = board2;
    }

    /**
     * Gibt das Spielbrett grafisch aus (momentan Console).
     */
    public void printBoard() {
        StringBuilder output = new StringBuilder();
        for (Cell[] zellenRow : this.board) {
            Arrays.stream(zellenRow).forEach(cell -> output.append("+-"));
            output.append("+\n");
            Arrays.stream(zellenRow).forEach(cell -> {
                output.append("|");
                if (cell.isAlive()) {
                    output.append("#");
                } else {
                    output.append(" ");
                }
            });
            output.append("|\n");
        }
        Arrays.stream(this.board[this.board.length - 1]).forEach(cell -> output.append("+-"));
        output.append("+\n");
        System.out.println(output);
    }

    public void clear() {
        Arrays.stream(board).forEach(colum -> {
            Arrays.stream(colum).forEach(Cell::kill);
        });
    }

    public void generateRandomLife() {
        Random random = new Random();
        Arrays.stream(board).forEach(colum -> {
            Arrays.stream(colum).forEach(cell -> {
                if(random.nextInt(3) == 1) {
                    cell.comeToLife();
                }
            });
        });
    }
}
