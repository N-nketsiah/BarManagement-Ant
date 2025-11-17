
package pubmanagement;

/**
 *
 * @author NAOMI
 */
import java.util.ArrayList;
import java.util.List;

/**
 * SUPPLIER CLASS - Represents a supplier who delivers beverages
 */
public class Supplier extends Human {
    private double amountOwed;
    private List<BeverageDelivery> pendingDeliveries;

    /**
     * Constructor for Supplier
     * @param firstName the first name
     * @param nickname the nickname
     * @param wallet the wallet amount
     * @param significantShout the significant cry
     */
    public Supplier(String firstName, String nickname, double wallet, String significantShout) {
        super(firstName, nickname, wallet, significantShout);
        this.amountOwed = 0.0;
        this.pendingDeliveries = new ArrayList<>();
    }

    /**
     * Get amount owed
     * @return the amount owed
     */
    public double getAmountOwed() {
        return amountOwed;
    }

    /**
     * Deliver beverages to the bar
     * @param bar the bar receiving delivery
     * @param beverages list of beverages to deliver
     */
    public void deliverBeverages(Bar bar, List<Beverage> beverages) {
        speak("Delivering beverages to the bar");

        for (Beverage beverage : beverages) {
            int quantity = 5;

            pendingDeliveries.add(new BeverageDelivery(beverage, quantity));
            bar.receiveStock(beverage, quantity);

            double amountForBeverage = beverage.getCostPrice() * quantity;
            amountOwed += amountForBeverage;

            speak("I delivered " + quantity + " units of " + beverage.getName());
        }

        speak("Total amount owed: " + amountOwed + " euros");
    }

    /**
     * Get paid by Patron
     * @param patron the bar owner
     */
    public void getPaid(Patron patron) {
        if (amountOwed <= 0) {
            speak("I have no pending deliveries to be paid for");
            return;
        }

        speak("I request payment of " + amountOwed + " euros from " + patron.getNickname());

        if (patron.getWallet() >= amountOwed) {
            patron.setWallet(patron.getWallet() - amountOwed);
            setWallet(getWallet() + amountOwed);
            speak("I was paid " + amountOwed + " euros");

            amountOwed = 0.0;
            pendingDeliveries.clear();
        } else {
            speak(patron.getNickname() + " does not have enough money");
        }
    }

    /**
     * Supplier doesn't drink while working
     */
    @Override
    public void drink(Beverage beverage) {
        speak("I don't drink while working");
    }

    /**
     * Inner class to track beverage deliveries
     */
    private static class BeverageDelivery {
        private Beverage beverage;
        private int quantity;

        public BeverageDelivery(Beverage beverage, int quantity) {
            this.beverage = beverage;
            this.quantity = quantity;
        }
    }
}
