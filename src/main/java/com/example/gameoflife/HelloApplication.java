package com.example.gameoflife;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

public class HelloApplication extends Application {
    static int heightGameboard = 50;
    static int lengthGameboard = 50;
    static int cellSize = 10;

    @Override
    public void start(Stage stage) throws IOException {

        Spielbrett spielbrett = new Spielbrett(lengthGameboard,heightGameboard);

        Button start = new Button("Start");
        Button stop = new Button("Stop");
        Button reset = new Button("Reset");

        spielbrett.fillSpielbrett();
        Canvas canvas = new Canvas(lengthGameboard * cellSize,heightGameboard * cellSize);
        VBox box = new VBox(canvas);
        box.getChildren().addAll(start,stop,reset);

        Scene scene = new Scene(box,lengthGameboard* cellSize, heightGameboard * cellSize + 100);

        GraphicsContext context = canvas.getGraphicsContext2D();
        tick(context,spielbrett);

        AnimationTimer animationTimer = new AnimationTimer() {
           long lastUpdate = 0;
            @Override
            public void handle(long now) {
                if (now - lastUpdate >= TimeUnit.MILLISECONDS.toNanos(500)){
                    spielbrett.nextGeneration();
                    tick(context,spielbrett);
                    lastUpdate = now;
                }
            }
        };
        start.setOnMouseClicked(event -> animationTimer.start()) ;
        stop.setOnMouseClicked(event -> animationTimer.stop());
        reset.setOnMouseClicked(event -> {
            spielbrett.clearSpielbrett();
            spielbrett.fillSpielbrett();
            tick(context,spielbrett);
        });
        stage.setTitle("Gameboard");
        stage.setScene(scene);
        stage.show();
    }

    public static void tick (GraphicsContext context, Spielbrett spielbrett){

        for (int i = 0; i < spielbrett.getBoard().length; i++) { //i = Spalte
            for (int j = 0; j < spielbrett.getBoard()[i].length; j++) { //j = Zeile
                if (spielbrett.getBoard()[i][j].isAlive()) {
                    context.setFill(Color.GRAY);
                    context.fillRect(i * cellSize,j * cellSize,cellSize,cellSize);
                    context.setFill(Color.RED);
                    context.fillRect((i * cellSize) + 1,(j * cellSize) + 1,cellSize - 2, cellSize - 2);
                }else {
                    context.setFill(Color.GRAY);
                    context.fillRect(i * cellSize,j * cellSize,cellSize,cellSize);
                    context.setFill(Color.WHITE);
                    context.fillRect((i * cellSize) + 1,(j * cellSize) + 1,cellSize - 2, cellSize - 2);
                }
            }
        }

    }

    public static void main(String[] args) {
        launch();
    }
}