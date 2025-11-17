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
 * SUPPLIER MENU - Interactive menu for Supplier role
 */
public class SupplierMenu {
    private Scanner scanner;
    private Bar bar;
    private Supplier supplier;

    public SupplierMenu(Bar bar, Supplier supplier) {
        this.bar = bar;
        this.supplier = supplier;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        boolean inMenu = true;

        while (inMenu) {
            System.out.println("\nYou are the Supplier. What would you like to do?");
            System.out.println("1. Supply drinks to the bar");
            System.out.println("2. Get paid by the patron");
            System.out.println("3. Check amount owed");
            System.out.println("4. Quit to main menu");
            System.out.print("Choose: ");

            int choice = getIntInput(1, 4);

            switch (choice) {
                case 1 -> supplyDrinks();
                case 2 -> getPaid();
                case 3 -> checkAmountOwed();
                case 4 -> inMenu = false;
            }
        }
    }

    private void supplyDrinks() {
        System.out.println("\nSupplying drinks to the bar...");
        List<Beverage> beverages = bar.getAvailableBeverages();

        if (beverages.isEmpty()) {
            System.out.println("Adding default beverages...");
            bar.addBeverages(new Beverage("Beer", 5.0, 2.0, true, 2), 50);
            bar.addBeverages(new Beverage("Wine", 6.0, 2.5, true, 3), 30);
            bar.addBeverages(new Beverage("Water", 1.0, 0.3, false, 0), 100);
        } else {
            System.out.println("Current beverages:");
            for (Beverage b : beverages) {
                System.out.println("- " + b.getName() + ": " + bar.getBeverageStock(b) + " units");
            }

            System.out.print("\nChoose beverage to restock (1-" + beverages.size() + "): ");
            int idx = getIntInput(1, beverages.size()) - 1;
            Beverage chosen = beverages.get(idx);

            System.out.print("Enter quantity to supply: ");
            int quantity = getIntInput(1, 1000);

            bar.addBeverages(chosen, quantity);
            System.out.println("✓ Supplied " + quantity + " units of " + chosen.getName());
        }
    }

    private void getPaid() {
        try {
            supplier.getPaid(bar.getPatron());
            System.out.println("✓ Payment received!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void checkAmountOwed() {
        System.out.println("\nThe bar owes you €" + supplier.getAmountOwed());
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