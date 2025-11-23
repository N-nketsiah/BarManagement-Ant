package game;

import Belote.Hand;

/**
 * GAMEPLAYER CLASS - Used by simplified Belote engine
 * This version matches the new Hand.java (no name argument)
 */
public class GamePlayer {

    private String name;
    private Hand hand;
    private int tricksWon;

    public GamePlayer(String name) {
        this.name = name;
        this.hand = new Hand();   // <-- FIXED
        this.tricksWon = 0;
    }

    public String getName() {
        return name;
    }

    public Hand getHand() {
        return hand;
    }

    public void addTrick() {
        tricksWon++;
    }

    public int getTricks() {
        return tricksWon;
    }

    @Override
    public String toString() {
        return name + " (Tricks won: " + tricksWon + ")";
    }
}
