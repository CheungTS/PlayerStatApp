package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TeamTest {
    private Team team1;
    private Team team2;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;

    @BeforeEach
    void runBefore() {
        team1 = new Team();
        team2 = new Team();
        player1 = new Player("a");
        player2 = new Player("b");
        player3 = new Player("c");
        player4 = new Player("d",100,100,100,100);
    }

    @Test
    void tesAddPlayer() {
        assertTrue(team1.addPlayer(player1));
        assertFalse(team1.addPlayer(player1));
        assertTrue(team1.addPlayer(player2));
        assertTrue(team1.addPlayer(player3));
        assertFalse(team1.addPlayer(player4));
        assertFalse(team1.addPlayer(player4));
    }

    @Test
    void testRemovePlayer() {
        assertFalse(team1.removePlayer(player4));

        assertTrue(team1.addPlayer(player1));
        assertTrue(team1.addPlayer(player2));
        assertTrue(team1.addPlayer(player3));

        assertTrue(team1.removePlayer(player1));
        assertFalse(team1.removePlayer(player1));
        assertTrue(team1.removePlayer(player2));
        assertTrue(team1.removePlayer(player3));
    }

    @Test
    void testGetTeamKD() {
        assertTrue(team2.addPlayer(player4));
        assertEquals(team2.getTeamKD(), player4.getKD());
        assertTrue(team2.addPlayer(player3));

        assertFalse(player1.addStats(100,-1,100,1000));
        assertFalse(player1.addStats(100,-1,-1,1000));

        assertTrue(player3.addStats(100,100,100,100));
        team2.update();
        assertEquals(team2.getTeamKD(), ( player4.getKD() + player3.getKD() ) / 2);

        assertTrue(team2.removePlayer(player4));
        assertEquals(team2.getTeamKD(), player3.getKD());

    }

    @Test
    void testGetTeamADR(){
        assertTrue(team2.addPlayer(player4));
        assertEquals(team2.getTeamADR(), player4.getADR());
        assertTrue(team2.addPlayer(player3));
        assertEquals(team2.getTeamADR(), ( player4.getADR() + player3.getADR() ) / 2);

        assertTrue(team2.removePlayer(player4));
        assertEquals(team2.getTeamKD(), player3.getADR());
    }

    @Test
    void testGetName() {
        assertEquals(player1.getName(), "a");
        assertEquals(player2.getName(), "b");
    }

    @Test
    void testGetStats() {
        assertEquals(player4.getStats(), "Kills: 100, Death: 100, Rounds: 100, Damage: 100");
    }

    @Test
    void testGetAdr() {
        assertEquals(player1.getADR(),0);
        assertEquals(player4.getADR(), (double)player4.getTotalDamage()/player4.getRoundsPlayed());
    }

    @Test
    void testGetKD() {
        assertEquals(player1.getKD(),0);
        assertEquals(player4.getKD(), (double)player4.getTotalKills()/player4.getTotalDeath());
    }
}
