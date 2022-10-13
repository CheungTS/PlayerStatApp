package model;

import java.util.ArrayList;

//TODO indexOf, override output, iterating over an arraylist
//TODO design console ui

// Represents a team which contains players.
public class Team {
    private static final int MAXPLAYER = 3;
    private ArrayList<Player> team = new ArrayList<>();
    private double teamKD;
    private double teamADR;

    public Team() {
        this.teamKD = 0;
        this.teamADR = 0;
    }

    /*
     * MODIFIES: this
     * EFFECTS: update the team average stats
     */
    public void update() {
        double totalADR = 0;
        double totalKD = 0;
        if (team.size() == 0) {
            this.teamADR = 0;
            this.teamKD = 0;
            return;
        }
        for (int i = 0; i < team.size(); i++) {
            totalADR += team.get(i).getADR();
            totalKD += team.get(i).getKD();
        }
        this.teamADR = totalADR / team.size();
        this.teamKD = totalKD / team.size();
    }

    /*
     * REQUIRES: list.size() <= MAXPLAYER
     * MODIFIES: this
     * EFFECTS: a player will be added to the team list
     *          Team stats will be updated if player added
     *          return true if player added
     */
    public boolean addPlayer(Player p) {
        if (team.contains(p)) {
            return false;
        }
        if (team.size() < MAXPLAYER) {
            team.add(p);
            update();
            return true;
        }
        return false;
    }

    /*
     * REQUIRES: list.size() is not empty
     * MODIFIES: this
     * EFFECTS: a player will be removed
     *          team stats will be updated if player is removed
     *          return true if player is removed successfully
     */
    public boolean removePlayer(Player p) {
        if (team.isEmpty()) {
            return false;
        }
        if (team.contains(p)) {
            team.remove(p);
            update();
            return true;
        } else {
            return false;
        }
    }

    public double getTeamKD() {
        return teamKD;
    }

    public double getTeamADR() {
        return teamADR;
    }

    /*
     * REQUIRES: list.size() > 0
     * MODIFIES: this
     * EFFECTS: a player will be replaced by new player
     *          return true if player is replaced successfully
     */
    /*
    public boolean replacePlayer(Player p){
        return false; //stub (list.set(index, element)
    }
     */
}
