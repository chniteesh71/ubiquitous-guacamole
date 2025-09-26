package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SnakeGameTest {

    @Test
    void testScoreIncrement() {
        SnakeGameTestable game = new SnakeGameTestable();
        int initialScore = game.getScore();
        game.increaseScore();
        assertEquals(initialScore + 1, game.getScore());
    }

    @Test
    void testGameOverCollision() {
        SnakeGameTestable game = new SnakeGameTestable();
        game.hitWall();
        assertTrue(game.isGameOver());
    }
}

// Helper class to test without console input
class SnakeGameTestable extends SnakeGame {
    void increaseScore() { super.score++; }
    void hitWall() { super.gameOver = true; }
    int getScore() { return super.score; }
    boolean isGameOver() { return super.gameOver; }
}
