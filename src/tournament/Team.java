
package tournament;

/**
 *
 * @author NAOMI
 */


import game.GamePlayer;
import java.util.ArrayList;

/**
 * TEAM CLASS - Represents a team in tournament
 */
public class Team {
    private String teamName;
    private int teamId;
    private static int idCounter = 1;
    private ArrayList<GamePlayer> players;
    private int wins;
    private int losses;
    private int totalScore;

    /**
     * Constructor
     */
    public Team(String teamName) {
        this.teamName = teamName;
        this.teamId = idCounter++;
        this.players = new ArrayList<>();
        this.wins = 0;
        this.losses = 0;
        this.totalScore = 0;
    }

    /**
     * Add player to team
     */
    public void addPlayer(GamePlayer player) {
        players.add(player);
    }

    /**
     * Get players
     */
    public ArrayList<GamePlayer> getPlayers() {
        return new ArrayList<>(players);
    }

    /**
     * Record win
     */
    public void recordWin(int points) {
        wins++;
        totalScore += points;
    }

    /**
     * Record loss
     */
    public void recordLoss() {
        losses++;
    }

    public String getTeamName() {
        return teamName;
    }

    public int getTeamId() {
        return teamId;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public int getPlayerCount() {
        return players.size();
    }

    @Override
    public String toString() {
        return teamName + " [" + wins + "W-" + losses + "L, Score: " + totalScore + "]";
    }
}