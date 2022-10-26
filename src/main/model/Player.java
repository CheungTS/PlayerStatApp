package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a player having stats for personal performance.
public class Player implements Writable {
    private String name;
    private int totalKills;
    private int totalDeath;
    private int roundsPlayed;
    private int totalDamage;

    // Default constructor
    public Player(String name) {
        this.name = name;
        this.totalKills = 0;
        this.totalDeath = 0;
        this.roundsPlayed = 0;
        this.totalDamage = 0;
    }

    public Player(String name,int totalKills, int totalDeath, int roundsPlayed, int totalDamage) {
        this.name = name;
        this.totalKills = totalKills;
        this.totalDeath = totalDeath;
        this.roundsPlayed = roundsPlayed;
        this.totalDamage = totalDamage;
    }

    /*
     * EFFECTS: return player stats as a string
     */
    public String getStats() {
        String s = "Kills: " + totalKills + ", Death: " + totalDeath;
        s += ", Rounds: " + roundsPlayed + ", Damage: " + totalDamage;
        return s;
    }

    /*
     * REQUIRES: totalDeath, roundsPlayed >= 0
     * MODIFIES: this
     * EFFECTS: all data are added to the player's data and updated
     */
    public boolean addStats(int totalKills, int totalDeath, int roundsPlayed, int totalDamage) {
        if (totalDeath < 0 || roundsPlayed < 0) {
            return false;
        }
        this.totalKills += totalKills;
        this.totalDeath += totalDeath;
        this.roundsPlayed += roundsPlayed;
        this.totalDamage += totalDamage;
        return true;
    }

    /*
     * REQUIRES: roundsPlayed != 0
     * EFFECTS: Compute the average per round
     * 			and an ADR is returned
     */
    public double getADR() {
        if (roundsPlayed == 0) {
            return 0;
        }
        return (double)totalDamage / (double)roundsPlayed;
    }

    /*
     * REQUIRES: totalDeath != 0
     * EFFECTS: Compute the kills and death ratio
     * 			and a KD is returned
     */
    public double getKD() {
        if (totalDeath == 0) {
            return 0;
        }
        return (double)totalKills / (double)totalDeath;
    }

    public String getName() {
        return name;
    }

    public int getTotalKills() {
        return totalKills;
    }

    public int getTotalDamage() {
        return totalDamage;
    }

    public int getRoundsPlayed() {
        return roundsPlayed;
    }

    public int getTotalDeath() {
        return totalDeath;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("kills", totalKills);
        json.put("death", totalDeath);
        json.put("rounds", roundsPlayed);
        json.put("damage", totalDamage);
        return json;
    }
}
