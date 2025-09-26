package com.example;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class FlappyBird extends Application {

    private static final int WIDTH = 400;
    private static final int HEIGHT = 600;
    private static final int BIRD_SIZE = 20;
    private static final int GRAVITY = 1;
    private static final int JUMP_STRENGTH = -15;
    private static final int PIPE_WIDTH = 50;
    private static final int GAP = 150;

    private Rectangle bird;
    private double velocity = 0;
    private List<Rectangle[]> pipes = new ArrayList<>();
    private int score = 0;
    private Text scoreText;

    private Random random = new Random();
    private boolean gameOver = false;

    @Override
    public void start(Stage stage) {
        Pane root = new Pane();
        root.setPrefSize(WIDTH, HEIGHT);

        bird = new Rectangle(BIRD_SIZE, BIRD_SIZE, Color.YELLOW);
        bird.setTranslateX(100);
        bird.setTranslateY(HEIGHT / 2);

        scoreText = new Text(10, 20, "Score: 0");
        scoreText.setFont(Font.font(20));
        scoreText.setFill(Color.WHITE);

        root.getChildren().add(bird);
        root.getChildren().add(scoreText);

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        scene.setFill(Color.SKYBLUE);

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE && !gameOver) {
                velocity = JUMP_STRENGTH;
            }
        });

        stage.setScene(scene);
        stage.setTitle("Flappy Bird - JavaFX");
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            private long lastPipeTime = 0;

            @Override
            public void handle(long now) {
                if (!gameOver) {
                    // Gravity
                    velocity += GRAVITY;
                    bird.setTranslateY(bird.getTranslateY() + velocity);

                    // Bird hits ground or ceiling
                    if (bird.getTranslateY() < 0 || bird.getTranslateY() > HEIGHT - BIRD_SIZE) {
                        stopGame();
                    }

                    // Add pipes every 1.5 seconds
                    if (now - lastPipeTime > 1_500_000_000) {
                        addPipe(root);
                        lastPipeTime = now;
                    }

                    // Move pipes
                    Iterator<Rectangle[]> iter = pipes.iterator();
                    while (iter.hasNext()) {
                        Rectangle[] pipe = iter.next();
                        pipe[0].setTranslateX(pipe[0].getTranslateX() - 5);
                        pipe[1].setTranslateX(pipe[1].getTranslateX() - 5);

                        // Check collisions
                        if (bird.getBoundsInParent().intersects(pipe[0].getBoundsInParent())
                                || bird.getBoundsInParent().intersects(pipe[1].getBoundsInParent())) {
                            stopGame();
                        }

                        // Update score
                        if (pipe[0].getTranslateX() + PIPE_WIDTH == bird.getTranslateX()) {
                            score++;
                            scoreText.setText("Score: " + score);
                        }

                        // Remove off-screen pipes
                        if (pipe[0].getTranslateX() + PIPE_WIDTH < 0) {
                            root.getChildren().removeAll(pipe[0], pipe[1]);
                            iter.remove();
                        }
                    }
                }
            }
        };
        timer.start();
    }

    private void addPipe(Pane root) {
        int gapStart = 50 + random.nextInt(HEIGHT - GAP - 100);
        Rectangle topPipe = new Rectangle(PIPE_WIDTH, gapStart, Color.GREEN);
        topPipe.setTranslateX(WIDTH);

        Rectangle bottomPipe = new Rectangle(PIPE_WIDTH, HEIGHT - gapStart - GAP, Color.GREEN);
        bottomPipe.setTranslateX(WIDTH);
        bottomPipe.setTranslateY(gapStart + GAP);

        root.getChildren().addAll(topPipe, bottomPipe);
        pipes.add(new Rectangle[]{topPipe, bottomPipe});
    }

    private void stopGame() {
        gameOver = true;
        scoreText.setText("Game Over! Score: " + score);
    }

    // 👇 New getter method for testing
    public int getScore() {
        return score;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
