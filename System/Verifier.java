package System;

import User.Account;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Verifier is an interface class
 * responsible for verifying the emails, passwords, phone number, username,
 * as well as sending confirmation
 */
public interface Verifier {

    /**
     * @param email to be verified
     * @return whether or not the email is valid
     */
    boolean verifyEmail(String email);

    /**
     * @param account the account to check if the password is matching
     * @param password to be verified
     * @return whether or not the password matches the one stored in the account
     */
    boolean verifyPassword(Account account, String password);

    /**
     * @param number to be verified
     * @return whether or not the entered number is valid and not duplicate
     */
    boolean verifyNumber(String number);

    /**
     * @param email to be verified
     * @return a random confirmation number for the account
     */
    int sendConfirmation(String email);

    /**
     * @param username to be verified
     * @return it verifies username. returns index of username if found,
     *  if not found returns -2 if username length is below 1
     *  if not found but username is more than 1 then returns -1
     */
    int verifyUserName(String username);

    /**
     * getUserChoice is responsibile for getting the user choice,
     * and guarantees that the choice is between the min and max value that was passed by the menu
     * @param min minimum value in the menu
     * @param max maximam value in the menu
     * @return userChoice which is between the min and max value
     */
    int getUserChoice(int min, int max);

}
