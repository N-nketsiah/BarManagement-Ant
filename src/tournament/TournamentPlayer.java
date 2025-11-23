package tournament;

import game.GamePlayer;
import pubmanagement.Human;
import utils.SkillLevel;

/**
 * TournamentPlayer wraps BOTH:
 * - The GamePlayer used for Belote
 * - The real Human (Client or Server) used in the bar
 */
public class TournamentPlayer {

    private GamePlayer gamePlayer;
    private Human human;   // <-- NEW
    private SkillLevel level;
    private int matchesPlayed;
    private int matchesWon;

    public TournamentPlayer(GamePlayer gp, Human human, SkillLevel level) {
        this.gamePlayer = gp;
        this.human = human;
        this.level = level;
        this.matchesPlayed = 0;
        this.matchesWon = 0;
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public Human getHuman() {      // <-- NEW
        return human;
    }

    public String getName() {
        return gamePlayer.getName();
    }

    public SkillLevel getSkillLevel() {
        return level;
    }

    public void recordPlayed() { matchesPlayed++; }
    public void recordWin() { matchesWon++; }

    @Override
    public String toString() {
        return getName() + " (" + level + ")";
    }
}
