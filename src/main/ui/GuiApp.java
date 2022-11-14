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
    private static final String JSON_STORE_1 = "./data/team1.json";

    private JsonWriter jsonWriter2;
    private JsonReader jsonReader2;
    private static final String JSON_STORE_2 = "./data/team2.json";

    private JsonWriter jsonWriterPlayer;
    private JsonReader jsonReaderPlayer;
    private static final String JSON_STORE_Player = "./data/player.json";

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
        savePlayers();
        saveTeams();
    }

    public void load() {
        loadPlayers();
        loadTeams();
    }

    // EFFECTS: saves the team to file
    private void saveTeams() {
        try {
            jsonWriter1.open();
            jsonWriter1.write(team1);
            jsonWriter1.close();
            System.out.println("Saved " + "team1" + " to " + JSON_STORE_1);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE_1);
        }

        try {
            jsonWriter2.open();
            jsonWriter2.write(team2);
            jsonWriter2.close();
            System.out.println("Saved " + "team2" + " to " + JSON_STORE_2);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE_2);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads team from file
    private void loadTeams() {
        try {
            team1 = jsonReader1.read();
            System.out.println("Loaded " + "team1" + " from " + JSON_STORE_1);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE_1);
        }

        try {
            team2 = jsonReader2.read();
            System.out.println("Loaded " + "team2" + " from " + JSON_STORE_2);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE_2);
        }
    }

    // EFFECTS: saves the team to file
    private void savePlayers() {
        try {
            jsonWriterPlayer.open();
            jsonWriterPlayer.write(playerList);
            jsonWriterPlayer.close();
            System.out.println("Saved " + "playerList" + " to " + JSON_STORE_Player);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE_Player);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads team from file
    private void loadPlayers() {
        try {
            playerList = jsonReader1.readPlayers();
            System.out.println("Loaded " + "playerList" + " from " + JSON_STORE_Player);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE_Player);
        }
    }

    /*
    // MODIFIES: this
    // EFFECTS: check stats of a player or a team
    private void checkStats() {
        System.out.println("team or player: ");
        String com = input.next();

        if (com.equals("player")) {
            checkPlayerStats();
        } else if (com.equals("team")) {
            checkTeamStats();
        }
    }

        // MODIFIES: this
    // EFFECTS: check stats of a player
    private void checkPlayerStats() {
        System.out.println("Enter the player name: ");
        String name = input.next();

        for (Player p : playerList) {
            if (p.getName().equals(name)) {
                System.out.println(p.getStats());
                return;
            }
        }
        System.out.println("---Player not exist---");
    }

    // MODIFIES: this
    // EFFECTS: check stats of a team
    private void checkTeamStats() {
        System.out.println("Enter the team name (1 or 2): ");
        int i = input.nextInt();

        if (i == 1) {
            System.out.println("Team ADR: " + team1.getTeamADR());
            System.out.println("Team KD: " + team1.getTeamKD());
        } else if (i == 2) {
            System.out.println("Team ADR: " + team2.getTeamADR());
            System.out.println("Team KD: " + team2.getTeamKD());
        } else {
            System.out.println("Team not exist");
        }
    }

*/
}
