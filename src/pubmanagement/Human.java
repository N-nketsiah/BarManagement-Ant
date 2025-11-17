
package pubmanagement;

/**
 *
 * @author NAOMI
 */
/**
 * HUMAN CLASS - Abstract base class for all people in the bar
 */
public abstract class Human {
    private String firstName;
    private String nickname;
    private double wallet;
    private double popularityRating;
    private String significantShout;
    
    /**
     * Constructor - Initialize a human
     * @param firstName the first name (cannot be modified)
     * @param nickname the nickname
     * @param wallet the amount of money
     * @param significantShout the characteristic cry/catchphrase
     */
    public Human(String firstName, String nickname, double wallet, String significantShout) {
        this.firstName = firstName;
        this.nickname = nickname;
        this.wallet = wallet;
        this.popularityRating = 0;
        this.significantShout = significantShout;
    }
    
    /**
     * Get first name
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }
    
    /**
     * Get nickname
     * @return the nickname
     */
    public String getNickname() {
        return nickname;
    }
    
    /**
     * Get wallet
     * @return the amount of money
     */
    public double getWallet() {
        return wallet;
    }
    
    /**
     * Set wallet
     * @param wallet the new amount
     */
    public void setWallet(double wallet) {
        this.wallet = wallet;
        saveHumanData();
    }
    
    /**
     * Get popularity rating
     * @return the popularity rating
     */
    public double getPopularityRating() {
        return popularityRating;
    }
    
    /**
     * Increase popularity rating
     * @param amount the amount to increase
     */
    public void increasePopularity(double amount) {
        this.popularityRating += amount;
    }
    
    /**
     * Decrease popularity rating
     * @param amount the amount to decrease
     */
    public void decreasePopularity(double amount) {
        this.popularityRating -= amount;
    }
    
    /**
     * Get significant shout
     * @return the catchphrase
     */
    public String getSignificantShout() {
        return significantShout;
    }
    
    /**
     * Speak - say something
     * @param message what to say
     */
    public void speak(String message) {
        System.out.println(getFirstName() + ": " + message);
    }
    
    /**
     * Drink - abstract method, each human drinks differently
     * @param beverage the drink to consume
     */
    public abstract void drink(Beverage beverage);
    
    /**
     * Introduce yourself
     */
    public void introduce() {
        speak("Hello, my name is " + getFirstName() + " and I go by " + getNickname() + ".");
    }
    
    /**
     * Save human data to file
     */
    protected void saveHumanData() {
    }
}