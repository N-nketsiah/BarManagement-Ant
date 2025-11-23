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
import java.util.Scanner;

/**
 * BARTENDER MENU - Interactive menu for Bartender role
 */
public class BarmanMenu {
    private Scanner scanner;
    private Bar bar;
    private List<Client> clients;

    public BarmanMenu(Bar bar, List<Client> clients) {
        this.bar = bar;
        this.clients = clients;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        boolean inMenu = true;

        while (inMenu) {
            System.out.println("\nYou are the Bartender. What would you like to do?");
            System.out.println("1. Serve a client");
            System.out.println("2. Announce a general round");
            System.out.println("3. Manage stock");
            System.out.println("4. Quit to main menu");
            System.out.print("Choose: ");

            int choice = getIntInput(1, 4);

            switch (choice) {
                case 1 -> serveClient();
                case 2 -> announceRound();
                case 3 -> manageStock();
                case 4 -> inMenu = false;
            }
        }
    }

    private void serveClient() {
        if (clients.isEmpty()) {
            System.out.println("No clients available.");
            return;
        }

        Client client = selectClient();
        if (client != null) {
            Beverage beverage = selectBeverage();
            if (beverage != null) {
                bar.serveDrink(client, beverage);
                System.out.println(" Served " + beverage.getName() + " to " + client.getFirstName());
            }
        }
    }

    private void announceRound() {
        bar.offerGeneralRound();
        System.out.println("General round announced!");
    }

   private void manageStock() {
    System.out.println("\nManaging stock.");
    System.out.println("1. View stock levels");
    System.out.println("2. Add stock");
    System.out.print("Choose: ");

    int choice = getIntInput(1, 2);

    switch (choice) {
        case 1 -> {
            bar.getBarman().speak("Current stock levels:");
            bar.displayStockLevels();
        }
        case 2 -> {
            Beverage beverage = selectBeverage();
            if (beverage != null) {
                System.out.print("Enter quantity to add: ");
                int quantity = getIntInput(1, 1000);
                bar.addBeverages(beverage, quantity);
                bar.getBarman().speak("Stock updated!");
            }
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