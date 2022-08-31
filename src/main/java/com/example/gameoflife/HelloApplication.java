package com.example.gameoflife;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Stack;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        Button xCoordinateButton = new Button();
        xCoordinateButton.setOnMouseClicked(event -> {
            System.out.println(event.getX());
        });
        xCoordinateButton.setText("X Coordinates");
        xCoordinateButton.setAlignment(Pos.CENTER_RIGHT);

        Label label = new Label("Activity: ");

        HBox hBox = new HBox();
        hBox.getChildren().addAll(label,xCoordinateButton);
        hBox.setPadding(new Insets(40,40,40,40));
        hBox.setSpacing(40);


        BorderPane bPane = new BorderPane();
        bPane.setTop(new TextField("Top"));
        bPane.setBottom(new TextField("Bottom"));
        bPane.setLeft(new TextField("Left"));
        bPane.setRight(new TextField("Right"));
        bPane.setCenter(hBox);
        bPane.setBackground(new Background(new BackgroundFill(Color.rgb(255,255,0),CornerRadii.EMPTY,Insets.EMPTY)));
//        BorderPane.setAlignment(hBox,Pos.CENTER);
//
//        AnchorPane.setTopAnchor(bPane,0.);
//        AnchorPane.setLeftAnchor(bPane,0.);
//        AnchorPane.setRightAnchor(bPane,0.);
//        AnchorPane.setBottomAnchor(bPane,0.);

        Scene scene = new Scene(bPane, 320, 240);


        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();

    }
}