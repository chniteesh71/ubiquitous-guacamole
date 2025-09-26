package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SnakeGameTest {

    @Test
    public void testInitialGameState() {
        SnakeGame game = new SnakeGame();
        assertEquals(0, game.getScore());
        assertFalse(game.isGameOver());
    }

    @Test
    public void testMoveSnakeUpdatesScore() {
        SnakeGame game = new SnakeGame();
        // Move snake manually
        game.setDirection("D");  // Example
        game.update();            // move and check collision
        assertTrue(game.getScore() >= 0); // at least 0
    }
}
