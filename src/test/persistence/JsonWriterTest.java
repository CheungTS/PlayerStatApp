package persistence;

import model.*;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Team t = new Team();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyTeam() {
        try {
            Team t = new Team();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyTeam.json");
            writer.open();
            writer.write(t);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyTeam.json");
            t = reader.read();
            assertEquals(0, t.getTeamKD());
            assertEquals(0, t.getTeamADR());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            Team t = new Team();
            Player p1 = new Player("a",100, 100, 100, 100);
            Player p2 = new Player("b");
            t.addPlayer(p1);
            t.addPlayer(p2);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralTeam.json");
            writer.open();
            writer.write(t);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralTeam.json");
            t = reader.read();
            assertEquals(0.5, t.getTeamADR());
            assertEquals(0.5, t.getTeamKD());
            checkPlayer("a" ,100,100,100,100,p1);
            checkPlayer("b" ,0,0,0,0,p2);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterEmptyPlayerList() {
        try {
            ArrayList<Player> playerList = new ArrayList<>();
            JsonWriter writer = new JsonWriter("./data/testPlayer.json");
            writer.open();
            writer.write(playerList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testPlayer.json");
            playerList = reader.readPlayers();
            assertEquals(0, playerList.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralPlayerList() {
        try {
            ArrayList<Player> playerList = new ArrayList<>();
            Player p1 = new Player("a",100, 100, 100, 100);
            Player p2 = new Player("b");
            playerList.add(p1);
            playerList.add(p2);
            JsonWriter writer = new JsonWriter("./data/testGeneralPlayer.json");
            writer.open();
            writer.write(playerList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testGeneralPlayer.json");
            playerList = reader.readPlayers();
            assertEquals(2, playerList.size());
            assertEquals(p1.getName(), playerList.get(0).getName());
            assertEquals(p2.getName(), playerList.get(1).getName());
            assertEquals(p1.getTotalDeath(), playerList.get(0).getTotalDeath());
            assertEquals(p2.getTotalDeath(), playerList.get(1).getTotalDeath());
            assertEquals(p1.getRoundsPlayed(), playerList.get(0).getRoundsPlayed());
            assertEquals(p2.getRoundsPlayed(), playerList.get(1).getRoundsPlayed());
            assertEquals(p1.getTotalDamage(), playerList.get(0).getTotalDamage());
            assertEquals(p2.getTotalDamage(), playerList.get(1).getTotalDamage());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
