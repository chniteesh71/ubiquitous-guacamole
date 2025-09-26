package com.example;

import java.util.LinkedList;
import java.util.Random;

public class SnakeGame {
    private static final int WIDTH = 20;
    private static final int HEIGHT = 10;

    private LinkedList<int[]> snake = new LinkedList<>();
    private int[] food = new int[2];
    private int score = 0;
    private boolean gameOver = false;
    private String direction = "RIGHT";

    public SnakeGame() {
        initGame();
    }

    public int getScore() {
        return score;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setDirection(String dir) {
        if ("WASD".contains(dir)) {
            direction = dir;
        }
    }

    public void update() {
        moveSnake();
        checkCollision();
    }

    private void initGame() {
        snake.clear();
        snake.add(new int[]{HEIGHT / 2, WIDTH / 2});
        spawnFood();
    }

    private void spawnFood() {
        Random rand = new Random();
        food[0] = rand.nextInt(HEIGHT);
        food[1] = rand.nextInt(WIDTH);
    }

    public void printBoard() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                boolean printed = false;
                for (int[] s : snake) {
                    if (s[0] == i && s[1] == j) {
                        System.out.print("S");
                        printed = true;
                        break;
                    }
                }
                if (!printed) {
                    if (food[0] == i && food[1] == j) System.out.print("F");
                    else System.out.print(".");
                }
            }
            System.out.println();
        }
        System.out.println("Score: " + score);
    }

    private void moveSnake() {
        int[] head = snake.getFirst();
        int[] newHead = head.clone();

        switch (direction) {
            case "W" -> newHead[0]--;
            case "S" -> newHead[0]++;
            case "A" -> newHead[1]--;
            case "D" -> newHead[1]++;
        }

        snake.addFirst(newHead);

        if (newHead[0] == food[0] && newHead[1] == food[1]) {
            score++;
            spawnFood();
        } else {
            snake.removeLast();
        }
    }

    private void checkCollision() {
        int[] head = snake.getFirst();
        if (head[0] < 0 || head[0] >= HEIGHT || head[1] < 0 || head[1] >= WIDTH) gameOver = true;

        for (int i = 1; i < snake.size(); i++) {
            if (head[0] == snake.get(i)[0] && head[1] == snake.get(i)[1]) gameOver = true;
        }
    }

    // Optional CLI main
    public static void main(String[] args) {
        SnakeGame game = new SnakeGame();
        java.util.Scanner scanner = new java.util.Scanner(System.in);

        while (!game.isGameOver()) {
            game.printBoard();
            System.out.println("Enter direction (W/A/S/D): ");
            if (scanner.hasNextLine()) {
                String input = scanner.nextLine().toUpperCase();
                game.setDirection(input);
            }
            game.update();
        }

        System.out.println("Game Over! Final Score: " + game.getScore());
    }
}
