package game;

import Belote.Card;
import Belote.CardDeck;
import Belote.Hand;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * BELOTEGAME CLASS - Full Belote Game with Teams, Dealing, Trump Suit
 */
public class BeloteGame {
    private ArrayList<GamePlayer> team1;
    private ArrayList<GamePlayer> team2;
    private CardDeck deck;
    private int dealerIndex;
    private Card trumpCard;
    private String trumpSuit;
    private Scanner scanner;
    private boolean gameStarted;

    /**
     * Constructor
     */
    public BeloteGame() {
        this.team1 = new ArrayList<>();
        this.team2 = new ArrayList<>();
        this.deck = new CardDeck();
        this.dealerIndex = 0;
        this.scanner = new Scanner(System.in);
        this.gameStarted = false;
    }

    /**
     * Add player to team
     */
    public void addPlayer(String playerName, int teamNumber) {
        GamePlayer player = new GamePlayer(new pubmanagement.Client(
            playerName, playerName, 100, "Cheers!", 
            utils.Gender.MALE, 
            new pubmanagement.Beverage("Beer", 5, 2, true, 2),
            new pubmanagement.Beverage("Wine", 6, 2.5, true, 3),
            "Shirt"
        ));

        if (teamNumber == 1) {
            team1.add(player);
            System.out.println(playerName + " joined Team 1.");
        } else {
            team2.add(player);
            System.out.println(playerName + " joined Team 2.");
        }
    }

    /**
     * Check if game can start
     */
    public boolean canStartGame() {
        return team1.size() == 2 && team2.size() == 2;
    }

    /**
     * Start game - shuffle, cut, deal
     */
    public void startGame() throws Exception {
        if (!canStartGame()) {
            System.out.println("Each team must have 2 players to start the game.");
            return;
        }

        System.out.println("\nDeck reset.");
        deck = new CardDeck();

        System.out.println("Deck reset.");
        System.out.println("Deck shuffled.");

        // Cut deck
        int cutPosition = 5;
        System.out.println("Deck cut at position " + cutPosition + ".");

        // Select dealer
        GamePlayer dealer = getDealer();
        System.out.println("Dealer is " + dealer.getPlayerName());

        // Deal cards
        System.out.println("Dealing cards...");
        dealCards(dealer);

        // Trump suit negotiation
        proposeTrumpSuit(dealer);

        gameStarted = true;
        System.out.println("\nGame started with Team 1 vs. Team 2. Select 'Play Game' to play");
    }

    /**
     * Get dealer
     */
    private GamePlayer getDealer() {
        ArrayList<GamePlayer> allPlayers = new ArrayList<>();
        allPlayers.addAll(team1);
        allPlayers.addAll(team2);

        if (dealerIndex >= allPlayers.size()) {
            dealerIndex = 0;
        }
        return allPlayers.get(dealerIndex);
    }

    /**
     * Deal cards - first 3, then 2
     */
    private void dealCards(GamePlayer dealer) {
        ArrayList<GamePlayer> allPlayers = new ArrayList<>();
        allPlayers.addAll(team1);
        allPlayers.addAll(team2);

        // Find dealer position
        int dealerPos = allPlayers.indexOf(dealer);

        // First round: 3 cards each
        System.out.println("First round: 3 cards each");
        for (int i = 0; i < 3; i++) {
            for (int p = 0; p < 4; p++) {
                int playerPos = (dealerPos + 1 + p) % 4;
                if (!deck.isEmpty()) {
                    allPlayers.get(playerPos).getHand().addCard(deck.drawCard());
                }
            }
        }
        for (GamePlayer p : allPlayers) {
            System.out.println(p.getPlayerName() + " received cards.");
        }

        // Second round: 2 cards each
        System.out.println("First round: 2 cards each");
        for (int i = 0; i < 2; i++) {
            for (int p = 0; p < 4; p++) {
                int playerPos = (dealerPos + 1 + p) % 4;
                if (!deck.isEmpty()) {
                    allPlayers.get(playerPos).getHand().addCard(deck.drawCard());
                }
            }
        }
        for (GamePlayer p : allPlayers) {
            System.out.println(p.getPlayerName() + " received cards.");
        }

        // Flip trump card
        if (!deck.isEmpty()) {
            trumpCard = deck.drawCard();
            System.out.println("Card flipped: " + trumpCard);
            trumpSuit = trumpCard.getSuit().toString();
            System.out.println("Proposed trump suit is: " + trumpSuit);
        }
    }

