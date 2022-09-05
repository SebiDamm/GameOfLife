package com.example.gameoflife;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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

        final Canvas canvas = new Canvas(width * cellSize, height * cellSize);
        root.getChildren().addAll(canvas, resetButton, stepButton, runButton, stopButton, clearButton);
        Scene scene = new Scene(root, width * cellSize, height * cellSize + 120);

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

        resetButton.setOnAction(event -> {
            board.clear();
            board.generateRandomLife();
            tick(graphics, board, width, height, cellSize);
        });
        runButton.setOnAction(event -> runAnimation.start());
        stepButton.setOnAction(event -> {
            board.nextGeneration();
            tick(graphics, board, width, height, cellSize);
        });
        stopButton.setOnAction(event -> runAnimation.stop());
        clearButton.setOnAction(event -> {
            board.clear();
            tick(graphics, board, width, height, cellSize);
        });

        stage.setTitle("Game of Life");
        stage.setScene(scene);
        stage.show();
    }

    public static void tick(GraphicsContext graphics, Spielbrett board, int width, int height, int cellSize) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (board.getBoard()[y][x].isAlive()) {
                    graphics.setFill(Color.gray(0.5, 0.5));
                    graphics.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
                    graphics.setFill(Color.RED);
                    graphics.fillRect((x * cellSize) + 1, (y * cellSize) + 1, cellSize - 2, cellSize - 2);
                } else {
                    graphics.setFill(Color.gray(0.5, 0.5));
                    graphics.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
                    graphics.setFill(Color.LAVENDER);
                    graphics.fillRect((x * cellSize) + 1, (y * cellSize) + 1, cellSize - 2, cellSize - 2);
                }
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}