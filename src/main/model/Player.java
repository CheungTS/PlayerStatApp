package model;

// Represents a player having stats for personal performance.
public class Player {
    private int totalKills;
    private int totalDeath;
    private int roundsPlayed;
    private int headshots;
    private int totalDamage;

    // Default constructor
    public Player() {
        this.totalKills = 0;
        this.totalDeath = 0;
        this.roundsPlayed = 0;
        this.headshots = 0;
        this.totalDamage = 0;
    }

    public Player(int totalKills, int totalDeath, int roundsPlayed, int headshots, int totalDamage) {
        this.totalKills = totalKills;
        this.totalDeath = totalDeath;
        this.roundsPlayed = roundsPlayed;
        this.headshots = headshots;
        this.totalDamage = totalDamage;
    }

    /*
     * MODIFIES: this
     * EFFECTS: all data are added to the player's data and updated
     */
    public void addStats(int totalKills, int totalDeath, int roundsPlayed, int headshots, int totalDamage) {
        this.totalKills += totalKills;
        this.totalDeath += totalDeath;
        this.roundsPlayed += roundsPlayed;
        this.headshots += headshots;
        this.totalDamage += totalDamage;
    }

    /*
     * EFFECTS: Compute the average per round
     * 			and an ADR is returned
     */
    public double getADR() {
        return (double)totalDamage / (double)roundsPlayed;
    }

    /*
     * EFFECTS: Compute the kills and death ratio
     * 			and a KD is returned
     */
    public double getKD() {
        return (double)totalKills / (double)totalDeath;
    }

    /*
     * EFFECTS: Compute the headshot percentage
     * 			and the percentage is returned
     */
    public double getHeadshotPercent() {
        return (double)headshots / (double)totalKills * 100;
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

    public int getHeadshots() {
        return headshots;
    }
}