    /**
     * Propose trump suit
     */
    private void proposeTrumpSuit(GamePlayer dealer) {
        ArrayList<GamePlayer> allPlayers = new ArrayList<>();
        allPlayers.addAll(team1);
        allPlayers.addAll(team2);

        int dealerPos = allPlayers.indexOf(dealer);
        GamePlayer nextPlayer = allPlayers.get((dealerPos + 1) % 4);

        System.out.println(nextPlayer.getPlayerName() + ", do you accept the trump suit " + trumpSuit + "? (1. Yes, 2. No)");
        System.out.print("Choice: ");

        int choice = getIntInput();

        if (choice == 1) {
            System.out.println(nextPlayer.getPlayerName() + " accepted the trump suit.");
            // Give extra card
            if (!deck.isEmpty()) {
                nextPlayer.getHand().addCard(deck.drawCard());
                System.out.println(nextPlayer.getPlayerName() + " received cards.");
            }
            if (!deck.isEmpty()) {
                dealer.getHand().addCard(deck.drawCard());
                System.out.println(dealer.getPlayerName() + " received cards.");
            }

            // Distribute remaining
            ArrayList<GamePlayer> others = new ArrayList<>(allPlayers);
            others.remove(nextPlayer);
            others.remove(dealer);

            for (GamePlayer p : others) {
                if (!deck.isEmpty()) {
                    p.getHand().addCard(deck.drawCard());
                    System.out.println(p.getPlayerName() + " received cards.");
                }
            }
        } else {
            System.out.println(nextPlayer.getPlayerName() + " rejected the trump suit.");
            // Pass to next player
            proposeTrumpSuit(allPlayers.get((dealerPos + 2) % 4));
        }

        System.out.println("Trump suit is: " + trumpSuit);
    }

    /**
     * Play game rounds
     */
    public void playRound() {
        if (!gameStarted) {
            System.out.println("Game not started!");
            return;
        }

        ArrayList<GamePlayer> allPlayers = new ArrayList<>();
        allPlayers.addAll(team1);
        allPlayers.addAll(team2);

        System.out.println("\n--- Playing Round ---");

        // Simple logic: highest card wins
        int maxValue = 0;
        GamePlayer winner = null;

        for (GamePlayer player : allPlayers) {
            if (!player.getHand().isEmpty()) {
                int value = 0;
                for (Card card : player.getHand().getCards()) {
                    value += card.getRank().getValue();
                }
                if (value > maxValue) {
                    maxValue = value;
                    winner = player;
                }
            }
        }

        if (winner != null) {
            winner.winRound();
            System.out.println(winner.getPlayerName() + " won the round!");
        }
    }

    /**
     * Show game status
     */
    public void showGameStatus() {
        System.out.println("\n─── GAME STATUS ───");
        System.out.println("Team 1:");
        for (GamePlayer p : team1) {
            System.out.println("  " + p.toString());
        }
        System.out.println("Team 2:");
        for (GamePlayer p : team2) {
            System.out.println("  " + p.toString());
        }
    }

    /**
     * Reset game
     */
    public void resetGame() {
        team1.clear();
        team2.clear();
        deck = new CardDeck();
        gameStarted = false;
        System.out.println("Game reset.");
    }

    public ArrayList<GamePlayer> getTeam1() {
        return team1;
    }

    public ArrayList<GamePlayer> getTeam2() {
        return team2;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    private int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }
}