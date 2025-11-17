
package game;

/**
 *
 * @author NAOMI
 * /



/**
 * GAMEPLAYER CLASS - Wraps a Client to play Belote
 */
import pubmanagement.Client;
import Belote.Hand;

public class GamePlayer implements Playable {
    private Client client;
    private Hand hand;
    private int score;
    private int roundsWon;
    private boolean isPlaying;

    /**
     * Constructor
     * @param client 
     */
    public GamePlayer(Client client) {
        this.client = client;
        this.hand = new Hand(client.getFirstName());
        this.score = 0;
        this.roundsWon = 0;
        this.isPlaying = false;
    }

    @Override
    public void playGame() {
        isPlaying = true;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public boolean canPlay() {
        return isPlaying && !hand.isEmpty();
    }

    @Override
    public String getPlayerName() {
        return client.getFirstName();
    }

    @Override
    public void winRound() {
        roundsWon++;
        score += 10;
    }

    @Override
    public void loseRound() {
        // no points lost
    }

    public Client getClient() {
        return client;
    }

    public Hand getHand() {
        return hand;
    }

    public int getRoundsWon() {
        return roundsWon;
    }

    public void addScore(int points) {
        score += points;
    }

    public void resetGame() {
        score = 0;
        roundsWon = 0;
        hand.clear();
        isPlaying = false;
    }

    @Override
    public String toString() {
        return getPlayerName() + " [Score: " + score + ", Rounds: " + roundsWon + "]";
    }
}