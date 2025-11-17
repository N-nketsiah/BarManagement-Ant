
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

    /**
     * Constructor for Bar
     * @param name the bar name
     * @param patron the bar owner
     * @param barman the bartender
     */
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

    /**
     * Initialize tables (4 tables by default)
     */
    private void initializeTables() {
        for (int i = 1; i <= 4; i++) {
            tables.add(new Table(i));
        }
    }
    /**
 * Get client count
 */
public int getClientCount() {
    return clients.size();
}

    /**
     * Get bar name
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Get patron
     * @return the patron
     */
    public Patron getPatron() {
        return patron;
    }

    /**
     * Get barman
     * @return the barman
     */
    public Barman getBarman() {
        return barman;
    }

    /**
     * Get all tables
     * @return list of tables
     */
    public List<Table> getTables() {
        return tables;
    }

    /**
     * Get all clients
     * @return list of clients
     */
    public List<Client> getClients() {
        return clients;
    }

    /**
     * Add a server
     * @param server the server to add
     */
    public void addServer(Server server) {
        servers.add(server);
        System.out.println(server.getNickname() + " is now working at the bar.");
    }

    /**
     * Remove a server
     * @param server the server to remove
     */
    public void removeServer(Server server) {
        servers.remove(server);
        System.out.println(server.getNickname() + " has left the bar.");
    }

    /**
     * Add a client
     * @param client the client to add
     */
    public void addClient(Client client) {
        clients.add(client);
        System.out.println(client.getNickname() + " enters the bar.");
        seatClientAtTable(client);
    }

    /**
     * Remove a client
     * @param client the client to remove
     */
    public void removeClient(Client client) {
        clients.remove(client);
        System.out.println(client.getNickname() + " leaves the bar.");
    }

    /**
     * Seat a client at a table
     * @param client the client to seat
     * @return true if seated successfully
     */
    public boolean seatClientAtTable(Client client) {
        for (Table table : tables) {
            if (!table.isFull()) {
                return table.seatClient(client);
            }
        }
        System.out.println("No tables available to seat " + client.getNickname() + ".");
        return false;
    }

    /**
     * Add beverages to stock
     * @param beverage the beverage to add
     * @param quantity the quantity
     */
    public void addBeverages(Beverage beverage, int quantity) {
        int index = stockBeverages.indexOf(beverage);
        if (index != -1) {
            int currentQuantity = stockQuantities.get(index);
            stockQuantities.set(index, currentQuantity + quantity);
        } else {
            stockBeverages.add(beverage);
            stockQuantities.add(quantity);
        }
        System.out.println("Bar stock updated: " + beverage.getName() + " now has " + getBeverageStock(beverage) + " units.");
    }
    /**
 * Save stock levels to file
 */
public void saveStockToFile() {
    try {
        Files.createDirectories(Paths.get("data"));
    } catch (IOException e) {
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
        System.out.println("✓ Stock levels saved to file");
    } catch (IOException e) {
        System.out.println("Error saving stock: " + e.getMessage());
    }
}

/**
 * Load stock levels from file
 */
public static List<Beverage> loadStockFromFile() {
    List<Beverage> beverages = new ArrayList<>();
    try (java.io.BufferedReader reader = new java.io.BufferedReader(
            new java.io.FileReader("data/stock.csv"))) {
        String line;
        boolean firstLine = true;
        while ((line = reader.readLine()) != null) {
            if (firstLine) {
                firstLine = false;
                continue; // Skip header
            }
            String[] parts = line.split(",");
            if (parts.length >= 2) {
                String name = parts[0];
                // Create beverage (simplified)
                beverages.add(new Beverage(name, 5.0, 2.0, true, 2));
            }
        }
    } catch (IOException e) {
        System.out.println("No previous stock found");
    }
    return beverages;
}

    /**
     * Receive stock from supplier
     * @param beverage the beverage
     * @param quantity the quantity received
     */
    public void receiveStock(Beverage beverage, int quantity) {
    int index = stockBeverages.indexOf(beverage);
    if (index != -1) {
        int currentQuantity = stockQuantities.get(index);
        stockQuantities.set(index, currentQuantity + quantity);
    } else {
        stockBeverages.add(beverage);
        stockQuantities.add(quantity);
    }
    System.out.println("Bar stock updated: " + beverage.getName() + " now has " + getBeverageStock(beverage) + " units.");
    saveStockToFile();  
}


    /**
     * Get stock quantity of a beverage
     * @param beverage the beverage
     * @return the quantity
     */
    public int getBeverageStock(Beverage beverage) {
        int index = stockBeverages.indexOf(beverage);
        if (index != -1) {
            return stockQuantities.get(index);
        }
        return 0;
    }

    /**
     * Serve a drink to a client
     * @param client the client
     * @param beverage the beverage
     * @return true if served successfully
     */
    public boolean serveDrink(Client client, Beverage beverage) {
    if (!client.canReceiveDrink()) {
        System.out.println(client.getNickname() + " is no longer allowed to be served.");
        return false;
    }

    int currentStock = getBeverageStock(beverage);
    if (currentStock > 0) {
        int index = stockBeverages.indexOf(beverage);
        stockQuantities.set(index, currentStock - 1);
        barman.serveDrink(client, beverage);
        saveStockToFile();  // ADD THIS LINE
        return true;
    } else {
        System.out.println("Sorry, " + beverage.getName() + " is out of stock.");
        return false;
    }
}

    /**
     * Offer a general round
     */
    public void offerGeneralRound() {
        barman.announceGeneralRound();
        patron.reactToGeneralRound();
        for (Server server : servers) {
            server.indicateBusy();
        }
        Client.expressSignificantCry(clients);
    }

    /**
     * Get all servers
     * @return list of servers
     */
    public List<Server> getServers() {
        return servers;
    }

    /**
     * Display stock levels
     */
    public void displayStockLevels() {
        System.out.println("Current stock levels:");
        if (stockBeverages.isEmpty()) {
            System.out.println("No beverages in stock.");
            return;
        }
        for (int i = 0; i < stockBeverages.size(); i++) {
            String beverageName = stockBeverages.get(i).getName();
            int quantity = stockQuantities.get(i);
            if (quantity == 0) {
                System.out.println(beverageName + ": Out of stock");
            } else {
                System.out.println(beverageName + ": " + quantity + " units");
            }
        }
    }

    /**
     * Get available beverages
     * @return list of available beverages
     */
    public List<Beverage> getAvailableBeverages() {
        List<Beverage> availableBeverages = new ArrayList<>();
        for (int i = 0; i < stockBeverages.size(); i++) {
            if (stockQuantities.get(i) > 0) {
                availableBeverages.add(stockBeverages.get(i));
            }
        }
        return availableBeverages;
    }
}
