/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pubmanagement;

/**
 *
 * @author NAOMI
 */


import java.util.List;
import utils.Gender;
import java.util.Scanner;

/**
 * SERVER MENU - Interactive menu for Server role
 */
public class ServerMenu {
    private Scanner scanner;
    private Bar bar;
    private List<Client> clients;

    public ServerMenu(Bar bar, List<Client> clients) {
        this.bar = bar;
        this.clients = clients;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        List<Server> servers = bar.getServers();

        if (servers.isEmpty()) {
            System.out.println("No servers available.");
            return;
        }

        System.out.println("\nYou are a Server. Choose an option:");
        System.out.println("1. Select an existing server");
        System.out.println("2. Add a custom server");
        System.out.println("3. Quit to main menu");
        System.out.print("Choose: ");

        int choice = getIntInput(1, 3);

        Server chosenServer = null;

        if (choice == 1) {
            System.out.println("\nChoose a server from the available list:");
            for (int i = 0; i < servers.size(); i++) {
                System.out.println((i + 1) + ". " + servers.get(i).getFirstName());
            }
            System.out.print("Choose: ");

            int serverIndex = getIntInput(1, servers.size());
            chosenServer = servers.get(serverIndex - 1);

        } else if (choice == 2) {
            System.out.print("Enter server's first name: ");
            String firstName = scanner.nextLine();
            System.out.print("Enter server's nickname: ");
            String nickname = scanner.nextLine();
            System.out.print("Enter server's wallet balance: ");
            double wallet = Double.parseDouble(scanner.nextLine());

            chosenServer = new Server(firstName, nickname, wallet, "Ready!", Gender.MALE, 8);
            servers.add(chosenServer);
            System.out.println("✓ Custom server added!");
        }

        if (chosenServer != null) {
            serverActions(chosenServer);
        }
    }

   private void serverActions(Server server) {
    boolean inMenu = true;

    while (inMenu) {
        System.out.println("=====================================");
        System.out.println("            SERVER MENU              ");
        System.out.println("=====================================");
        System.out.println("1. View Profile");
        System.out.println("2. Take an order from a client");
        System.out.println("3. Serve a client");
        System.out.println("4. Quit to main menu");
        System.out.print("Choose: ");

        int action = getIntInput(1, 4);

        switch (action) {
            case 1 -> {
                System.out.println("\nServer Profile:");
                System.out.println("Name: " + server.getFirstName());
                System.out.println("Nickname: " + server.getNickname());
                System.out.println("Wallet: " + server.getWallet());
            }
            case 2 -> {
                Client client = selectClient();
                if (client != null) {
                    server.receiveOrder(client);
                    System.out.println("✓ Order taken!");
                }
            }
            case 3 -> {
                Client client = selectClient();
                if (client != null) {
                    Beverage drink = selectBeverage();
                    if (drink != null) {
                        server.serve(client, drink);
                        System.out.println("✓ Served!");
                    }
                }
            }
            case 4 -> inMenu = false;
        }
    }
}
    private Client selectClient() {
        if (clients.isEmpty()) {
            System.out.println("No clients available.");
            return null;
        }

        System.out.println("\nChoose a client:");
        for (int i = 0; i < clients.size(); i++) {
            System.out.println((i + 1) + ". " + clients.get(i).getFirstName());
        }
        System.out.print("Choose: ");

        int idx = getIntInput(1, clients.size()) - 1;
        return clients.get(idx);
    }

    private Beverage selectBeverage() {
        List<Beverage> beverages = bar.getAvailableBeverages();

        if (beverages.isEmpty()) {
            System.out.println("No beverages available!");
            return null;
        }

        System.out.println("\nChoose a beverage:");
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
        } catch (Exception e) {
        }
        System.out.print("Invalid! Try again: ");
        return getIntInput(min, max);
    }
}