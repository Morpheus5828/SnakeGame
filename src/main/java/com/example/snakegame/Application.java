package com.example.snakegame;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Application extends javafx.application.Application {
    public static int speed = 5  ;
    public static int foodColor = 0;
    public static int width = 20;
    public static int height = 20;
    public static int foodX = 0;
    public static int foodY = 0;
    public static int cornerSize = 25;

    public static List<Corner> snake = new ArrayList<>();
    public static Direction direction = Direction.left;
    public static boolean gameOver = false;
    public static Random random = new Random();


    @Override
    public void start(Stage stage) throws IOException {
        try {
            newFood();

            VBox root = new VBox();
            Canvas canvas = new Canvas(width * cornerSize, height * cornerSize);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            root.getChildren().add(canvas);

            new AnimationTimer() {
                long lastTick = 0;

                @Override
                public void handle(long now) {
                    if(lastTick == 0) {
                        lastTick = now;
                        tick(gc);
                        return;
                    }

                    if(now - lastTick > 1000000000 / speed) {
                        lastTick = now;
                        tick(gc);
                    }
                }
            }.start();

            Scene scene = new Scene(root, width * cornerSize, height * cornerSize);

            scene.addEventFilter(KeyEvent.KEY_PRESSED, key-> {
                if(key.getCode() == KeyCode.UP)
                    direction = Direction.up;
                if(key.getCode() == KeyCode.DOWN)
                    direction = Direction.down;
                if(key.getCode() == KeyCode.RIGHT)
                    direction = Direction.right;
                if(key.getCode() == KeyCode.LEFT)
                    direction = Direction.left;
            });

            snake.add(new Corner(width/2, height/2));
            snake.add(new Corner(width/2, height/2));
            snake.add(new Corner(width/2, height/2));

            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("SnakeGame");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void tick(GraphicsContext gc) {
        if(gameOver) {
            gc.setFill(Color.RED);
            gc.setFont(new Font("", 50));
            gc.fillText("GAME OVER", 100, 250);
            return;
        }


        for (int i = snake.size() - 1; i >= 1; i--) {
            snake.get(i).setX(snake.get(i - 1).getX());
            snake.get(i).setY(snake.get(i - 1).getY());
        }

        switch (direction) {
            case up:
                snake.get(0).setY(snake.get(0).getY() - 1);
                if(snake.get(0).getY() < 0)
                    gameOver = true;
                break;
            case down:
                snake.get(0).setY(snake.get(0).getY() + 1);
                if(snake.get(0).getY() > height)
                    gameOver = true;
                break;
            case left:
                snake.get(0).setX(snake.get(0).getX() - 1);
                if(snake.get(0).getX() < 0)
                    gameOver = true;
                break;
            case right:
                    snake.get(0).setX(snake.get(0).getX() + 1);
                if(snake.get(0).getX() > width)
                    gameOver = true;
                break;
        }

        // Eat food
        if(foodX == snake.get(0).getX() && foodY == snake.get(0).getY()) {
            snake.add(new Corner(-1, -1));
            newFood();
        }

        //Self-Destroy
        for(int i = 1; i < snake.size(); i++) {
            if (snake.get(0).getX() == snake.get(i).getX() && snake.get(0).getY() == snake.get(i).getY()) {
                gameOver = true;
                break;
            }
        }

        //fill and Background
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width*cornerSize, height*cornerSize);

        //score
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("", 30));
        gc.fillText("score"+(speed-6), 10, 30);

        // random foodColor
        Color cc = switch (foodColor) {
            case 0 -> Color.PURPLE;
            case 1 -> Color.LIGHTBLUE;
            case 2 -> Color.YELLOW;
            case 3 -> Color.PINK;
            case 4 -> Color.ORANGE;
            default -> Color.WHITE;
        };

        gc.setFill(cc);
        gc.fillOval(foodX * cornerSize, foodY * cornerSize, cornerSize, cornerSize);

        //snake
        for(Corner c : snake) {
            gc.setFill(Color.LIGHTGREEN);
            gc.fillRect(c.getX() * cornerSize, c.getY() * cornerSize, cornerSize - 1, cornerSize - 1);
            gc.setFill(Color.GREEN);
            gc.fillRect(c.getX() * cornerSize, c.getY() * cornerSize, cornerSize - 2, cornerSize - 2);
        }


    }

    public static void newFood() {
        start:while(true) {
            foodX = random.nextInt(width);
            foodY = random.nextInt(height);

            for (Corner corner : snake) {
                if (corner.getX() == foodX && corner.getY() == foodY)
                    continue start;
            }
            foodColor = random.nextInt(5);
            speed++;
            break;
        }
    }


    public static void main(String[] args) {
        launch();
    }
}