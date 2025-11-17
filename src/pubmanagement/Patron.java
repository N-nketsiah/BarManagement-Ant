
package pubmanagement;

/**
 *
 * @author NAOMI
 */
import java.util.List;

/**
 * PATRONNE CLASS - Represents the bar owner (always a woman)
 */
public class Patron extends Human {
    private String barName;

    /**
     * Constructor for Patron
     * @param firstName the first name
     * @param nickname the nickname
     * @param wallet the wallet amount
     * @param significantShout the significant cry
     * @param barName the name of the bar
     */
    public Patron(String firstName, String nickname, double wallet, String significantShout, String barName) {
        super(firstName, nickname, wallet, significantShout);
        this.barName = barName;
    }

    /**
     * Get bar name
     * @return the bar name
     */
    public String getBarName() {
        return barName;
    }

    /**
     * Collect cash from bartender when excess liquidity
     * @param barman the bartender
     * @param threshold the threshold amount
     */
    public void collectCashFromRegister(Barman barman, double threshold) {
        double cash = barman.getCashRegister();
        if (cash > threshold) {
            double collectedAmount = cash - threshold;
            barman.addToCashRegister(-collectedAmount);
            setWallet(getWallet() + collectedAmount);
            speak("I have collected " + collectedAmount + " euros from the cash register.");
        } else {
            speak("There is not enough excess cash to collect right now.");
        }
    }

    /**
     * React to general round
     */
    public void reactToGeneralRound() {
        speak("Everything is fine, business is picking up.");
    }

    /**
     * Offer a drink to someone (patron doesn't pay)
     * @param person the person receiving the drink
     * @param beverage the beverage to offer
     */
    public void offerDrink(Human person, Beverage beverage) {
        speak("I'm offering a " + beverage.getName() + " to " + person.getNickname() + ".");
        person.drink(beverage);
    }

    /**
     * Order to stop serving a client
     * @param client the client to stop serving
     */
    public void stopServing(Client client) {
        client.setCanReceiveDrink(false);
        client.saveClientData();
        speak("Stop serving " + client.getNickname() + " immediately.");
    }

    /**
     * Temporarily ban a client from the bar
     * @param client the client to ban
     * @param bar the bar
     */
    public void banClient(Client client, Bar bar) {
        List<Client> clients = bar.getClients();
        if (clients.contains(client)) {
            bar.removeClient(client);
            speak("I am temporarily banning " + client.getNickname() + " from the bar.");
        } else {
            speak(client.getNickname() + " is not currently in the bar.");
        }
    }

    /**
     * Patron drinks
     */
    @Override
    public void drink(Beverage beverage) {
        speak("I am enjoying a " + beverage.getName() + ".");
    }
}
