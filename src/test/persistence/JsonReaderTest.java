package persistence;

import model.*;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest{
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Team t = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
        try {
            Team t = reader.read();
            assertEquals(0, t.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testWriterGeneralTeam.json");
        try {
            Team t = reader.read();
            assertEquals(2, t.size());
            assertEquals(0.5, t.getTeamADR());
            assertEquals(0.5, t.getTeamKD());
            checkPlayer("a" ,100,100,100,100,t.getPlayer(0));
            checkPlayer("b" ,0,0,0,0,t.getPlayer(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
