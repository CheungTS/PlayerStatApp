package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import java.util.Scanner;
import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

// Stats collection application.
public class App {
    private Team team1;
    private Team team2;
    private ArrayList<Player> playerList;
    private Scanner input;

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
    public App() {
        jsonWriter1 = new JsonWriter(JSON_STORE_1);
        jsonReader1 = new JsonReader(JSON_STORE_1);
        jsonWriter2 = new JsonWriter(JSON_STORE_2);
        jsonReader2 = new JsonReader(JSON_STORE_2);
        jsonWriterPlayer = new JsonWriter(JSON_STORE_Player);
        jsonReaderPlayer = new JsonReader(JSON_STORE_Player);
        runApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runApp() {
        boolean keepGoing = true;
        String command;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: initializes Teams
    private void init() {
        playerList = new ArrayList<>();
        team1 = new Team();
        team2 = new Team();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\n===Options menu===");
        System.out.println("\nSelect from:");
        System.out.println("\tc  -> Create player");
        System.out.println("\ta  -> Add player to the team");
        System.out.println("\tr  -> Remove player");
        System.out.println("\tcs -> Check stats");
        System.out.println("\ts -> save players and teams to file");
        System.out.println("\tl -> load players and teams from file");
        System.out.println("\tq  -> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    @SuppressWarnings("methodlength")
    private void processCommand(String command) {
        switch (command) {
            case "c":
                createPlayer();
                break;
            case "a":
                addPlayerToTeam();
                break;
            case "r":
                removePlayer();
                break;
            case "cs":
                checkStats();
                break;
            case "s":
                saveTeams();
                savePlayers();
                break;
            case "l":
                loadTeams();
                loadPlayers();
                break;
            default:
                System.out.println("Selection not valid...");
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: create a player
    private void createPlayer() {
        Player p = readPlayerStats();
        playerList.add(p);
        System.out.println("---Player created---");
    }

    // EFFECTS: Read player data from user and return the player
    private Player readPlayerStats() {
        Player p;
        System.out.println("Enter the player name: ");
        String name = input.next();

        System.out.println("Create a default player? Enter yes or no: ");
        String command = input.next();
        command = command.toLowerCase();
        if (command.equals("yes")) {
            p = new Player(name);
        } else {
            System.out.println("Enter the total kills: ");
            int kills = input.nextInt();

            System.out.println("Enter the total death: ");
            int death = input.nextInt();

            System.out.println("Enter the rounds played: ");
            int rounds = input.nextInt();

            System.out.println("Enter the total damage: ");
            int damage = input.nextInt();
            p = new Player(name, kills, death, rounds, damage);
        }
        return p;
    }

    // MODIFIES: this
    // EFFECTS: add player to a team
    private void addPlayerToTeam() {
        Team tmp;
        System.out.println("team 1 or team 2, enter 1 or 2");
        int i = input.nextInt();
        if (i == 1) {
            tmp = team1;
        } else {
            tmp = team2;
        }

        System.out.println("Enter the player name: ");
        String name = input.next();

        for (Player p : playerList) {
            if (p.getName().equals(name)) {
                tmp.addPlayer(p);
                System.out.println("---Player added---");
                return;
            }
        }
        System.out.println("---Player not exist---");
    }

    // MODIFIES: this
    // EFFECTS: remove player from a team
    private void removePlayer() {
        System.out.println("Enter the player name: ");
        String name = input.next();

        for (Player p : playerList) {
            if (p.getName().equals(name)) {
                if (team1.removePlayer(p)) {
                    System.out.println("---Player removed--");
                    return;
                } else if (team2.removePlayer(p)) {
                    System.out.println("---Player removed--");
                    return;
                } else {
                    System.out.println("---Player not exist---");
                }
            }
        }
    }

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

}
