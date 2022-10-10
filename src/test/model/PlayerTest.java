package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerTest {
    private Player testPlayer;
    private Player testPlayer1;
    @BeforeEach
    void runBefore() {
        testPlayer = new Player();
        testPlayer1 = new Player(100, 100, 100, 50, 1000);
    }

    @Test
    void
}