package model;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;

// Represents a team which contains players.
public class Team {
    private static final int MAXPLAYER = 3;
    private ArrayList<Player> team = new ArrayList<>();
    private int teamTotalKills;

    public Team() {}

    /*
     * REQUIRES: list.size() <= MAXPLAYER
     * MODIFIES: this
     * EFFECTS: a player will be added to the team list
     */
    public void addPlayer(Player p) {
        team.add(p);
    }

    /*
     * REQUIRES: list.size() > 0
     * MODIFIES: this
     * EFFECTS: a player will be replaced by new player
     *          return true if player is replaced successfully
     */
    public boolean replacePlayer(Player p){
        return false; //stub (list.set(index, element)
    }

    /*
     * REQUIRES: list.size() > 0
     * MODIFIES: this
     * EFFECTS: a player will be removed
     *          return true if player is removed successfully
     */
    public boolean removePlayer(String name){
        return false; //stub (isEmpty(), remove()) check if exist(contains)
    }

    //TODO create update() method, update team stats if player changed
    //TODO indexOf, override output, iterating over an arraylist
}
