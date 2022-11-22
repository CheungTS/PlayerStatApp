package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

// Stats collection application.
// Without prompt
public class GuiApp {
    private Team team1;
    private Team team2;
    private ArrayList<Player> playerList;

    private JsonWriter jsonWriter1;
    private JsonReader jsonReader1;
    private static final String JSON_STORE_1 = "./data/team1GUI.json";

    private JsonWriter jsonWriter2;
    private JsonReader jsonReader2;
    private static final String JSON_STORE_2 = "./data/team2GUI.json";

    private JsonWriter jsonWriterPlayer;
    private JsonReader jsonReaderPlayer;
    private static final String JSON_STORE_Player = "./data/playerGUI.json";

    // EFFECTS: runs the application
    public GuiApp() {
        jsonWriter1 = new JsonWriter(JSON_STORE_1);
        jsonReader1 = new JsonReader(JSON_STORE_1);
        jsonWriter2 = new JsonWriter(JSON_STORE_2);
        jsonReader2 = new JsonReader(JSON_STORE_2);
        jsonWriterPlayer = new JsonWriter(JSON_STORE_Player);
        jsonReaderPlayer = new JsonReader(JSON_STORE_Player);
        init();
    }

    // MODIFIES: this
    // EFFECTS: initializes Teams
    private void init() {
        playerList = new ArrayList<>();
        team1 = new Team();
        team2 = new Team();
    }

    // MODIFIES: this
    // EFFECTS: create a player
    public void createPlayer(String name) {
        Player p = new Player(name);
        playerList.add(p);
    }

    // MODIFIES: this
    // EFFECTS: add player to a team
    public void addPlayerToTeam(String name, int teamIndex) {
        Team tmp;

        if (teamIndex == 1) {
            tmp = team1;
        } else {
            tmp = team2;
        }

        //System.out.println("Add player to team" + teamIndex);

        for (Player p : playerList) {
            if (p.getName().equals(name)) {
                tmp.addPlayer(p);
                return;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: remove player from player list
    public void removePlayer(String name) {
        for (Player p : playerList) {
            if (p.getName().equals(name)) {
                playerList.remove(p);
                return;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: remove player from a team
    public void removePlayerFromTeam(String name, int teamIndex) {

        //System.out.println("remove player from team" + teamIndex);

        for (Player p : playerList) {
            if (p.getName().equals(name)) {
                if (team1.removePlayer(p)) {
                    return;
                } else if (team2.removePlayer(p)) {
                    return;
                } else {
                    return;
                }
            }
        }
    }

    public void save() {
        //System.out.println("saving");
        savePlayers();
        saveTeams();
    }

    public void load() {
        //System.out.println("loading");
        loadPlayers();
        loadTeams();
    }

    // EFFECTS: saves the team to file
    private void saveTeams() {
        try {
            jsonWriter1.open();
            jsonWriter1.write(team1);
            jsonWriter1.close();
            //System.out.println("Saved " + "team1" + " to " + JSON_STORE_1);
        } catch (FileNotFoundException e) {
            //System.out.println("Unable to write to file: " + JSON_STORE_1);
        }

        try {
            jsonWriter2.open();
            jsonWriter2.write(team2);
            jsonWriter2.close();
            //System.out.println("Saved " + "team2" + " to " + JSON_STORE_2);
        } catch (FileNotFoundException e) {
            //System.out.println("Unable to write to file: " + JSON_STORE_2);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads team from file
    private void loadTeams() {
        try {
            team1 = jsonReader1.read();
            //System.out.println("Loaded " + "team1" + " from " + JSON_STORE_1);
        } catch (IOException e) {
            //System.out.println("Unable to read from file: " + JSON_STORE_1);
        }

        try {
            team2 = jsonReader2.read();
            //System.out.println("Loaded " + "team2" + " from " + JSON_STORE_2);
        } catch (IOException e) {
            //System.out.println("Unable to read from file: " + JSON_STORE_2);
        }
    }

    // EFFECTS: saves the team to file
    private void savePlayers() {
        try {
            jsonWriterPlayer.open();
            jsonWriterPlayer.write(playerList);
            jsonWriterPlayer.close();
            //System.out.println("Saved " + "playerList" + " to " + JSON_STORE_Player);
        } catch (FileNotFoundException e) {
            //System.out.println("Unable to write to file: " + JSON_STORE_Player);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads team from file
    private void loadPlayers() {
        try {
            playerList = jsonReaderPlayer.readPlayers();
            //System.out.println("Loaded " + "playerList" + " from " + JSON_STORE_Player);
        } catch (IOException e) {
            //System.out.println("Unable to read from file: " + JSON_STORE_Player);
        }
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    public Team getTeam1() {
        return team1;
    }

    public Team getTeam2() {
        return team2;
    }
}
