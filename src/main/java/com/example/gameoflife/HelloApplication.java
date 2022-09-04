package com.example.gameoflife;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        int width = 50;
        int height = 50;
        int cellSize = 10;

        Spielbrett board = new Spielbrett(width, height);
        board.generateRandomLife();

        VBox root = new VBox();

        Button resetButton = new Button("Reset");
        Button stepButton = new Button("Step");
        Button runButton = new Button("Run");
        Button stopButton = new Button("Stop");
        Button clearButton = new Button("Clear");

        final Canvas canvas = new Canvas(width * 10, height * 10);
        root.getChildren().addAll(canvas, resetButton, stepButton, runButton, stopButton, clearButton);
        Scene scene = new Scene(root, width * 10, height * 10 + 120);

        GraphicsContext graphics = canvas.getGraphicsContext2D();

        AnimationTimer runAnimation = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                // only update once every second
                if ((now - lastUpdate) >= TimeUnit.MILLISECONDS.toNanos(500)) {
                    board.nextGeneration();
                    tick(graphics, board, width, height, cellSize);
                    lastUpdate = now;
                }
            }
        };
        runAnimation.start();

        resetButton.setOnAction(l -> {
            board.clear();
            board.generateRandomLife();
            tick(graphics, board, width, height, cellSize);
        });
        runButton.setOnAction(  l -> runAnimation.start());
        stepButton.setOnAction( l -> {
            board.nextGeneration();
            tick(graphics, board, width, height, cellSize);
        });
        stopButton.setOnAction( l -> runAnimation.stop());
        clearButton.setOnAction(event -> {
            board.clear();
            tick(graphics, board, width, height, cellSize);
        });

        stage.setTitle("Game of Life");
        stage.setScene(scene);
        stage.show();
    }

    public static void tick(GraphicsContext graphics, Spielbrett board, int width, int height, int cellSize) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (board.getBoard()[i][j].isAlive()) {
                    graphics.setFill(Color.gray(0.5, 0.5));
                    graphics.fillRect(i * cellSize, j * cellSize, cellSize, cellSize);
                    graphics.setFill(Color.RED);
                    graphics.fillRect((i * cellSize) + 1, (j * cellSize) + 1, cellSize - 2, cellSize - 2);
                }else {
                    graphics.setFill(Color.gray(0.5, 0.5));
                    graphics.fillRect(i * cellSize, j * cellSize, cellSize, cellSize);
                    graphics.setFill(Color.LAVENDER);
                    graphics.fillRect((i * cellSize) + 1, (j * cellSize) + 1, cellSize - 2, cellSize - 2);
                }
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}