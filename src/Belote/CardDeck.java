package Belote;

import java.util.ArrayList;
import java.util.Collections;

/**
 * CARDDECK CLASS – 32-card Belote deck
 */
public class CardDeck {
    private ArrayList<Card> cards;

    public CardDeck() {
        cards = new ArrayList<>();
        createDeck();
        shuffle();
    }

    /**
     * Create the 32-card Belote deck (4 suits × 8 ranks)
     */
    private void createDeck() {
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        if (cards.isEmpty()) return null;
        return cards.remove(0);
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public int getRemainingCards() {
        return cards.size();
    }

    @Override
    public String toString() {
        return "CardDeck [" + cards.size() + " cards remaining]";
    }
}
