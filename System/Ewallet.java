package System;

/**
 * Ewallet class holds all the important information of the Ewallet such as the balance
 */
public class Ewallet {

    /**
     * the balance of the user
     */
    private double balance;

    /**
     * Ewallet constructor initialises the balance
     */
    public Ewallet() {
        balance = 100;
    }

    /**
     * @return user balance
     */
    public double getBalance() {
        return this.balance;
    }

    /**
     * @param balance sets user balance with the balance value
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

}
