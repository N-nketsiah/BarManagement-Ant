package pubmanagement;

/**
 * BARMAN CLASS - Represents the bartender
 */
public class Barman extends Human {
    private double cashRegister;
    private boolean isServing;

    /**
     * Constructor for Barman
     * @param firstName the first name
     * @param nickname the nickname
     * @param wallet the wallet amount
     * @param significantShout the significant cry
     */
    public Barman(String firstName, String nickname, double wallet, String significantShout) {
        super(firstName, nickname, wallet, significantShout);
        this.cashRegister = 0;
        this.isServing = true;
    }

    /**
     * Get cash register amount
     * @return the cash in register
     */
    public double getCashRegister() {
        return cashRegister;
    }

    /**
     * Serve a drink to a client
     * @param client the client to serve
     * @param beverage the beverage to serve
     */
    public void serveDrink(Client client, Beverage beverage) {
        if (isServing) {
            speak("I serve a " + beverage.getName() + " to " + client.getNickname());
            client.drink(beverage);
            double price = beverage.getSalePrice();
            client.pay(price);
            cashRegister += price;
            saveCashRegister();
        } else {
            speak("I am not serving right now");
        }
    }

    /**
     * Offer a drink (special case for regular clients)
     * @param person the person to offer to
     * @param beverage the beverage to offer
     */
    public void offerDrink(Human person, Beverage beverage) {
        speak("I offer a " + beverage.getName() + " to " + person.getNickname());
        double cost = beverage.getCostPrice();
        cashRegister -= cost;
        saveCashRegister();
        person.drink(beverage);
    }

    /**
     * Manage stock of beverages
     * @param bar the bar
     * @param beverage the beverage to stock
     * @param quantity the quantity
     */
    public void manageStock(Bar bar, Beverage beverage, int quantity) {
        speak("I am managing stock for " + beverage.getName());
        bar.receiveStock(beverage, quantity);
    }

    /**
     * Announce general round
     */
    public void announceGeneralRound() {
        speak("GENERAL ROUND");
    }

    /**
     * Add to cash register
     * @param amount the amount to add
     */
    public void addToCashRegister(double amount) {
        cashRegister += amount;
        saveCashRegister();
    }

    /**
     * Barman only drinks non-alcoholic beverages
     */
    @Override
    public void drink(Beverage beverage) {
        if (beverage.isAlcoholic()) {
            speak("I do not drink alcoholic beverages");
        } else {
            speak("I drink " + beverage.getName());
        }
    }

    /**
     * Override speak - Barman always ends sentences with "coco"
     */
    @Override
    public void speak(String message) {
        super.speak(message + ", coco");
    }

    /**
     * Save cash register
     */
    private void saveCashRegister() {
        // Will implement file management later
    }
}