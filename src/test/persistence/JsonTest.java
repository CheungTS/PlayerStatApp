package persistence;

import model.*;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTest {
    protected void checkPlayer(String name,int kills, int death, int rounds, int damage, Player p) {
        assertEquals(name, p.getName());
        assertEquals(kills, p.getTotalKills());
        assertEquals(death, p.getTotalDeath());
        assertEquals(rounds, p.getRoundsPlayed());
        assertEquals(damage, p.getTotalDamage());
    }
}
