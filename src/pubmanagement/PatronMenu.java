/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pubmanagement;

/**
 *
 * @author NAOMI
 */


import pubmanagement.*;
import java.util.List;
import java.util.Scanner;

/**
 * PATRON MENU - Interactive menu for Patron role
 */
public class PatronMenu {
    private Scanner scanner;
    private Bar bar;
    private List<Client> clients;

    public PatronMenu(Bar bar, List<Client> clients) {
        this.bar = bar;
        this.clients = clients;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() throws Exception {
        boolean inMenu = true;

        while (inMenu) {
            System.out.println("=====================================");
            System.out.println("            PATRON MENU              ");
            System.out.println("=====================================");
            System.out.println("1. Ban a client");
            System.out.println("2. Collect cash from the bartender");
            System.out.println("3. Stop serving a client");
            System.out.println("4. Offer a drink to someone");
            System.out.println("5. View bar status");
            System.out.println("6. Quit to main menu");
            System.out.print("Choose: ");

            int choice = getIntInput(1, 6);

            switch (choice) {
                case 1 -> banClient();
                case 2 -> collectCash();
                case 3 -> stopServing();
                case 4 -> offerDrink();
                case 5 -> viewBarStatus();
                case 6 -> inMenu = false;
            }
        }
    }

    private void banClient() {
        if (clients.isEmpty()) {
            System.out.println("No clients available.");
            return;
        }

        Client client = selectClient();
        if (client != null) {
            bar.getPatron().banClient(client, bar);
            clients.remove(client);
            System.out.println("✓ " + client.getFirstName() + " has been banned!");
        }
    }

    private void collectCash() {
        bar.getPatron().collectCashFromRegister(bar.getBarman(), 50);
        System.out.println("✓ Cash collected!");
    }

    private void stopServing() {
        Client client = selectClient();
        if (client != null) {
            bar.getPatron().stopServing(client);
            System.out.println("✓ Stopped serving " + client.getFirstName());
        }
    }

    private void offerDrink() {
        Client client = selectClient();
        if (client != null) {
            Beverage beverage = selectBeverage();
            if (beverage != null) {
                bar.getPatron().offerDrink(client, beverage);
                System.out.println("✓ Offered " + beverage.getName() + " to " + client.getFirstName());
            }
        }
    }

    private void viewBarStatus() {
        System.out.println("\n─── BAR STATUS ───");
        System.out.println("Bar: " + bar.getName());
        System.out.println("Clients: " + clients.size());
        System.out.println("Servers: " + bar.getServers().size());
        System.out.println("Tables: " + bar.getTables().size());
        System.out.println("\n─── STOCK LEVELS ───");
        bar.displayStockLevels();
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
            System.out.println((i + 1) + ". " + beverages.get(i).getName() + " - €" + beverages.get(i).getSalePrice());
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
