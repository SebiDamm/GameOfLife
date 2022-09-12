package com.example.gameoflife;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.concurrent.TimeUnit;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        int width = 50;
        int height = 50;
        int cellSize = 10;

        Spielbrett board = new Spielbrett(width, height);

        VBox root = new VBox();

        Button runButton = new Button("Run");
        runButton.setPrefWidth(100);
        runButton.setMaxWidth(1000);
        Button stopButton = new Button("Stop");
        stopButton.setPrefWidth(100);
        stopButton.setMaxWidth(1000);
        Button stepButton = new Button("Step");
        stepButton.setPrefWidth(100);
        stepButton.setMaxWidth(1000);
        Button randomButton = new Button("Random");
        randomButton.setPrefWidth(100);
        randomButton.setMaxWidth(1000);
        Button clearButton = new Button("Clear");
        clearButton.setPrefWidth(100);
        clearButton.setMaxWidth(1000);

        Text h1 = new Text("Game of Life");
        h1.setFont(Font.font("Times New Roman", FontWeight.BOLD, 35));
        h1.setFill(Color.RED);

        Slider tickRateSlider = new Slider(0, 2, 0.5);
        HBox tickRateBox = new HBox(new Label("Tick Rate: "), tickRateSlider);
        HBox.setHgrow(tickRateSlider, Priority.ALWAYS);

        final Canvas canvas = new Canvas(width * cellSize, height * cellSize);
        root.getChildren().addAll(h1, canvas, runButton, stopButton, stepButton, randomButton, clearButton, tickRateBox);
        root.setSpacing(10);
        root.setPadding(new Insets(10));
        HBox.setHgrow(randomButton, Priority.ALWAYS);
        Scene scene = new Scene(root, width * cellSize + 20, height * cellSize + 280);

        GraphicsContext graphics = canvas.getGraphicsContext2D();

        AnimationTimer runAnimation = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if ((now - lastUpdate) >= TimeUnit.MILLISECONDS.toNanos((long) (tickRateSlider.getValue() * 1000))) {
                    board.nextGeneration();
                    draw(graphics, board, width, height, cellSize);
                    lastUpdate = now;
                }
            }
        };

        runButton.setOnAction(event -> runAnimation.start());
        stopButton.setOnAction(event -> runAnimation.stop());
        stepButton.setOnAction(event -> {
            board.nextGeneration();
            draw(graphics, board, width, height, cellSize);
        });
        randomButton.setOnAction(event -> {
            board.clear();
            board.generateRandomLife();
            draw(graphics, board, width, height, cellSize);
        });
        clearButton.setOnAction(event -> {
            board.clear();
            draw(graphics, board, width, height, cellSize);
        });

        canvas.setOnMouseClicked(event -> {
            if (board.getBoard()[(int) (event.getY() / cellSize)][(int) (event.getX() / cellSize)].isAlive()) {
                board.deleteLife((int) (event.getX() / cellSize), (int) (event.getY() / cellSize));
            } else {
                board.createLife((int) (event.getX() / cellSize), (int) (event.getY() / cellSize));
            }
            draw(graphics, board, width, height, cellSize);
        });
        canvas.setOnMouseDragged(event -> {
            if (board.getBoard()[(int) (event.getX() / cellSize)][(int) (event.getY() / cellSize)].isAlive()) {
                board.deleteLife((int) (event.getX() / cellSize), (int) (event.getY() / cellSize));
            } else {
                board.createLife((int) (event.getX() / cellSize), (int) (event.getY() / cellSize));
            }
            draw(graphics, board, width, height, cellSize);
        });

        draw(graphics, board, width, height, cellSize);
        stage.setResizable(false);
        stage.setTitle("Game of Life");
        stage.setScene(scene);
        stage.show();
    }

    public static void draw(GraphicsContext graphics, Spielbrett board, int width, int height, int cellSize) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (board.getBoard()[y][x].isAlive()) {
                    graphics.setFill(Color.LIGHTGRAY);
                    graphics.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
                    graphics.setFill(Color.RED);
                    graphics.fillRect((x * cellSize) + 1, (y * cellSize) + 1, cellSize - 2, cellSize - 2);
                } else {
                    graphics.setFill(Color.LIGHTGRAY);
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