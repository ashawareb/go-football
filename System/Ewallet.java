package System;

public class Ewallet {

    private double balance;

    public Ewallet() {
        balance = 0;
    }

    public double getBalance() {
        return this.balance;
    }

    /**
     * @param balance
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

}