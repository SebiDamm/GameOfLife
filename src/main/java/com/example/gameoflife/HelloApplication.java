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
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class HelloApplication extends Application {

    private static int width;
    private static int height;
    private static double offsetX;
    private static double offsetY;
    private static int cellSize;

    @Override
    public void start(Stage stage) {
        Board board = new Board();

        width = 50;
        height = 50;
        offsetX = 0;
        offsetY = 0;
        cellSize = 10;

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

        final Canvas canvas = new Canvas(
                50 * cellSize,
                50 * cellSize);
        root.getChildren().addAll(h1, canvas, runButton, stopButton, stepButton, randomButton, clearButton, tickRateBox);
        root.setSpacing(10);
        root.setPadding(new Insets(10));
        HBox.setHgrow(randomButton, Priority.ALWAYS);
        Scene scene = new Scene(root,
                canvas.getWidth() + 20,
                canvas.getHeight() + 280);
        scene.setFill(Color.LAVENDER);

        GraphicsContext graphics = canvas.getGraphicsContext2D();

        AnimationTimer runAnimation = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if ((now - lastUpdate) >= TimeUnit.MILLISECONDS.toNanos((long) (tickRateSlider.getValue() * 1000))) {
                    board.nextState();
                    draw(graphics, board);
                    lastUpdate = now;
                }
            }
        };

        runButton.setOnAction(event -> runAnimation.start());
        stopButton.setOnAction(event -> runAnimation.stop());
        stepButton.setOnAction(event -> {
            board.nextState();
            draw(graphics, board);
        });
        randomButton.setOnAction(event -> {
            board.clear();
            board.nextState();
            draw(graphics, board);
        });
        clearButton.setOnAction(event -> {
            board.clear();
            draw(graphics, board);
        });

        canvas.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                double x = (event.getX() / cellSize) - (offsetX / cellSize);
                double y = (event.getY() / cellSize) - (offsetY / cellSize);
                if (board.isCellAlive((int) x, (int) y)) {
                    board.deleteLife((int) x, (int) y);
                } else {
                    board.createLife((int) x, (int) y);
                }
                draw(graphics, board);
            }
        });

        AtomicReference<Double> oldX = new AtomicReference<>((double) 0);
        AtomicReference<Double> oldY = new AtomicReference<>((double) 0);

        canvas.setOnMousePressed(event -> {
            if (event.getButton().equals(MouseButton.SECONDARY)) {
                oldX.set(event.getX());
                oldY.set(event.getY());
            }
        });

        canvas.setOnMouseDragged(event -> {
            try {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    double x = (event.getX() / cellSize) - (offsetX / cellSize);
                    double y = (event.getY() / cellSize) - (offsetY / cellSize);
                    if (board.isCellAlive((int) x, (int) y)) {
                        board.deleteLife((int) x, (int) y);
                    } else {
                        board.createLife((int) x, (int) y);
                    }
                } else if (event.getButton().equals(MouseButton.SECONDARY)) {
                    offsetX += event.getX() - oldX.get();
                    offsetY += event.getY() - oldY.get();
                    oldX.set(event.getX());
                    oldY.set(event.getY());
                }
                draw(graphics, board);
            } catch (IndexOutOfBoundsException ignored) {
            }
        });

        draw(graphics, board);
        stage.setResizable(false);
        stage.setTitle("Game of Life");
        stage.setScene(scene);
        stage.show();
    }

    public static void draw(GraphicsContext graphics, Board board) {
        graphics.setFill(Color.LAVENDER);
        graphics.fillRect(0, 0, width, height);
        for (int y = -5; y < height + 5; y++) {
            for (int x = -5; x < width + 5; x++) {
                if (board.isCellAlive((int) (x - (offsetX / cellSize)), (int) (y - (offsetY / cellSize)))) {
                    graphics.setFill(Color.LIGHTGRAY);
                    graphics.fillRect(
                            x * cellSize + offsetX,
                            y * cellSize + offsetY,
                            cellSize, cellSize);
                    graphics.setFill(Color.RED);
                    graphics.fillRect(
                            x * cellSize + 1 + offsetX,
                            y * cellSize + 1 + offsetY,
                            cellSize - 2, cellSize - 2);
                } else {
                    graphics.setFill(Color.LIGHTGRAY);
                    graphics.fillRect(
                            x * cellSize + offsetX,
                            y * cellSize + offsetY,
                            cellSize, cellSize);
                    graphics.setFill(Color.LAVENDER);
                    graphics.fillRect(
                            x * cellSize + 1 + offsetX,
                            y * cellSize + 1 + offsetY,
                            cellSize - 2, cellSize - 2);
                }
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}