
package Belote;

/**
 *
 * @author NAOMI
 */

import java.util.ArrayList;

/**
 * HAND CLASS - Represents cards held by a player
 */
public class Hand {
    private ArrayList<Card> cards;
    private String playerName;

    /**
     * Constructor
     */
    public Hand(String playerName) {
        this.playerName = playerName;
        this.cards = new ArrayList<>();
    }

    /**
     * Add card
     */
    public void addCard(Card card) {
        cards.add(card);
    }

    /**
     * Play card at index
     */
    public Card playCard(int index) {
        if (index < 0 || index >= cards.size()) {
            return null;
        }
        return cards.remove(index);
    }

    /**
     * Get card at index
     */
    public Card getCard(int index) {
        if (index < 0 || index >= cards.size()) {
            return null;
        }
        return cards.get(index);
    }

    /**
     * Get card count
     */
    public int getCardCount() {
        return cards.size();
    }

    /**
     * Check if empty
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    /**
     * Get all cards
     */
    public ArrayList<Card> getCards() {
        return new ArrayList<>(cards);
    }

    /**
     * Clear hand
     */
    public void clear() {
        cards.clear();
    }

    @Override
    public String toString() {
        return playerName + "'s hand: " + cards.size() + " cards";
    }
}
