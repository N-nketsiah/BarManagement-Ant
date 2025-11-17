
package game;

/**
 *
 * @author NAOMI
 * PLAYABLE INTERFACE - Contract for anything that can play Belote
 */
public interface Playable {
    
    /**
     * Start playing
     */
    void playGame();
    
    /**
     * Get player's score
     * @return the score
     */
    int getScore();
    
    /**
     * Check if player can still play
     * @return true if can play
     */
    boolean canPlay();
    
    /**
     * Get player name
     * @return the name
     */
    String getPlayerName();
    
    /**
     * Player wins a round
     */
    void winRound();
    
    /**
     * Player loses a round
     */
    void loseRound();
}
