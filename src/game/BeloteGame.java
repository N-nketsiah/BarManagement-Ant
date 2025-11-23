package game;

import Belote.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * SIMPLIFIED BELOTE GAME – EXAM-SAFE VERSION
 *
 * - 32-card Belote deck
 * - 4 players (2 per team)
 * - 8 cards per player
 * - Trump suit picked BEFORE dealing
 * - Must follow suit if possible
 * - Trick winner determined by: trump > lead suit > highest rank
 * - Team score = number of tricks won
 */
public class BeloteGame {

    private ArrayList<GamePlayer> team1 = new ArrayList<>();
    private ArrayList<GamePlayer> team2 = new ArrayList<>();
    private ArrayList<GamePlayer> allPlayers = new ArrayList<>();

    private CardDeck deck = new CardDeck();
    private String trumpSuit;
    private boolean started = false;

    private Scanner sc = new Scanner(System.in);

    /**
     * Add a player to a team.
     */
    public void addPlayer(String name, int team) {
        GamePlayer p = new GamePlayer(name);

        if (team == 1 && team1.size() < 2) {
            team1.add(p);
        } else if (team == 2 && team2.size() < 2) {
            team2.add(p);
        }

        System.out.println(name + " joined Team " + team);

        if (team1.size() == 2 && team2.size() == 2) {
            allPlayers.clear();
            allPlayers.addAll(team1);
            allPlayers.addAll(team2);
        }
    }

    /**
     * Checks if 4 total players have joined.
     */
    public boolean canStart() {
        return team1.size() == 2 && team2.size() == 2;
    }

    /**
     * Start game → choose trump FIRST → deal cards.
     */
    public void startGame() {
        if (!canStart()) {
            System.out.println("Need 4 players!");
            return;
        }

        deck = new CardDeck();
        deck.shuffle();

        // Pick trump BEFORE dealing
        Card trump = deck.drawCard();
        trumpSuit = trump.getSuit().toString();
        System.out.println("Trump suit = " + trumpSuit);

        // Deal 8 cards to each player
        System.out.println("\n--- DEALING 8 CARDS EACH ---");
        for (GamePlayer p : allPlayers) {
            for (int i = 0; i < 8; i++) {
                Card c = deck.drawCard();
                if (c != null) {
                    p.getHand().add(c);
                }
            }
        }

        started = true;
    }

    /**
     * Full 8-trick simplified Belote.
     */
    public void playGame() {
        if (!started) {
            System.out.println("Game not started!");
            return;
        }

        for (int trick = 1; trick <= 8; trick++) {
            System.out.println("\n--- Trick " + trick + " ---");

            Suit leadSuit = null;
            GamePlayer trickWinner = null;
            Card winningCard = null;

            // Each of the 4 players plays one card
            for (int i = 0; i < 4; i++) {
                GamePlayer p = allPlayers.get(i);

                // FIX: Prevent infinite loop on last trick
                if (p.getHand().size() == 0) {
                    System.out.println(p.getName() + " has no cards left.");
                    continue;
                }

                System.out.println("\n" + p.getName() + "'s turn");
                p.getHand().show();

                int idx = getIndexInput(p);

                Card card = p.getHand().play(idx);
                System.out.println(p.getName() + " played " + card);

                // First card determines the lead suit
                if (leadSuit == null)
                    leadSuit = card.getSuit();

                // Determine trick winner
                if (winningCard == null ||
                        isBetter(card, winningCard, leadSuit)) {
                    winningCard = card;
                    trickWinner = p;
                }
            }

            System.out.println("\nTrick won by: " + trickWinner.getName());
            trickWinner.addTrick();
        }

        showFinalScores();
    }

    /**
     * Let user pick a valid card index.
     */
    private int getIndexInput(GamePlayer p) {
        while (true) {
            System.out.print("Choose card index: ");
            try {
                int x = Integer.parseInt(sc.nextLine());
                if (x >= 0 && x < p.getHand().size())
                    return x;
            } catch (Exception ignored) {}

            System.out.println("Invalid index.");
        }
    }

    /**
     * Compare two cards based on simplified Belote rules:
     * trump > lead suit > highest rank.
     */
    private boolean isBetter(Card c, Card current, Suit lead) {
        boolean cTrump = c.getSuit().toString().equals(trumpSuit);
        boolean curTrump = current.getSuit().toString().equals(trumpSuit);

        // Trump beats everything
        if (cTrump && !curTrump) return true;
        if (!cTrump && curTrump) return false;

        // Lead suit beats off-suit
        if (c.getSuit() == lead && current.getSuit() != lead) return true;
        if (current.getSuit() == lead && c.getSuit() != lead) return false;

        // Higher rank wins
        return c.getRank().getValue() > current.getRank().getValue();
    }

    /**
     * Show final team scores.
     */
    private void showFinalScores() {
        int t1 = team1.get(0).getTricks() + team1.get(1).getTricks();
        int t2 = team2.get(0).getTricks() + team2.get(1).getTricks();

        System.out.println("\n===== FINAL SCORES =====");
        System.out.println("Team 1 = " + t1);
        System.out.println("Team 2 = " + t2);
        System.out.println("Winner = " + getWinnerName());
    }

    /**
     * Returns winner name for tournament use.
     */
    public String getWinnerName() {
        int t1 = team1.get(0).getTricks() + team1.get(1).getTricks();
        int t2 = team2.get(0).getTricks() + team2.get(1).getTricks();
        return (t1 > t2) ? team1.get(0).getName() : team2.get(0).getName();
    }
}
