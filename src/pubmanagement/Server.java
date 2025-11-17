
package pubmanagement;

/**
 *
 * @author NAOMI
 */
import utils.Gender;

/**
 * SERVER CLASS - Represents a server/waitress in the bar
 */
public class Server extends Human {
    private int strength;
    private int charm;
    private Gender gender;

    /**
     * Constructor for Server
     * @param firstName the first name
     * @param nickname the nickname
     * @param wallet the wallet amount
     * @param significantShout the significant cry
     * @param gender the gender (MALE or FEMALE)
     * @param strengthOrCharm strength for males, charm for females
     */
    public Server(String firstName, String nickname, double wallet, String significantShout, Gender gender,
                  int strengthOrCharm) {
        super(firstName, nickname, wallet, significantShout);
        this.gender = gender;
        if (gender == Gender.MALE) {
            this.strength = strengthOrCharm;
            this.charm = 0;
        } else {
            this.charm = strengthOrCharm;
            this.strength = 0;
        }
    }

    /**
     * Get strength
     * @return the strength value
     */
    public int getStrength() {
        return strength;
    }

    /**
     * Get charm
     * @return the charm value
     */
    public int getCharm() {
        return charm;
    }

    /**
     * Get gender
     * @return the gender
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Serve a drink to a client
     * @param client the client to serve
     * @param beverage the beverage to serve
     */
    public void serve(Client client, Beverage beverage) {
        speak("I serve a " + beverage.getName() + " to " + client.getNickname());
        client.drink(beverage);
        double price = beverage.getSalePrice();
        client.pay(price);
        client.saveClientData();

        if (gender == Gender.FEMALE && charm > 5) {
            speak(client.getNickname() + " consumes more due to my charm");
            client.drink(beverage);
            client.saveClientData();
        } else if (gender == Gender.MALE && strength > 5 && client.getAlcoholLevel() > 5) {
            speak(client.getNickname() + " behaves more reasonably due to my strength");
        }
    }

    /**
     * Receive order from client
     * @param client the client
     */
    public void receiveOrder(Client client) {
        speak("I take order from " + client.getNickname());
    }

    /**
     * Servers only drink water
     */
    @Override
    public void drink(Beverage beverage) {
        if (beverage.getName().equalsIgnoreCase("water")) {
            speak("I drink water");
        } else {
            speak("I only drink water");
        }
    }

    /**
     * Indicate server is busy
     */
    public void indicateBusy() {
        speak("I'm busy serving clients");
    }

    /**
     * Save server data
     */
    private void saveServerData() {
        // Will implement file management later
    }
}
