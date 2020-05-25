package System;

import User.Account;
import User.Administrator;
import User.Player;
import User.PlaygroundOwner;
import Exceptions.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Application implements Verifier {

    private ArrayList<Account> accounts;
    private ArrayList<Playground> unapprovedPlaygrounds;
    private ArrayList<Playground> allPlaygrounds;

    public Application() {
        accounts = new ArrayList<>();
        unapprovedPlaygrounds = new ArrayList<>();
        allPlaygrounds = new ArrayList<>();
        accounts.add(new Administrator(this));
    }

    public int getUserChoice(int min, int max) {
        Scanner input = new Scanner(System.in);
        int userChoice = min - 1;
        boolean flag = true;
        while (flag) {
            try {
                userChoice = Integer.parseInt(input.nextLine());
                while (userChoice < min || userChoice > max) {
                    System.out.println("Please enter a valid number:");
                    userChoice = Integer.parseInt(input.nextLine());
                }
                flag = false;
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid digit");
            }
        }
        return userChoice;
    }

    /**
     * @param admin
     */
    private void menu(Administrator admin) {
        boolean flag = true;
        while (flag) {
            Scanner input = new Scanner(System.in);
            System.out.println("===========================");
            System.out.println("Administrator Menu");
            System.out.println("===========================");
            System.out.println("Choose from the following:");
            System.out.println("[1] Approve Playground");
            System.out.println("[2] Suspend Playground");
            System.out.println("[3] Delete Playground");
            System.out.println("[4] Activate Playground");
            System.out.println("[5] Log out");
            int userChoice = getUserChoice(1, 5);
            if (userChoice == 1) {
                int counter = 1;
                System.out.println("Displaying all unapproved playgrounds:");
                System.out.println("[0] Cancel");
                for (Playground playground : unapprovedPlaygrounds) {
                    System.out.println("[" + String.valueOf(counter) + "] " + playground.getPlaygroundName());
                    counter++;
                }
                int approvedPlaygroundIndex = getUserChoice(0, unapprovedPlaygrounds.size());
                if (approvedPlaygroundIndex != 0) {
                    admin.approvePlayground(unapprovedPlaygrounds.get(approvedPlaygroundIndex - 1));
                    allPlaygrounds.add(unapprovedPlaygrounds.get(approvedPlaygroundIndex - 1));
                    unapprovedPlaygrounds.remove(approvedPlaygroundIndex - 1);
                    System.out.println("Playground approved");
                }
            } else if (userChoice == 2) {
                System.out.println("===========================");
                System.out.println("[0] Cancel");
                for (int i = 0; i < allPlaygrounds.size(); i++) {
                    System.out.println('[' + String.valueOf(i + 1) + "] " + allPlaygrounds.get(i));
                }
                System.out.println("Choose a playground to suspend: ");
                userChoice = getUserChoice(0, allPlaygrounds.size());
                if (userChoice != 0) {
                    admin.suspendPlayground(allPlaygrounds.get(userChoice - 1));
                    System.out.println("Playground suspended");
                }
            } else if (userChoice == 3) {
                System.out.println("===========================");
                System.out.println("[0] Cancel");
                for (int i = 0; i < allPlaygrounds.size(); i++) {
                    System.out.println('[' + String.valueOf(i + 1) + "] " + allPlaygrounds.get(i).getPlaygroundName());
                }
                System.out.println("Choose a playground to delete: ");
                userChoice = getUserChoice(0, allPlaygrounds.size());
                if (userChoice != 0) {
                    admin.deletePlayground(allPlaygrounds, unapprovedPlaygrounds, allPlaygrounds.get(userChoice - 1));
                    System.out.println("Playground deleted and all bookings refunded");
                }
            } else if (userChoice == 4) {
                System.out.println("===========================");
                System.out.println("[0] Cancel");
                for (int i = 0; i < allPlaygrounds.size(); i++) {
                    System.out.println('[' + String.valueOf(i + 1) + "] " + allPlaygrounds.get(i).getPlaygroundName());
                }
                System.out.println("Choose a playground to activate: ");
                userChoice = getUserChoice(0, allPlaygrounds.size());
                if (userChoice != 0) {
                    admin.activatePlayground(allPlaygrounds.get(userChoice - 1));
                    System.out.println("Playground activated");
                }
            } else {
                flag = false;
            }
        }
    }

    /**
     * @param player
     */
    private void menu(Player player) {
        boolean flag = true;
        while (flag) {
            Scanner input = new Scanner(System.in);
            System.out.println("===========================");
            System.out.println("Player Menu");
            System.out.println("===========================");
            System.out.println("Choose from the following:");
            System.out.println("[1] View playgrounds");
            System.out.println("[2] Book a playground");
            System.out.println("[3] Create a team");
            System.out.println("[4] Send invitation");
            System.out.println("[5] Cancel booking");
            System.out.println("[6] Edit player info");
            System.out.println("[7] Modify team");
            System.out.println("[8] Filter by time slot");
            System.out.println("[9] Filter by location and date");
            System.out.println("[10] Log out");
            int userChoice = getUserChoice(1, 10);
            if (userChoice == 1) {
                player.viewPlaygrounds(allPlaygrounds);
            } else if (userChoice == 2) {
                System.out.println("===========================");
                System.out.println("[0] Cancel");
                player.viewPlaygrounds(allPlaygrounds);
                System.out.println("===========================");
                System.out.println("Please choose a number:");
                userChoice = getUserChoice(0, allPlaygrounds.size());
                if (userChoice != 0) {
                    try {
                        player.bookPlayground(player, allPlaygrounds.get(userChoice - 1));
                        System.out.println("Booking done");
                    } catch (InsufficientBalance ex) {
                        System.out.println("Your balance is insufficient for the booking.");
                    } catch (PlaygroundUnavailable ex) {
                        System.out.println("The time slot you picked is unavailable for booking");
                    }
                }
            } else if (userChoice == 3) {
                player.createTeam(this);
            } else if (userChoice == 4) {
                player.sendInvitation(this);
            } else if (userChoice == 5) {
                System.out.println("===========================");
                System.out.println("[0] Cancel");
                for (int i = 0; i < player.getPlayerBookings().size(); i++) {
                    System.out.println('[' + String.valueOf(i + 1) + "] " + player.getPlayerBookings().get(i));
                }
                System.out.println("===========================");
                System.out.println("Please choose a number:");
                userChoice = getUserChoice(0, player.getPlayerBookings().size());
                if (userChoice != 0) {
                    player.cancelBooking(player.getPlayerBookings().get(userChoice - 1));
                }
            } else if (userChoice == 6) {
                try {
                    player.updateProfile(accounts);
                } catch (InvalidPassword ex) {
                    System.out.println("Invalid password, please try again.");
                } catch (InvalidEmail ex) {
                    System.out.println("Invalid email, please try again.");
                } catch (InvalidNumber ex) {
                    System.out.println("Invalid number, please try again.");
                } catch (InvalidAddress ex) {
                    System.out.println("Invalid address, please try again.");
                }
            } else if (userChoice == 7) {
                System.out.println("===========================");
                System.out.println("[0] Cancel");
                for (int i = 0; i < player.getTeams().size(); i++) {
                    System.out.println('[' + String.valueOf(i + 1) + "] " + player.getTeams().get(i).getTeamName());
                }
                System.out.println("===========================");
                System.out.println("Please choose a number:");
                userChoice = getUserChoice(0, player.getTeams().size());
                if (userChoice != 0){
                    try {
                        player.modifyTeam(player.getTeams().get(userChoice - 1));
                    } catch (InvalidEmail ex) {
                        System.out.println("Invalid email entered");
                    }
                }
            } else if (userChoice == 8) {
                player.filterPlaygrounds(allPlaygrounds, "timeslot");
            } else if (userChoice == 9) {
                player.filterPlaygrounds(allPlaygrounds, "location");
            } else {
                flag = false;
            }
        }
    }

    /**
     * @param playgroundOwner
     */
    private void menu(PlaygroundOwner playgroundOwner) {
        boolean flag = true;
        while (flag) {
            Scanner input = new Scanner(System.in);
            System.out.println("===========================");
            System.out.println("Playground Owner Menu");
            System.out.println("===========================");
            System.out.println("Choose from the following:");
            System.out.println("[1] View All Playgrounds");
            System.out.println("[2] Add New Playground");
            System.out.println("[3] View Pending Playground Requests");
            System.out.println("[4] View Bookings");
            System.out.println("[5] View E-Wallet");
            System.out.println("[6] Edit Profile");
            System.out.println("[7] Log out");
            int userChoice = getUserChoice(1, 7);
            if (userChoice == 1) {
                int playgroundsCount = playgroundOwner.getPlaygrounds().size();
                System.out.println("===========================");
                System.out.println("[0] Cancel");
                for (int i = 0; i < playgroundsCount; i++) {
                    System.out.println('[' + String.valueOf(i + 1) + "] " + playgroundOwner.getPlaygrounds().get(i).getPlaygroundName());
                }
                System.out.println("===========================");
                System.out.println("Please choose a playground to modify or enter 0 to return to main menu: ");
                int chosenPlayground = getUserChoice(0, playgroundsCount);
                if (chosenPlayground != 0) {
                    playgroundOwner.updatePlayground(allPlaygrounds, unapprovedPlaygrounds, playgroundOwner.getPlaygrounds().get(chosenPlayground - 1));
                }
            } else if (userChoice == 2) {
                playgroundOwner.addPlayground(allPlaygrounds, unapprovedPlaygrounds);
            } else if (userChoice == 3) {
                playgroundOwner.viewPending();
            } else if (userChoice == 4) {
                int playgroundsCount = playgroundOwner.getPlaygrounds().size();
                System.out.println("===========================");
                System.out.println("[0] Cancel");
                for (int i = 0; i < playgroundsCount; i++) {
                    System.out.println('[' + String.valueOf(i + 1) + "] " + playgroundOwner.getPlaygrounds().get(i).getPlaygroundName());
                }
                System.out.println("===========================");
                System.out.println("Please choose a playground to view its bookings or enter 0 to return to main menu: ");
                int chosenPlayground = getUserChoice(0, playgroundsCount);
                if (chosenPlayground != 0) {
                    playgroundOwner.viewBookings(playgroundOwner.getPlaygrounds().get(chosenPlayground - 1));
                }
            } else if (userChoice == 5) {
                playgroundOwner.checkEwallet();
            } else if (userChoice == 6) {
                try {
                    playgroundOwner.updateProfile(accounts);
                } catch (InvalidPassword ex) {
                    System.out.println("Invalid password, please try again.");
                } catch (InvalidEmail ex) {
                    System.out.println("Invalid email, please try again.");
                } catch (InvalidNumber ex) {
                    System.out.println("Invalid number, please try again.");
                } catch (InvalidAddress ex) {
                    System.out.println("Invalid address, please try again.");
                }
            } else {
                flag = false;
            }
        }
    }

    private void displayPlaygroundOwners() {
        System.out.println("===========================");
        System.out.println("Playground Owners: ");
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i) instanceof PlaygroundOwner) {
                System.out.println('[' + String.valueOf(i + 1) + "] " + accounts.get(i));
            }
        }
    }

    private void displayPlayers() {
        System.out.println("===========================");
        System.out.println("Players: ");
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i) instanceof Player) {
                System.out.println('[' + String.valueOf(i + 1) + "] " + accounts.get(i));
            }
        }
    }

    public void launch() {
        boolean flag = true;
        while (flag) {
            Scanner input = new Scanner(System.in);
            System.out.println("Welcome to GoFo Application");
            System.out.println("===========================");
            System.out.println("[1] Login to an existing account");
            System.out.println("[2] Register a new account");
            System.out.println("[3] Exit");
            System.out.println("===========================");
            int userChoice = getUserChoice(1, 3);
            if (userChoice == 1) {
                int currentUserIndex = -1;
                try {
                    currentUserIndex = login();
                } catch (InvalidUserName ex) {
                    System.out.println("Invalid username, please try again.");
                } catch (InvalidPassword ex) {
                    System.out.println("Invalid password, please try again.");
                }
                if (currentUserIndex != -1) {
                    Account currentAccount = accounts.get(currentUserIndex);
                    if (currentAccount instanceof Administrator) menu((Administrator) currentAccount);
                    else if (currentAccount instanceof Player) menu((Player) currentAccount);
                    else menu((PlaygroundOwner) currentAccount);
                }
            } else if (userChoice == 2) {
                try {
                    register();
                } catch (InvalidUserName ex) {
                    System.out.println("Username already exists or is invalid, please try again.");
                } catch (InvalidPassword ex) {
                    System.out.println("Invalid password, please try again.");
                } catch (InvalidEmail ex) {
                    System.out.println("Invalid email, please try again.");
                } catch (InvalidNumber ex) {
                    System.out.println("Invalid number, please try again.");
                } catch (InvalidConfirmation ex) {
                    System.out.println("Invalid confirmation code, please try again.");
                } catch (InvalidAddress ex) {
                    System.out.println("Invalid address, please try again.");
                }
            } else {
                System.out.println("Exiting...");
                flag = false;
            }
        }
    }

    /**
     * @param email
     */
    @Override
    public boolean verifyEmail(String email) {
        if (email.contains(" ")) return false;
        int countAt = 0, countDot = 0;
        for (int i = 0; i < email.length(); i++) {
            int charAscii = (int) email.charAt(i);
            if (charAscii >= 65 && charAscii <= 90) continue;
            else if (charAscii >= 97 && charAscii <= 122) continue;
            else if (charAscii >= 48 && charAscii <= 57) continue;
            else if (email.charAt(i) == '-') continue;
            else if (email.charAt(i) == '_') {
                if (countAt > 0) return false;
            } else if (email.charAt(i) == '@') {
                if (countAt > 0) return false;
                else countAt++;
            } else if (email.charAt(i) == '.') {
                if (countAt == 0) return false;
                else countDot++;
            } else return false;
        }
        if (countAt == 0 || countDot == 0) return false;
        else return true;
    }

    /**
     * @param account
     * @param password
     */
    @Override
    public boolean verifyPassword(Account account, String password) {
        if (account.getPassword().equals(password)) return true;
        else return false;
    }

    /**
     * @param number
     */
    @Override
    public boolean verifyNumber(String number) {
        if (number.length() < 11) return false;
        try {
            int phoneNumber = Integer.parseInt(number);
            if (phoneNumber < 0) return false;
            for (int i = 0; i < accounts.size(); i++) {
                if (accounts.get(i).getPhoneNumber().equals(number)) return false;
            }
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    /**
     * @param email
     */
    @Override
    public int sendConfirmation(String email) {
        int confirmationNumber = (int) (Math.random() * 9999) + 1000;
        return confirmationNumber;
    }

    /**
     * @param playgrounds
     * @param bookings
     */
    @Override
    public boolean verifyBookings(ArrayList<Playground> playgrounds, Booking bookings) {
        return false;
    }

    /**
     * @param username
     */
    public int verifyUserName(String username) {
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getUserName().equals(username)) return i;
        }
        if (username.length() < 1) return -2;
        return -1;
    }

    public int login() throws InvalidUserName, InvalidPassword {
        Scanner input = new Scanner(System.in);
        String username = null;
        String password = null;
        System.out.println("Username: ");
        username = input.nextLine();
        System.out.println("Password: ");
        password = input.nextLine();
        int userNameValid = verifyUserName(username);
        if (userNameValid > -1) {
            boolean passwordValid = verifyPassword(accounts.get(userNameValid), password);
            if (passwordValid) return userNameValid;
            else throw new InvalidPassword();
        }
        throw new InvalidUserName();
    }

    public void register() throws InvalidUserName, InvalidPassword, InvalidEmail, InvalidNumber, InvalidConfirmation, InvalidAddress {
        Scanner input = new Scanner(System.in);
        boolean flag = true;
        String userName = "";
        String password = "";
        String email = "";
        String phoneNumber = "";
        String confirmationNumber = "";
        String address = "";
        Account newAccount = null;
        while (flag) {
            System.out.println("What account would you like to register?");
            System.out.println("===========================");
            System.out.println("[1] Player");
            System.out.println("[2] Playground Owner");
            System.out.println("[3] Exit");
            System.out.println("===========================");

            int userChoice = getUserChoice(1, 3);

            if (userChoice == 1) {
                newAccount = new Player(this);
            } else if (userChoice == 2) {
                newAccount = new PlaygroundOwner(this);
            } else {
                System.out.println("Going back to main menu...");
                flag = false;
            }
            if (flag) {
                System.out.println("Please enter a username: ");
                userName = input.nextLine();
                if (verifyUserName(userName) != -1) throw new InvalidUserName();
                System.out.println("Please enter a password: ");
                password = input.nextLine();
                if (password.length() < 6) throw new InvalidPassword();
                System.out.println("Please enter an email: ");
                email = input.nextLine().toLowerCase();
                boolean duplicateEmail = false;
                for (int i = 0; i < accounts.size(); i++) {
                    if (accounts.get(i).getEmail().equals(email)) {
                        duplicateEmail = true;
                        break;
                    }
                }
                if ((!verifyEmail(email)) || duplicateEmail) throw new InvalidEmail();
                System.out.println("Please enter a phone number: ");
                phoneNumber = input.nextLine();
                if (!verifyNumber(phoneNumber)) throw new InvalidNumber();
                if (newAccount instanceof PlaygroundOwner) {
                    System.out.println("Please enter an address: ");
                    address = input.nextLine();
                    if (address.length() < 1) throw new InvalidAddress();
                }
                boolean confirmationFlag = true;
                while (confirmationFlag) {
                    System.out.println("Please enter the confirmation number: ");
                    confirmationNumber = input.nextLine();
                    try {
                        int userConfirmationNumber = Integer.parseInt(confirmationNumber);
                        if (userConfirmationNumber < 0) {
                            System.out.println("Invalid confirmation number, please try again");
                        } else {
                            confirmationFlag = false;
                        }
                    } catch (NumberFormatException ex) {
                        System.out.println("Please enter a valid confirmation number");
                    }
                }
                newAccount.register(userName, password, email, phoneNumber);
                if (newAccount instanceof PlaygroundOwner) ((PlaygroundOwner) newAccount).setAddress(address);
                accounts.add(newAccount);
                System.out.println("Account registered successfully!");
                flag = false;
            }
        }
    }

}