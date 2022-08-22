package com.example.gameoflife;

public class Main {
    public static void main(String[] args) {
        Spielbrett spielbrett = new Spielbrett(10,10);

        spielbrett.createLife(2,6);
        spielbrett.createLife(2,5);
        spielbrett.createLife(2,7);

        spielbrett.printBoard();
        spielbrett.nextGeneration();
        spielbrett.printBoard();
    }
}
