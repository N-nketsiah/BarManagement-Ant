
package pubmanagement;

/**
 *
 * @author NAOMI
 */
import java.util.ArrayList;

/**
 * TABLE CLASS - Represents a table in the bar
 */
public class Table {
    private int tableNumber;
    private int capacity;
    private ArrayList<Client> occupants;

    /**
     * Constructor for Table
     * @param tableNumber the table number
     */
    public Table(int tableNumber) {
        this.tableNumber = tableNumber;
        this.capacity = 4;
        this.occupants = new ArrayList<>();
    }

    /**
     * Get table number
     * @return the table number
     */
    public int getTableNumber() {
        return tableNumber;
    }

    /**
     * Get capacity
     * @return the capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Get occupants
     * @return list of clients at table
     */
    public ArrayList<Client> getOccupants() {
        return new ArrayList<>(occupants);
    }

    /**
     * Get number of occupants
     * @return occupant count
     */
    public int getOccupantCount() {
        return occupants.size();
    }

    /**
     * Seat a client at the table
     * @param client the client to seat
     * @return true if seated successfully
     */
    public boolean seatClient(Client client) {
        if (occupants.size() < capacity) {
            occupants.add(client);
            return true;
        }
        return false;
    }

    /**
     * Remove a client from the table
     * @param client the client to remove
     * @return true if removed successfully
     */
    public boolean removeClient(Client client) {
        return occupants.remove(client);
    }

    /**
     * Check if table is full
     * @return true if full
     */
    public boolean isFull() {
        return occupants.size() >= capacity;
    }

    /**
     * Check if table is empty
     * @return true if empty
     */
    public boolean isEmpty() {
        return occupants.isEmpty();
    }

    @Override
    public String toString() {
        return "Table " + tableNumber + " [" + occupants.size() + "/" + capacity + " occupants]";
    }
}