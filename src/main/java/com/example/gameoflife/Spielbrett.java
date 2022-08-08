package com.example.gameoflife;

public class Spielbrett {
    private Zelle board[][];

    public Spielbrett(int length, int heigth) {
        board = new Zelle[heigth][length];

        for (int i = 0; i < board.length; i++) { //i = Spalte
            for (int j = 0; j < board[i].length; j++) { //j = Zeile
                board[i][j] = new Zelle();
            }
        }
    }

    public void createLife(int x, int y) throws IndexOutOfBoundsException {
        if (y < 0 || y >= board.length) {
            throw new IndexOutOfBoundsException("Spalte out of bounds");
        }
        if (x < 0 || x >= board[y].length) {
            throw new IndexOutOfBoundsException("Zeile out of bounds");
        }
        board[y][x].comeToLife();
    }

    public void deleteLife(int x, int y) throws IndexOutOfBoundsException {
        if (y < 0 || y >= board.length) {
            throw new IndexOutOfBoundsException("Spalte out of bounds");
        }
        if (x < 0 || x >= board[y].length) {
            throw new IndexOutOfBoundsException("Zeile out of bounds");
        }
        board[y][x].kill();

    }

    public int countNeighbours(int x, int y) {
        int counter = 0;
        for (int i = -1; i < 1; i++) {
            for (int j = -1; j < 1; j++) {
                if (board[y + i][x + j].isAlive() && (i != 0 || j != 0)) {
                    counter++;
                }
            }
        }
        return counter;
    }

    public void nextGeneration() {


        Zelle board2[][] = new Zelle[board.length][board[1].length];

        for (int i = 0; i < board2.length; i++) { //i = Spalte
            for (int j = 0; j < board2[i].length; j++) { //j = Zeile
                board2[i][j] = new Zelle();
            }
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {

                if (board[j][i].isAlive()) {
                    board2[j][i].comeToLife();
                    switch (countNeighbours(j, i)) {
                        case 0, 1, 4, 5, 6, 7, 8 -> board2[j][i].kill();
                    }
                } else {
                    if (countNeighbours(j, i) == 3) {
                        board2[j][i].comeToLife();
                    }
                }
            }
        }
        board = board2;
    }
}
