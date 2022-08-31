package com.example.gameoflife;

public class Main {
    public static void main(String[] args) {
        Spielbrett spielbrett = new Spielbrett(50,20);

//        spielbrett.createLife(2,2);
//        spielbrett.createLife(2,1);
//        spielbrett.createLife(3,3);
//        spielbrett.createLife(3,4);
//        spielbrett.createLife(1,3);
//        spielbrett.createLife(4,2);

        spielbrett.createLife(1,3);
        spielbrett.createLife(2,3);
        spielbrett.createLife(2,1);
        spielbrett.createLife(3,2);
        spielbrett.createLife(3,3);

        for (int i = 0; i < 100; i++) {
            spielbrett.printBoard();
            spielbrett.nextGeneration();
        }
    }
}
