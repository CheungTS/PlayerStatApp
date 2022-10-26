package persistence;

import model.Player;
import model.Team;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads workroom from JSON data stored in file
// part of code is from sample application provided
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads team from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Team read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTeam(jsonObject);
    }

    // EFFECTS: reads team from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ArrayList<Player> readPlayers() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePlayer(jsonObject);
    }

    // EFFECTS: parses players from JSON object and returns it
    private ArrayList<Player> parsePlayer(JSONObject jsonObject) {
        ArrayList<Player> l = new ArrayList<>();
        addPlayersToList(l, jsonObject);
        return l;
    }

    // MODIFIES: t
    // EFFECTS: parses players from JSON object and adds them to team
    private void addPlayersToList(ArrayList<Player> l, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("players");
        for (Object json : jsonArray) {
            JSONObject nextPlayer = (JSONObject) json;
            addPlayerToList(l, nextPlayer);
        }
    }

    // MODIFIES: l
    // EFFECTS: parses Player from JSON object and adds it to list
    private void addPlayerToList(ArrayList<Player> l, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int kills = jsonObject.getInt("kills");
        int death = jsonObject.getInt("death");
        int rounds = jsonObject.getInt("rounds");
        int damage = jsonObject.getInt("damage");

        Player p = new Player(name, kills, death, rounds, damage);
        l.add(p);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses team from JSON object and returns it
    private Team parseTeam(JSONObject jsonObject) {
        Team t = new Team();
        addPlayers(t, jsonObject);
        return t;
    }

    // MODIFIES: t
    // EFFECTS: parses players from JSON object and adds them to team
    private void addPlayers(Team t, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("players");
        for (Object json : jsonArray) {
            JSONObject nextPlayer = (JSONObject) json;
            addPlayer(t, nextPlayer);
        }
    }

    // MODIFIES: t
    // EFFECTS: parses Player from JSON object and adds it to list
    private void addPlayer(Team t, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int kills = jsonObject.getInt("kills");
        int death = jsonObject.getInt("death");
        int rounds = jsonObject.getInt("rounds");
        int damage = jsonObject.getInt("damage");

        Player p = new Player(name, kills, death, rounds, damage);
        t.addPlayer(p);
    }
}
