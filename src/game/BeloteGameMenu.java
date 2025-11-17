
package game;

/**
 *
 * @author NAOMI
 */


import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * BELOTE GAME MENU - Interactive menu for Belote game
 */
public class BeloteGameMenu {
    private BeloteGame game;
    private Scanner scanner;
    private int currentTeam;

    /**
     * Constructor
     */
    public BeloteGameMenu() {
        this.game = new BeloteGame();
        this.scanner = new Scanner(System.in);
        this.currentTeam = 1;
    }

    /**
     * Show menu
     */
    public void showMenu() {
        boolean running = true;

        while (running) {
            System.out.println("\nBelote Game Menu:");
            System.out.println("1. Add Player");
            System.out.println("2. Start Game");
            System.out.println("3. Play Game");
            System.out.println("4. Reset Game");
            System.out.println("5. Exit");
            System.out.print("Choose: ");

            int choice = getIntInput();

            switch (choice) {
                case 1 -> addPlayer();
                case 2 -> startGame();
                case 3 -> playGame();
                case 4 -> game.resetGame();
                case 5 -> running = false;
            }
        }
    }

    /**
     * Add player
     */
    private void addPlayer() {
        System.out.print("Enter player name: ");
        String name = scanner.nextLine();

        game.addPlayer(name, currentTeam);

        // Alternate teams
        currentTeam = (currentTeam == 1) ? 2 : 1;
    }

    /**
     * Start game
     */
    private void startGame() {
        try {
            game.startGame();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Play game
     */
    private void playGame() {
    if (!game.isGameStarted()) {
        System.out.println("Game not started!");
        return;
    }

    boolean playing = true;
    while (playing) {
        System.out.println("\n1. Play Round\n2. Show Status\n3. Back");
        System.out.print("Choose: ");

        int choice = getIntInput();

        switch (choice) {
            case 1 -> game.playRound();
            case 2 -> game.showGameStatus();
            case 3 -> {
                saveGameResult();  // ADD THIS LINE
                playing = false;
            }
        }
    }
}
    /**
 * Save game result to file
 */
    private void saveGameResult() {
    try {
        Files.createDirectories(Paths.get("data"));
    } catch (IOException e) {
        System.out.println("Error creating data directory: " + e.getMessage());
    }

    try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/games.csv", true))) {
        String team1List = game.getTeam1().stream()
            .map(p -> p.getPlayerName())
            .reduce((a, b) -> a + "|" + b)
            .orElse("Unknown");
        String team2List = game.getTeam2().stream()
            .map(p -> p.getPlayerName())
            .reduce((a, b) -> a + "|" + b)
            .orElse("Unknown");

        int score1 = game.getTeam1().stream()
            .mapToInt(p -> p.getScore())
            .sum();
        int score2 = game.getTeam2().stream()
            .mapToInt(p -> p.getScore())
            .sum();

        String winner = score1 > score2 ? team1List : team2List;

        String line = String.format("%s,%s,%s,%d,%d,%s",
            team1List, team2List, winner, score1, score2, new java.util.Date());
        
        writer.write(line);
        writer.newLine();
        System.out.println("Game result saved to file");
    } catch (IOException e) {
        System.out.println("Error saving game: " + e.getMessage());
    }
}

    private int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }
}