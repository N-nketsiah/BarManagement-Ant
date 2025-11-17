
package tournament;

/**
 *
 * @author NAOMI
 */
import game.GamePlayer;
import utils.SkillLevel;

/**
 * TOURNAMENTPLAYER CLASS - Represents a player in tournament
 */
public class TournamentPlayer {
    private GamePlayer gamePlayer;
    private SkillLevel skillLevel;
    private int matchesPlayed;
    private int matchesWon;

    /**
     * Constructor
     */
    public TournamentPlayer(GamePlayer gamePlayer, SkillLevel skillLevel) {
        this.gamePlayer = gamePlayer;
        this.skillLevel = skillLevel;
        this.matchesPlayed = 0;
        this.matchesWon = 0;
    }

    /**
     * Get game player
     */
    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    /**
     * Get skill level
     */
    public SkillLevel getSkillLevel() {
        return skillLevel;
    }

    /**
     * Record match played
     */
    public void recordMatchPlayed() {
        matchesPlayed++;
    }

    /**
     * Record match won
     */
    public void recordMatchWon() {
        matchesWon++;
    }

    public int getMatchesPlayed() {
        return matchesPlayed;
    }

    public int getMatchesWon() {
        return matchesWon;
    }

    public String getPlayerName() {
        return gamePlayer.getPlayerName();
    }

    @Override
    public String toString() {
        return getPlayerName() + " [" + skillLevel + ", Matches: " + matchesWon + "/" + matchesPlayed + "]";
    }
}
