package game;

import pubmanagement.Bar;
import pubmanagement.Client;
import pubmanagement.Server;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Belote Game Menu (Players must come from the Bar)
 * - Prevent selecting a player twice
 * - Shows only available players
 */
public class BeloteGameMenu {

    private BeloteGame game = new BeloteGame();
    private Scanner sc = new Scanner(System.in);

    private Bar bar;
    private int currentTeam = 1;
    private int playerCount = 0;
    private boolean started = false;

    // NEW: track already picked players
    private ArrayList<String> usedPlayers = new ArrayList<>();

    public BeloteGameMenu(Bar bar) {
        this.bar = bar;
    }

    public void showMenu() {

        boolean run = true;

        while (run) {
            System.out.println("\nBelote Menu:");
            System.out.println("1. Add Player");
            System.out.println("2. Start Game");
            System.out.println("3. Play");
            System.out.println("4. Exit");
            System.out.print("Choose: ");

            int c = getInt();

            switch (c) {
                case 1 -> addPlayer();
                case 2 -> start();
                case 3 -> play();
                case 4 -> run = false;
            }
        }
    }

    /**
     * Add a player only once
     */
    private void addPlayer() {
        if (playerCount >= 4) {
            System.out.println(" You already have 4 players!");
            return;
        }

        List<Object> available = new ArrayList<>();

        System.out.println("\n--- AVAILABLE CLIENTS ---");
        for (Client c : bar.getClients()) {
            if (!usedPlayers.contains(c.getFirstName())) {
                available.add(c);
                System.out.println(available.size() + ". " + c.getFirstName());
            }
        }

        System.out.println("\n--- AVAILABLE SERVERS ---");
        for (Server s : bar.getServers()) {
            if (!usedPlayers.contains(s.getFirstName())) {
                available.add(s);
                System.out.println(available.size() + ". " + s.getFirstName());
            }
        }

        if (available.isEmpty()) {
            System.out.println(" No players available. Add more clients or servers first!");
            return;
        }

        System.out.print("Choose player: ");
        int choice = getInt();

        if (choice < 1 || choice > available.size()) {
            System.out.println(" Invalid choice.");
            return;
        }

        Object selected = available.get(choice - 1);
        String name;

        if (selected instanceof Client c) {
            name = c.getFirstName();
        } else {
            Server s = (Server) selected;
            name = s.getFirstName();
        }

        // Mark player as used
        usedPlayers.add(name);

        game.addPlayer(name, currentTeam);
        currentTeam = (currentTeam == 1) ? 2 : 1;
        playerCount++;
    }

    private void start() {
        if (playerCount < 4) {
            System.out.println(" Need 4 players before starting.");
            return;
        }

        if (started) {
            System.out.println(" Game already started.");
            return;
        }

        game.startGame();
        started = true;
    }

    private void play() {
        if (!started) {
            System.out.println(" You must start the game first!");
            return;
        }

        game.playGame();
    }

    private int getInt() {
        try { return Integer.parseInt(sc.nextLine()); }
        catch (Exception e) { return -1; }
    }
}
