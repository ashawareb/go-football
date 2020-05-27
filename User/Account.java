package User;

import Exceptions.*;
import System.Application;
import System.Verifier;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * account class responsible for holding all the information of users, whether admin, player or playground Owner
 */
public abstract class Account {

    /**
     * the username of the user
     */
    private String userName;
    /**
     * the password of the user
     */
    private String password;
    /**
     * the email of the user
     */
    private String email;
    /** 
     * the phone number of the user
     */
    private String phoneNumber;
    /**
     * the static counter to keep track of how many users are there
     */
    static private int counter = 0;
    /**
     * the id of the user
     */
    private int id;
    /**
     * the verifier attribute of the user
     */
    private Verifier verifier;

    /**
     * the account constructor, responsible for initialising the attributes of the class
     * @param verifier used to initialise the attribute of the verifier attribute of the class Account
     */
    public Account(Verifier verifier) {
        this.userName = "";
        this.password = "";
        this.email = "";
        this.phoneNumber = "";
        this.id = counter;
        this.counter++;
        this.verifier = verifier;
    }

    /**
     * @return getter for usernamt
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * @param userName setter for username
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return getter for password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * @param password setter for password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return getter for email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * @param email setter for email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return getter for phone number
     */
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    /**
     * @param phoneNumber setter for phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return getter for id
     */
    public int getId() {
        return this.id;
    }

    /**
     * @return getter for verifier
     */
    public Verifier getVerifier() {
        return verifier;
    }

    /**
     * @param username sets current username with username
     * @param password sets current password with password
     * @param email sets current email with email
     * @param phoneNumber sets current phonenumber with phonenumber
     */
    public void register(String username, String password, String email, String phoneNumber) {
        this.userName = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    /**
     * takes account that needs to be updated
     * it displays a menu for the user to choose from
     * the user should choose wether to update email, password or phone number
     * in case the user is a playground owner, he has the extra option for updating the address of his own
     * the method checks if the new username, email and address are unique, if not, throws an exception based on what was duplicated
     * @param accounts account attributes to be updated
     * @throws InvalidEmail if user entered invalid email
     * @throws InvalidPassword if user entered invalid password
     * @throws InvalidNumber if user entered invalid phone number
     * @throws InvalidAddress if playground owner entered invalid address
     */
    public void updateProfile(ArrayList<Account> accounts) throws InvalidEmail, InvalidPassword, InvalidNumber, InvalidAddress {
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
        if (isOwner) {
            System.out.println("[4] Address");
        }
        System.out.println("Please enter a number: ");
        int userChoice = getVerifier().getUserChoice(0, maxLimit);
        if (userChoice == 1) {
            System.out.println("Current email address: " + getEmail());
            System.out.println("Please enter an email: ");
            String newEmail = input.nextLine().toLowerCase();
            boolean duplicateEmail = false;
            for (int i = 0; i < accounts.size(); i++) {
                if (accounts.get(i).getEmail().equals(newEmail)) {
                    duplicateEmail = true;
                    break;
                }
            }
            if ((!verifier.verifyEmail(newEmail)) || duplicateEmail) throw new InvalidEmail();
            else setEmail(newEmail);
            System.out.println("New email address: " + getEmail());
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
            System.out.println("New phone number: " + getPhoneNumber());
        } else if (userChoice == 4) {
            System.out.println("Current address: " + ((PlaygroundOwner) this).getAddress());
            System.out.println("Please enter a new address: ");
            String newAddress = input.nextLine();
            if (newAddress.length() < 1) throw new InvalidAddress();
            else ((PlaygroundOwner) this).setAddress(newAddress);
        }
    }
}
