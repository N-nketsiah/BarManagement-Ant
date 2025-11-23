package pubmanagement;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * BAR CLASS - Represents the bar establishment
 */
public class Bar {
    private String name;
    private Patron patron;
    private Barman barman;
    private List<Server> servers;
    private List<Client> clients;
    private List<Beverage> stockBeverages;
    private List<Integer> stockQuantities;
    private List<Table> tables;

    public Bar(String name, Patron patron, Barman barman) {
        this.name = name;
        this.patron = patron;
        this.barman = barman;
        this.servers = new ArrayList<>();
        this.clients = new ArrayList<>();
        this.stockBeverages = new ArrayList<>();
        this.stockQuantities = new ArrayList<>();
        this.tables = new ArrayList<>();
        initializeTables();
    }

    /** Initialize tables (4 tables by default) */
    private void initializeTables() {
        for (int i = 1; i <= 4; i++) {
            tables.add(new Table(i));
        }
    }

    public int getClientCount() { return clients.size(); }
    public String getName() { return name; }
    public Patron getPatron() { return patron; }
    public Barman getBarman() { return barman; }
    public List<Table> getTables() { return tables; }
    public List<Client> getClients() { return clients; }
    public List<Server> getServers() { return servers; }

    /** Add a server */
    public void addServer(Server server) {
        servers.add(server);
        System.out.println(server.getNickname() + " is now working at the bar.");
    }

    /** Remove server */
    public void removeServer(Server server) {
        servers.remove(server);
        System.out.println(server.getNickname() + " has left the bar.");
    }

    /** Add client and seat them */
    public void addClient(Client client) {
        clients.add(client);
        System.out.println(client.getNickname() + " enters the bar.");
        seatClientAtTable(client);
    }

    public void removeClient(Client client) {
        clients.remove(client);
        System.out.println(client.getNickname() + " leaves the bar.");
    }

    /** Seat client automatically at first available table */
    public boolean seatClientAtTable(Client client) {
        for (Table table : tables) {
            if (!table.isFull()) {
                table.seatClient(client);
                return true;
            }
        }
        System.out.println("No tables available to seat " + client.getNickname() + ".");
        return false;
    }

    /** ------------------------ TABLE DISPLAY LOGIC ------------------------ */

    /** Show all tables and their occupants */
    public void showTables() {
        System.out.println("\n--- TABLE STATUS ---");
        for (Table t : tables) {
            System.out.println("Table " + t.getTableNumber()
                    + " [" + t.getOccupantCount() + "/" + t.getCapacity() + "]");
            if (!t.isEmpty()) {
                for (Client c : t.getOccupants()) {
                    System.out.println("   - " + c.getFirstName());
                }
            }
        }
    }

    /** ------------------------ STOCK MANAGEMENT ------------------------ */

    public void addBeverages(Beverage beverage, int quantity) {
        int index = stockBeverages.indexOf(beverage);
        if (index != -1) {
            int currentQuantity = stockQuantities.get(index);
            stockQuantities.set(index, currentQuantity + quantity);
        } else {
            stockBeverages.add(beverage);
            stockQuantities.add(quantity);
        }
        System.out.println("Bar stock updated: " + beverage.getName()
                + " now has " + getBeverageStock(beverage) + " units.");
    }

    public void saveStockToFile() {
        try { Files.createDirectories(Paths.get("data")); }
        catch (IOException e) {
            System.out.println("Error creating data directory: " + e.getMessage());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/stock.csv"))) {
            writer.write("Beverage,Quantity,LastUpdated\n");

            for (int i = 0; i < stockBeverages.size(); i++) {
                String line = String.format("%s,%d,%s",
                        stockBeverages.get(i).getName(),
                        stockQuantities.get(i),
                        new java.util.Date());

                writer.write(line);
                writer.newLine();
            }
            System.out.println("Stock levels saved to file");
        } catch (IOException e) {
            System.out.println("Error saving stock: " + e.getMessage());
        }
    }

    public static List<Beverage> loadStockFromFile() {
        List<Beverage> beverages = new ArrayList<>();
        try (java.io.BufferedReader reader = new java.io.BufferedReader(
                new java.io.FileReader("data/stock.csv"))) {

            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String name = parts[0];
                    beverages.add(new Beverage(name, 5.0, 2.0, true, 2));
                }
            }
        } catch (IOException e) {
            System.out.println("No previous stock found");
        }
        return beverages;
    }

    public void receiveStock(Beverage beverage, int quantity) {
        int index = stockBeverages.indexOf(beverage);
        if (index != -1) {
            int currentQuantity = stockQuantities.get(index);
            stockQuantities.set(index, currentQuantity + quantity);
        } else {
            stockBeverages.add(beverage);
            stockQuantities.add(quantity);
        }
        System.out.println("Bar stock updated: " + beverage.getName()
                + " now has " + getBeverageStock(beverage) + " units.");
        saveStockToFile();
    }

    public int getBeverageStock(Beverage beverage) {
        int index = stockBeverages.indexOf(beverage);
        if (index != -1) return stockQuantities.get(index);
        return 0;
    }

    public boolean serveDrink(Client client, Beverage beverage) {
        if (!client.canReceiveDrink()) {
            System.out.println(client.getNickname() + " cannot be served.");
            return false;
        }

        int currentStock = getBeverageStock(beverage);
        if (currentStock > 0) {
            int idx = stockBeverages.indexOf(beverage);
            stockQuantities.set(idx, currentStock - 1);
            barman.serveDrink(client, beverage);
            saveStockToFile();
            return true;
        } else {
            System.out.println("Sorry, " + beverage.getName() + " is out of stock.");
            return false;
        }
    }

    public void offerGeneralRound() {
        barman.announceGeneralRound();
        patron.reactToGeneralRound();
        for (Server s : servers) s.indicateBusy();
        Client.expressSignificantCry(clients);
    }

    public void displayStockLevels() {
        System.out.println("Current stock levels:");
        if (stockBeverages.isEmpty()) {
            System.out.println("No beverages in stock.");
            return;
        }
        for (int i = 0; i < stockBeverages.size(); i++) {
            System.out.println(stockBeverages.get(i).getName()
                    + ": " + stockQuantities.get(i) + " units");
        }
    }

    public List<Beverage> getAvailableBeverages() {
        List<Beverage> list = new ArrayList<>();
        for (int i = 0; i < stockBeverages.size(); i++) {
            if (stockQuantities.get(i) > 0) list.add(stockBeverages.get(i));
        }
        return list;
    }
}
