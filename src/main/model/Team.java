package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a team which contains players.
public class Team implements Writable {
    private static final int MAXPLAYER = 3;
    private ArrayList<Player> member = new ArrayList<>();
    private double teamKD;
    private double teamADR;

    public Team() {
        this.teamKD = 0;
        this.teamADR = 0;
    }

    /*
     * EFFECTS: Return number of players in the team
     */
    public int size() {
        return member.size();
    }

    /*
     * EFFECTS: Return a player
     */
    public Player getPlayer(int i) {
        return member.get(i);
    }

    /*
     * MODIFIES: this
     * EFFECTS: update the team average stats
     */
    public void update() {
        double totalADR = 0;
        double totalKD = 0;
        if (member.size() == 0) {
            this.teamADR = 0;
            this.teamKD = 0;
            return;
        }
        for (int i = 0; i < member.size(); i++) {
            totalADR += member.get(i).getADR();
            totalKD += member.get(i).getKD();
        }
        this.teamADR = totalADR / member.size();
        this.teamKD = totalKD / member.size();
    }

    /*
     * REQUIRES: list.size() <= MAXPLAYER
     * MODIFIES: this
     * EFFECTS: a player will be added to the team list
     *          Team stats will be updated if player added
     *          return true if player added
     */
    public boolean addPlayer(Player p) {
        if (member.contains(p)) {
            return false;
        }
        if (member.size() < MAXPLAYER) {
            member.add(p);
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
        if (member.isEmpty()) {
            return false;
        }
        if (member.contains(p)) {
            member.remove(p);
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

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("KD", teamKD);
        json.put("ADR", teamADR);
        json.put("players", playersToJson());
        return json;
    }

    // EFFECTS: returns players in this team as a JSON array
    private JSONArray playersToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Player t : member) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }
}
