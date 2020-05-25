package User;

import Exceptions.*;
import System.Application;
import System.Verifier;

import java.util.Scanner;

public abstract class Account {

    private String userName;
    private String password;
    private String email;
    private String phoneNumber;
    static private int counter = 0;
    private int id;
    private Verifier verifier;

    public Account(Verifier verifier) {
        this.userName = "";
        this.password = "";
        this.email = "";
        this.phoneNumber = "";
        this.id = counter;
        this.counter++;
        this.verifier = verifier;
    }

    public String getUserName() {
        return this.userName;
    }

    /**
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    /**
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    /**
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return this.id;
    }

    public Verifier getVerifier() {
        return verifier;
    }

    /**
     * @param username
     * @param password
     * @param email
     * @param phoneNumber
     */
    public void register(String username, String password, String email, String phoneNumber) {
        this.userName = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public void updateProfile() throws InvalidEmail, InvalidPassword, InvalidNumber, InvalidAddress {
        Scanner input = new Scanner(System.in);
        boolean isOwner = (this instanceof PlaygroundOwner);
        int maxLimit = (isOwner) ? 4 : 3;
        System.out.println("===========================");
        System.out.println("What would you like to update: ");
        System.out.println("===========================");
        System.out.println("[0] Cancel");
        System.out.println("[1] Email");
        System.out.println("[2] Password");
        System.out.println("[3] Phone Number");
        if (isOwner)
            System.out.println("[4] Address");
        int userChoice = getVerifier().getUserChoice(0, maxLimit);
        if (userChoice == 1) {
            System.out.println("Current email address: " + getEmail());
            System.out.println("Please enter an email: ");
            String newEmail = input.nextLine();
            if (!verifier.verifyEmail(newEmail)) throw new InvalidEmail();
            else setEmail(newEmail);
        } else if (userChoice == 2) {
            System.out.println("Current password: " + getPassword());
            System.out.println("Please enter a new password: ");
            String newPassword = input.nextLine();
            if (password.length() < 6) throw new InvalidPassword();
            else setPassword(newPassword);
        } else if (userChoice == 3) {
            System.out.println("Current phone number: " + getPhoneNumber());
            System.out.println("Please enter a new phone number: ");
            String newNumber = input.nextLine();
            if (!verifier.verifyNumber(newNumber)) throw new InvalidNumber();
            else setPhoneNumber(newNumber);
        } else if (userChoice == 4) {
            System.out.println("Current address: " + ((PlaygroundOwner) this).getAddress());
            System.out.println("Please enter an email: ");
            String newAddress = input.nextLine();
            if (newAddress.length() < 1) throw new InvalidAddress();
            else ((PlaygroundOwner) this).setAddress(newAddress);
        }
    }
}