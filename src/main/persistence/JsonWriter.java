package persistence;

import model.Player;
import model.Team;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;

// Represents a writer that writes JSON representation of team, player to file
// part of code is from sample application provided
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    public void write(Team t) {
        JSONObject json = t.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of players to file
    public void write(ArrayList<Player> playerList) {
        JSONObject json = new JSONObject();
        json.put("players", playersToJson(playerList));
        saveToFile(json.toString(TAB));
    }

    // EFFECTS: returns players in this team as a JSON array
    private JSONArray playersToJson(ArrayList<Player> playerList) {
        JSONArray jsonArray = new JSONArray();

        for (Player t : playerList) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }


    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
