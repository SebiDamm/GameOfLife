package com.example.gameoflife;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
//        launch();

        Spielbrett spielbrett = new Spielbrett(10, 10);
        spielbrett.createLife(4,4);
        spielbrett.createLife(4, 5);
        spielbrett.createLife(4, 6);
        spielbrett.printBoard();
        spielbrett.nextGeneration();
        spielbrett.printBoard();
        spielbrett.nextGeneration();
        spielbrett.printBoard();

        System.exit(0);
    }
}