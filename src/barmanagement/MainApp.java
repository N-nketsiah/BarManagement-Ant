package barmanagement;

import pubmanagement.*;
import game.BeloteGameMenu;
import tournament.TournamentMenu;
import utils.Gender;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainApp {

    private static Scanner scanner = new Scanner(System.in);
    private static Bar bar;
    private static List<Client> clients = new ArrayList<>();
    private static Supplier supplier;

    public static void main(String[] args) throws Exception {
        setupBar();
        mainMenu();
    }

    private static void setupBar() throws Exception {

        Patron patron = new Patron("Naomi", "Boss", 5000, "Welcome!", "Chez Naomi");
        Barman barman = new Barman("Amine", "Chief", 500, "Cheers!");

        bar = new Bar("Chez Naomi", patron, barman);

        // Default servers
        bar.addServer(new Server("Alice", "Ali", 200, "Yes!", Gender.FEMALE, 8));
        bar.addServer(new Server("John", "Strong", 200, "Ready!", Gender.MALE, 6));

        // Default stock
        bar.addBeverages(new Beverage("Beer", 5.0, 2.0, true, 2), 50);
        bar.addBeverages(new Beverage("Wine", 6.0, 2.5, true, 3), 30);
        bar.addBeverages(new Beverage("Water", 1.0, 0.3, false, 0), 100);

        supplier = new Supplier("Pierre", "Pete", 0.0, "Ready!");

        // Load clients from file
        clients = ClientMenu.loadClientsFromFile();

        // Add loaded clients into the bar so Belote + Tournament can see them
        for (Client c : clients) {
            bar.getClients().add(c); 
        }

        System.out.println(" Bar setup complete!\n");
    }

    private static void mainMenu() throws Exception {
        boolean running = true;

        while (running) {
            System.out.println("=====================================");
            System.out.println("  BAR MANAGEMENT & BELOTE TOURNAMENT");
            System.out.println("=====================================");
            System.out.println("\nPlease select an option:");
            System.out.println("1. Manage Pub");
            System.out.println("2. Play Belote Game");
            System.out.println("3. Play Belote Tournament");
            System.out.println("4. Quit");
            System.out.print("Choose: ");

            int choice = getIntInput(1, 4);

            switch (choice) {
                case 1 -> managePub();
                case 2 -> new BeloteGameMenu(bar).showMenu();
                case 3 -> new TournamentMenu(bar, clients).showMenu();
                case 4 -> {
                    System.out.println("\nThanks for playing! Goodbye.");
                    running = false;
                }
            }
        }
    }

    private static void managePub() throws Exception {
        System.out.println("\nPlease choose your role:");
        System.out.println("1. Patron");
        System.out.println("2. Barman");
        System.out.println("3. Server");
        System.out.println("4. Supplier");
        System.out.println("5. Client");
        System.out.println("6. Quit");
        System.out.print("Choose: ");

        int role = getIntInput(1, 6);

        switch (role) {
            case 1 -> new PatronMenu(bar, clients).showMenu();
            case 2 -> new BarmanMenu(bar, clients).showMenu();
            case 3 -> new ServerMenu(bar, clients).showMenu();
            case 4 -> new SupplierMenu(bar, supplier).showMenu();
            case 5 -> new ClientMenu(bar, clients).showMenu();
        }
    }

    private static int getIntInput(int min, int max) {
        try {
            int input = Integer.parseInt(scanner.nextLine());
            if (input >= min && input <= max) return input;
        } catch (Exception ignored) {}
        System.out.print("Invalid! Try again: ");
        return getIntInput(min, max);
    }
}
