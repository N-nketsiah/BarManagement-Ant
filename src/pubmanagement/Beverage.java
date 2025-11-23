
package pubmanagement;

/**
 *
 * @author NAOMI
 */

/**
 * BEVERAGE CLASS - Represents a drink available in the 
 */
public class Beverage {
    private String name;
    private double salePrice;
    private double costPrice;
    private boolean isAlcoholic;
    private int alcoholPoints;
    
    /**
     * Constructor - create a beverage
     * @param name the name of the beverage 
     * @param salePrice the sale price 
     * @param costPrice the cost price
     * @param alcoholPoints the alcohol points
     */
    public Beverage(String name, double salePrice, double costPrice, 
                    boolean isAlcoholic, int alcoholPoints) {
        this.name = name;
        this.salePrice = salePrice;
        this.costPrice = costPrice;
        this.isAlcoholic = isAlcoholic;
        this.alcoholPoints = alcoholPoints;
    }
    
    /**
     * Get name
     * @return the name of the beverage
     */
    public String getName() {
        return name;
    }
    
    /**
     * Get sale price
     * @return the sale price
     */
    public double getSalePrice() {
        return salePrice;
    }
    
    /**
     * Get cost price
     * @return the cost price
     */
    public double getCostPrice() {
        return costPrice;
    }
    
    /**
     * Check if alcoholic
     * @return true if alcoholic
     */
    public boolean isAlcoholic() {
        return isAlcoholic;
    }
    
    /**
     * Get alcohol points
     * @return the alcohol points
     */
    public int getAlcoholPoints() {
        return alcoholPoints;
    }
    
    /**
     * Get profit per beverage
     * @return profit (sale price - cost price)
     */
    public double getProfit() {
        return salePrice - costPrice;
    }
    
    @Override
    public String toString() {
        return name + " ($" + salePrice + ")";
    }
}

