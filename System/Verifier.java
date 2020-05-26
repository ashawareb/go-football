package System;

import User.Account;

import java.util.ArrayList;
import java.util.Scanner;

public interface Verifier {

    /**
     * @param email
     */
    boolean verifyEmail(String email);

    /**
     * @param password
     */
    boolean verifyPassword(Account account, String password);

    /**
     * @param number
     */
    boolean verifyNumber(String number);

    /**
     * @param email
     */
    int sendConfirmation(String email);

    int verifyUserName(String username);

    int getUserChoice(int min, int max);

}