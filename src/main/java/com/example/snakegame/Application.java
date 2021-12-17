package com.example.snakegame;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Snake Game by Morpheus5828");

        Pane pane = new Pane();

        for(int i = 0; i < 580; i = i + 40) {
            for(int j = 0; j < 580; j = j + 40) {
                Rectangle rectangle = new Rectangle(i, j, 200, 200);
                rectangle.setWidth(40);
                rectangle.setHeight(40);
                rectangle.setFill(Color.BLACK);
                rectangle.setStroke(Color.WHITE);
                pane.getChildren().add(rectangle);
            }
        }

        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(40);
        rectangle.setHeight(40);
        rectangle.setFill(Color.RED);
        rectangle.setStroke(Color.WHITE);

       // pane.getChildren().remove(4);
        pane.getChildren().add(4, rectangle);



        pane.setStyle("-fx-background-color: black;");

        Scene scene = new Scene(pane, 600, 600);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}