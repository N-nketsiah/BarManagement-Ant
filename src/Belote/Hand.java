package Belote;

import java.util.ArrayList;

/**
 * HAND CLASS - Stores cards for each player.
 * Required by simplified Belote game.
 */
public class Hand {

    private ArrayList<Card> cards = new ArrayList<>();

    /**
     * Add a card to the hand.
     */
    public void add(Card c) {
        cards.add(c);
    }

    /**
     * Play (remove) a card at given index.
     */
    public Card play(int index) {
        if (index < 0 || index >= cards.size()) return null;
        return cards.remove(index);
    }

    /**
     * Get number of cards.
     */
    public int size() {
        return cards.size();
    }

    /**
     * Get the whole list of cards.
     */
    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     * Show cards with index numbers.
     */
    public void show() {
        System.out.println("Your cards:");
        for (int i = 0; i < cards.size(); i++) {
            System.out.println(i + " - " + cards.get(i));
        }
    }
}
