package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FlappyBirdTest {

    @Test
    void testGameStartsWithZeroScore() {
        FlappyBird game = new FlappyBird();
        assertEquals(0, game.getScore());  // You may need to add a getter for score
    }

    @Test
    void dummyTest() {
        assertTrue(true);  // Always passes, just to validate CI
    }
}

