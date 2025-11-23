package tournament;

import pubmanagement.Bar;
import pubmanagement.Client;
import pubmanagement.Server;
import pubmanagement.Human;
import utils.SkillLevel;
import game.GamePlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Tournament Menu connected to Bar actors.
 * - Uses only real Clients/Servers from the bar
 * - Each player can only be used once
 * - Supports skill levels, popularity, pre-match drinks
 */
public class TournamentMenu {

    private Scanner sc = new Scanner(System.in);
    private Tournament tournament;
    private Bar bar;
    private ArrayList<Client> clients;

    // Track names already used in the tournament
    private ArrayList<String> used = new ArrayList<>();

    public TournamentMenu(Bar bar, List<Client> clients) {
        this.bar = bar;
        this.clients = new ArrayList<>(clients);
    }

    /**
     * Main menu for tournament setup.
     */
    public void showMenu() {

        System.out.println("\n--- TOURNAMENT SETUP ---");
        System.out.print("Tournament name: ");
        tournament = new Tournament(sc.nextLine(), bar);   // <-- FIXED

        boolean run = true;

        while (run) {
            System.out.println("\n1. Register Team");
            System.out.println("2. View Teams");
            System.out.println("3. Start Tournament");
            System.out.println("4. Exit");
            System.out.print("Choose: ");

            int c = getInt(1, 4);

            switch (c) {
                case 1 -> registerTeam();
                case 2 -> viewTeams();
                case 3 -> startTournament();
                case 4 -> run = false;
            }
        }
    }

    /**
     * Register a new team (2 players).
     */
    private void registerTeam() {

        int remaining = getRemainingAvailablePlayers();

        if (remaining < 2) {
            System.out.println("\n❗ Not enough players to form a new team!");
            System.out.println("Add more Clients/Servers to the bar first.");
            return;
        }

        System.out.print("Team name: ");
        Team t = new Team(sc.nextLine());

        for (int i = 1; i <= 2; i++) {
            System.out.println("\nSelect Player " + i);
            TournamentPlayer tp = choosePlayer();
            if (tp == null) return;
            t.addPlayer(tp);
        }

        try {
            tournament.addTeam(t);
            System.out.println("Team added successfully!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Select a Client or Server who has not been used yet.
     */
    private TournamentPlayer choosePlayer() {

        ArrayList<Object> available = new ArrayList<>();

        // --- CLIENTS ---
        System.out.println("\n--- CLIENTS ---");
        for (Client c : clients) {
            if (!used.contains(c.getFirstName())) {
                available.add(c);
                System.out.println(available.size() + ". " + c.getFirstName());
            }
        }

        // --- SERVERS ---
        System.out.println("\n--- SERVERS ---");
        for (Server s : bar.getServers()) {
            if (!used.contains(s.getFirstName())) {
                available.add(s);
                System.out.println(available.size() + ". " + s.getFirstName());
            }
        }

        if (available.isEmpty()) {
            System.out.println("\n❗ No available players left!");
            return null;
        }

        System.out.print("Choose: ");
        int choice = getInt(1, available.size());

        Object selected = available.get(choice - 1);
        GamePlayer gp;
        Human human;
        String name;

        if (selected instanceof Client c) {
            gp = new GamePlayer(c.getFirstName());
            human = c;
            name = c.getFirstName();
        } else {
            Server s = (Server) selected;
            gp = new GamePlayer(s.getFirstName());
            human = s;
            name = s.getFirstName();
        }

        used.add(name);

        // Pick skill level
        System.out.println("\nSkill level:");
        System.out.println("1. BEGINNER");
        System.out.println("2. INTERMEDIATE");
        System.out.println("3. ADVANCED");
        System.out.println("4. EXPERT");

        SkillLevel level = SkillLevel.values()[getInt(1, 4) - 1];

        return new TournamentPlayer(gp, human, level);
    }

    /**
     * View all registered teams.
     */
    private void viewTeams() {
        System.out.println("\nREGISTERED TEAMS:");
        for (Team t : tournament.getTeams()) {
            System.out.println("\nTeam: " + t.getName());
            for (TournamentPlayer p : t.getPlayers())
                System.out.println("  - " + p);
        }
    }

    /**
     * Start the tournament.
     */
    private void startTournament() {
        try {
            tournament.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Validate input range
     */
    private int getInt(int min, int max) {
        while (true) {
            try {
                int n = Integer.parseInt(sc.nextLine());
                if (n >= min && n <= max) return n;
            } catch (Exception ignored) {}
            System.out.print("Invalid. Try again: ");
        }
    }

    /**
     * Count how many players are left unused.
     */
    private int getRemainingAvailablePlayers() {
        int count = 0;

        for (Client c : clients)
            if (!used.contains(c.getFirstName())) count++;

        for (Server s : bar.getServers())
            if (!used.contains(s.getFirstName())) count++;

        return count;
    }
}
