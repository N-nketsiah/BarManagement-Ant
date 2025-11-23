
package pubmanagement;

/**
 *
 * @author NAOMI
 */
import utils.Gender;
import java.util.List;

/**
 * CLIENT CLASS - Represents a client in the bar
 */
public class Client extends Human {
    private Beverage favoriteDrink;
    private Beverage backupDrink;
    private int alcoholLevel;
    private Gender gender;
    private boolean canReceiveDrink;
    private String clothingOrJewelry;

    private static final int ALCOHOL_THRESHOLD = 5;
    private static final String[] MALE_QUALIFIERS = {"doll", "my pretty"};
    private static final String[] FEMALE_QUALIFIERS = {"handsome", "cutie"};

    /**
     * Constructor for the Client class
     */
    public Client(String firstName, String nickname, double wallet, String significantShout, Gender gender,
                  Beverage favoriteDrink, Beverage backupDrink, String clothingOrJewelry) {
        super(firstName, nickname, wallet, significantShout);
        this.gender = gender;
        this.favoriteDrink = favoriteDrink;
        this.backupDrink = backupDrink;
        this.alcoholLevel = 0;
        this.canReceiveDrink = true;
        this.clothingOrJewelry = clothingOrJewelry;
    }

    public Beverage getFavoriteDrink() {
        return favoriteDrink;
    }

    public void setFavoriteDrink(Beverage favoriteDrink) {
        this.favoriteDrink = favoriteDrink;
        saveClientData();
    }

    public Beverage getBackupDrink() {
        return backupDrink;
    }

    public void setBackupDrink(Beverage backupDrink) {
        this.backupDrink = backupDrink;
        saveClientData();
    }

    public int getAlcoholLevel() {
        return alcoholLevel;
    }

    public Gender getGender() {
        return gender;
    }

    public void changeGender(Gender newGender, String newClothingOrJewelry) {
        this.gender = newGender;
        this.clothingOrJewelry = newClothingOrJewelry;
        speak("I have changed gender to " + newGender + " and now wear " + clothingOrJewelry + ".");
        saveClientData();
    }

    public boolean canReceiveDrink() {
        return canReceiveDrink;
    }

    public void setCanReceiveDrink(boolean canReceiveDrink) {
        this.canReceiveDrink = canReceiveDrink;
        saveClientData();
    }

    public String getClothingOrJewelry() {
        return clothingOrJewelry;
    }

    /**
     * Client drinks a beverage
     */
    @Override
    public void drink(Beverage beverage) {
        speak("I drink a " + beverage.getName() + ".");
        if (beverage.isAlcoholic()) {
            alcoholLevel += beverage.getAlcoholPoints();
            checkForQualifierUsage();
            saveClientData();
        }
    }

    /**
     * Introduce the client
     */
    @Override
    public void introduce() {
        super.introduce();
        if (gender == Gender.MALE) {
            speak("I'm wearing a " + clothingOrJewelry + " T-shirt.");
        } else {
            speak("I'm wearing " + clothingOrJewelry + " jewelry.");
        }
        speak("My favorite drink is " + favoriteDrink.getName() + ".");
    }

    /**
     * Receive a beverage
     */
    public void receiveDrink(Beverage beverage) {
        if (canReceiveDrink) {
            if (getWallet() >= beverage.getSalePrice()) {
                drink(beverage);
                pay(beverage.getSalePrice());
                speak("I have received a " + beverage.getName() + ".");
            } else {
                speak("I don't have enough money to pay for this drink.");
            }
        } else {
            speak("I cannot receive any more drinks.");
        }
    }

    /**
     * Check for qualifier usage
     */
    private void checkForQualifierUsage() {
        if (alcoholLevel >= ALCOHOL_THRESHOLD) {
            if (gender == Gender.MALE) {
                speak("addresses the waitress as " + getRandomQualifier(MALE_QUALIFIERS) + ".");
            } else if (gender == Gender.FEMALE) {
                speak("addresses the waiter as " + getRandomQualifier(FEMALE_QUALIFIERS) + ".");
            }
        }
    }

    /**
     * Get random qualifier
     */
    private String getRandomQualifier(String[] qualifiers) {
        int randomIndex = (int) (Math.random() * qualifiers.length);
        return qualifiers[randomIndex];
    }

    /**
     * Pay for something
     */
    public void pay(double amount) {
        if (getWallet() >= amount) {
            setWallet(getWallet() - amount);
            speak("I paid " + amount + " dollars.");
            saveClientData();
        } else {
            speak("I don't have enough money to pay for this.");
        }
    }

    /**
     * All clients express their cry
     */
    public static void expressSignificantCry(List<Client> clients) {
        for (Client client : clients) {
            client.speak(client.getSignificantShout());
        }
    }

    /**
     * Save client data
     */
    public void saveClientData() {
        //
    }

    @Override
    protected void saveHumanData() {
        saveClientData();
    }
}
