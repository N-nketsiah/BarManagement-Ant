package pubmanagement;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import utils.Gender;

public class ClientMenu {
    private Scanner scanner;
    private Bar bar;
    private List<Client> clients;
    private static final String CLIENTS_FILE = "data/clients.csv";

    public ClientMenu(Bar bar, List<Client> clients) {
        this.bar = bar;
        this.clients = clients;
        this.scanner = new Scanner(System.in);
        createDataDirectory();
    }

    private void createDataDirectory() {
        try {
            Files.createDirectories(Paths.get("data"));
        } catch (IOException e) {
            System.out.println("Error creating data directory: " + e.getMessage());
        }
    }

    public void showMenu() {
        System.out.println("\nYou are a Client. Choose a client or create a new one:");
        System.out.println("0. Create a new client");
        for (int i = 0; i < clients.size(); i++) {
            System.out.println((i + 1) + ". " + clients.get(i).getFirstName());
        }
        System.out.print("Choose: ");

        int clientChoice = getIntInput(0, clients.size());
        Client chosenClient;

        if (clientChoice == 0) {
            chosenClient = createNewClient();
            if (chosenClient != null) {
                clients.add(chosenClient);
                bar.addClient(chosenClient);
                saveClientToFile(chosenClient);
                System.out.println(" Client saved!");
            } else {
                System.out.println("Failed to create a new client.");
                return;
            }
        } else {
            chosenClient = clients.get(clientChoice - 1);
        }

        clientActions(chosenClient);
    }

    // ---------------------------- FILE HANDLING -------------------------------

    private void saveClientToFile(Client client) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CLIENTS_FILE, true))) {
            String line = String.format("%s,%s,%f,%s,%s",
                client.getFirstName(),
                client.getNickname(),
                client.getWallet(),
                client.getGender(),
                client.getSignificantShout()
            );
            writer.write(line);
            writer.newLine();
            System.out.println(" Client saved to file");
        } catch (IOException e) {
            System.out.println("Error saving client: " + e.getMessage());
        }
    }

    public static List<Client> loadClientsFromFile() {
        List<Client> loadedClients = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("data/clients.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String firstName = parts[0];
                    String nickname = parts[1];
                    double wallet = Double.parseDouble(parts[2]);
                    Gender gender = Gender.valueOf(parts[3]);
                    String shout = parts[4];

                    Client client = new Client(
                        firstName, nickname, wallet, shout, gender,
                        new Beverage("Beer", 5, 2, true, 2),
                        new Beverage("Wine", 6, 2.5, true, 3),
                        "Default"
                    );
                    loadedClients.add(client);
                }
            }
        } catch (IOException e) {
            System.out.println("No previous clients found");
        }
        return loadedClients;
    }

    // ---------------------------- CLIENT ACTIONS -----------------------------

    private void clientActions(Client client) {
        boolean inMenu = true;

        while (inMenu) {
            System.out.println("\nWhat would you like to do?");
            System.out.println("1. View Profile");
            System.out.println("2. Order a drink");
            System.out.println("3. Pay for a drink");
            System.out.println("4. Change gender");
            System.out.println("5. View Table Assignments");   // <-- NEW OPTION
            System.out.println("6. Quit to main menu");
            System.out.print("Choose: ");

            int action = getIntInput(1, 6);

            switch (action) {
                case 1 -> viewProfile(client);
                case 2 -> {
                    Beverage drink = selectBeverage();
                    if (drink != null) {
                        client.receiveDrink(drink);
                    }
                }
                case 3 -> {
                    System.out.print("Enter amount to pay: ");
                    double amount = Double.parseDouble(scanner.nextLine());
                    client.pay(amount);
                }
                case 4 -> changeGender(client);

                case 5 -> bar.showTables();       

                case 6 -> inMenu = false;
            }
        }
    }

    private void viewProfile(Client client) {
        System.out.println("\n--- Client Profile ---");
        System.out.println("Name: " + client.getFirstName() + " " + client.getNickname());
        System.out.println("Gender: " + client.getGender());
        System.out.println("Balance: $" + client.getWallet());
        System.out.println("Favorite Drink: " + client.getFavoriteDrink().getName());
        System.out.println("Backup Drink: " + client.getBackupDrink().getName());
        System.out.println("Significant Shout: " + client.getSignificantShout());
        System.out.println("Clothing/Jewelry: " + client.getClothingOrJewelry());
        System.out.println("----------------------\n");
    }

    private void changeGender(Client client) {
        System.out.println("Choose your new gender:");
        System.out.println("1. Male");
        System.out.println("2. Female");
        System.out.print("Choose: ");

        int genderChoice = getIntInput(1, 2);
        Gender newGender = (genderChoice == 1) ? Gender.MALE : Gender.FEMALE;

        System.out.print("Enter new clothing/jewelry description: ");
        String newClothing = scanner.nextLine();
        if (newClothing.isEmpty())
            newClothing = (newGender == Gender.MALE) ? "T-shirt" : "Necklace";

        client.changeGender(newGender, newClothing);
        System.out.println(" Gender changed!");
    }

    private Client createNewClient() {
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        if (firstName.isEmpty()) firstName = "Anonymous";

        System.out.print("Enter nickname: ");
        String nickname = scanner.nextLine();
        if (nickname.isEmpty()) nickname = "Anon";

        System.out.print("Enter wallet balance: ");
        double wallet = Double.parseDouble(scanner.nextLine());

        System.out.print("Enter significant shout: ");
        String shout = scanner.nextLine();
        if (shout.isEmpty()) shout = "Cheers!";

        System.out.println("Choose gender:");
        System.out.println("1. Male");
        System.out.println("2. Female");
        int genderChoice = getIntInput(1, 2);
        Gender gender = (genderChoice == 1) ? Gender.MALE : Gender.FEMALE;

        System.out.print("Enter clothing or jewelry description: ");
        String clothingOrJewelry = scanner.nextLine();
        if (clothingOrJewelry.isEmpty())
            clothingOrJewelry = (gender == Gender.MALE) ? "T-shirt" : "Necklace";

        System.out.println("Choose favorite drink:");
        Beverage favoriteDrink = selectBeverage();

        System.out.println("Choose backup drink:");
        Beverage backupDrink = selectBeverage();

        if (favoriteDrink == null || backupDrink == null) {
            System.out.println("Failed to choose drinks.");
            return null;
        }

        return new Client(firstName, nickname, wallet, shout, gender,
                favoriteDrink, backupDrink, clothingOrJewelry);
    }

    private Beverage selectBeverage() {
        List<Beverage> beverages = bar.getAvailableBeverages();

        if (beverages.isEmpty()) {
            System.out.println("No beverages available!");
            return null;
        }

        System.out.println("Choose a beverage:");
        for (int i = 0; i < beverages.size(); i++) {
            System.out.println((i + 1) + ". " + beverages.get(i).getName());
        }
        System.out.print("Choose: ");

        int idx = getIntInput(1, beverages.size()) - 1;
        return beverages.get(idx);
    }

    private int getIntInput(int min, int max) {
        try {
            int input = Integer.parseInt(scanner.nextLine());
            if (input >= min && input <= max) return input;
        } catch (Exception ignored) {}
        System.out.print("Invalid! Try again: ");
        return getIntInput(min, max);
    }
}
