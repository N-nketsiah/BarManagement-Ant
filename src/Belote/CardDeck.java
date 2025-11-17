
package Belote;

/**
 *
 * @author NAOMI
 */
import java.util.ArrayList;
import java.util.Collections;

/**
 * CARDDECK CLASS - Represents a full deck of 52 playing cards
 */
public class CardDeck {
    private ArrayList<Card> cards;
    
    /**
     * Constructor - Create and shuffle a full deck
     */
    public CardDeck() {
        this.cards = new ArrayList<>();
        createDeck();
        shuffle();
    }
    
    /**
     * Create all 52 cards (4 suits × 13 ranks)
     */
    private void createDeck() {
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
    }
    
    /**
     * Shuffle the deck randomly
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }
    
    /**
     * Draw a card from the deck
     * @return the card drawn, or null if deck is empty
     */
    public Card drawCard() {
        if (cards.isEmpty()) {
            return null;
        }
        return cards.remove(0);
    }
    
    /**
     * Check if deck is empty
     * @return true if no cards left
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }
    
    /**
     * Get number of remaining cards
     * @return number of cards left in deck
     */
    public int getRemainingCards() {
        return cards.size();
    }
    
    /**
     * Print deck info
     */
    @Override
    public String toString() {
        return "CardDeck [" + cards.size() + " cards remaining]";
    }
}
