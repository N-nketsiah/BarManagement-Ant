
package Belote;

/**
 *
 * @author NAOMI
 */

/**
 * CARD CLASS - Represents a playing card
 */
public class Card {
    private Suit suit;
    private Rank rank;

    /**
     * Constructor
     * @param suit the card suit
     * @param rank the card rank
     */
    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    /**
     * Get suit
     * @return the suit
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * Get rank
     * @return the rank
     */
    public Rank getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}