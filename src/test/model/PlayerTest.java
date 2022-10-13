package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Player testPlayer;
    private Player testPlayer1;
    @BeforeEach
    void runBefore() {
        testPlayer = new Player("a");
        testPlayer1 = new Player("b",100, 100, 100, 1000);
    }

    @Test
    void testGetStats() {
        assertEquals(testPlayer.getStats(), "Kills: 0, Death: 0, Rounds: 0, Damage: 0");
    }

    @Test
    void testAddStats() {
        assertFalse(testPlayer.addStats(100,-1,100,1000));
        assertFalse(testPlayer.addStats(100,-1,-1,1000));

        assertTrue(testPlayer.addStats(100,100,100,1000));
        assertEquals(testPlayer.getTotalKills() , 100);
        assertEquals(testPlayer.getTotalDeath() , 100);
        assertEquals(testPlayer.getRoundsPlayed(), 100);
        assertEquals(testPlayer.getTotalDamage(),1000);
    }

    @Test
    void testGetName() {
        assertEquals(testPlayer.getName(), "a");
        assertEquals(testPlayer1.getName(), "b");
    }

    @Test
    void testGetAdr() {
        assertEquals(testPlayer.getADR(),0);
        assertEquals(testPlayer1.getADR(), (double)testPlayer1.getTotalDamage()/testPlayer1.getRoundsPlayed());
    }

    @Test
    void testGetKD() {
        assertEquals(testPlayer.getKD(),0);
        assertEquals(testPlayer1.getKD(), (double)testPlayer1.getTotalKills()/testPlayer1.getTotalDeath());
    }
}