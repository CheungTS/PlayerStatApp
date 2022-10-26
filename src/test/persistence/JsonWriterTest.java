package persistence;

import model.*;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

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
    void testWriterEmptyWorkroom() {
        try {
            Team t = new Team();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(t);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
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
}
