package com.example;

import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class SnakeGame {
    private static final int WIDTH = 20;
    private static final int HEIGHT = 10;

    private static LinkedList<int[]> snake = new LinkedList<>();
    private static int[] food = new int[2];
    private static int score = 0;
    private static boolean gameOver = false;
    private static String direction = "RIGHT";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        initGame();

        while (!gameOver) {
            printBoard();
            System.out.println("Enter direction (W/A/S/D): ");
            if (scanner.hasNextLine()) {
                String input = scanner.nextLine().toUpperCase();
                if ("WASD".contains(input)) direction = input;
            }
            moveSnake();
            checkCollision();
        }
        System.out.println("Game Over! Final Score: " + score);
    }

    private static void initGame() {
        snake.add(new int[]{HEIGHT / 2, WIDTH / 2});
        spawnFood();
    }

    private static void spawnFood() {
        Random rand = new Random();
        food[0] = rand.nextInt(HEIGHT);
        food[1] = rand.nextInt(WIDTH);
    }

    private static void printBoard() {
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

    private static void moveSnake() {
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

    private static void checkCollision() {
        int[] head = snake.getFirst();
        if (head[0] < 0 || head[0] >= HEIGHT || head[1] < 0 || head[1] >= WIDTH) gameOver = true;

        for (int i = 1; i < snake.size(); i++) {
            if (head[0] == snake.get(i)[0] && head[1] == snake.get(i)[1]) gameOver = true;
        }
    }
}
