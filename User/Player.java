package User;

import Exceptions.InsufficientBalance;
import Exceptions.InvalidEmail;
import Exceptions.PlaygroundUnavailable;
import System.Booking;
import System.Ewallet;
import System.Playground;
import System.Time;
import System.Verifier;

import java.util.ArrayList;
import java.util.Scanner;

public class Player extends Account {
    private int favouriteTeamIndex = -1;

    private Ewallet playerWallet;
    private ArrayList<Team> teams;
    private ArrayList<Booking> playerBookings;

    public Player(Verifier verifier) {
        super(verifier);
        playerWallet = new Ewallet();
        teams = new ArrayList<>();
        playerBookings = new ArrayList<>();
    }

    public Ewallet getPlayerWallet() {
        return this.playerWallet;
    }

    public ArrayList<Team> getTeams() {
        return this.teams;
    }

    public ArrayList<Booking> getPlayerBookings() {
        return this.playerBookings;
    }

    /**
     * @param playgrounds
     */
    public void viewPlaygrounds(ArrayList<Playground> playgrounds) {
        for (int i = 0; i < playgrounds.size(); i++) {
            System.out.println('[' + String.valueOf(i + 1) + "] " + playgrounds.get(i));
        }
    }

    /**
     * @param playground
     * @param criterion
     */
    public void filterPlaygrounds(ArrayList<Playground> playground, String criterion) {

        ArrayList<Playground> filteredPlaygrounds = new ArrayList<>();
        Scanner input = new Scanner(System.in);

        Time time = new Time();
        time.setTime();

        if (criterion.equals("timeslot")) {
            filteredPlaygrounds = filterByTimeSlot(playground, time);
        } else {
            System.out.println("Enter address: ");
            String address = input.nextLine();
            filteredPlaygrounds = filterByLocationAndDate(playground, address, time);
        }

        viewPlaygrounds(filteredPlaygrounds);
    }

    /**
     * @param playgrounds
     * @param time
     */
    public ArrayList<Playground> filterByTimeSlot(ArrayList<Playground> playgrounds, Time time) {
        ArrayList<Playground> filteredPlaygrounds = new ArrayList<>();
        for (int i = 0; i < playgrounds.size(); i++) {
            Time playgroundTime = playgrounds.get(i).getAvailableAtTime();
            if (!(time.startsBefore(playgroundTime) || time.endsAfter(playgroundTime))) {
                filteredPlaygrounds.add(playgrounds.get(i));
            }
        }
        return filteredPlaygrounds;
    }

    public ArrayList<Playground> filterByLocationAndDate(ArrayList<Playground> playgrounds, String addressLocation, Time time) {
        ArrayList<Playground> filteredPlaygrounds = new ArrayList<>();
        for (int i = 0; i < playgrounds.size(); i++) {
            Time playgroundTime = playgrounds.get(i).getAvailableAtTime();
            if (!(time.startsBefore(playgroundTime) || time.endsAfter(playgroundTime)) && playgrounds.get(i).getAddress().equalsIgnoreCase(addressLocation)) {
                filteredPlaygrounds.add(playgrounds.get(i));
            }
        }
        return filteredPlaygrounds;
    }

    /**
     * @param bookedPlayground
     */
    public void bookPlayground(Player player, Playground bookedPlayground) throws InsufficientBalance, PlaygroundUnavailable {
        if (bookedPlayground.getPlaygroundStatus().equals("Activated") && bookedPlayground.getAvailability()) {
            Time bookingTime = new Time();
            Booking newBooking = new Booking(getVerifier());
            boolean flag = true;
            System.out.println("Please enter your desired time slot: ");
            boolean validTime = bookingTime.setTime();
            if (validTime) {
                Time playgroundTime = bookedPlayground.getAvailableAtTime();
                if (!(bookingTime.startsBefore(playgroundTime) || bookingTime.endsAfter(playgroundTime))) {
                    newBooking.setTime(bookingTime);
                    newBooking.setPlayer(player);
                    newBooking.setPlayground(bookedPlayground);
                    for (int i = 0; i < bookedPlayground.getBookings().size(); i++) {
                        if (bookedPlayground.getBookings().get(i).getTime().conflicts(bookingTime)) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        double currentBalanace = player.getPlayerWallet().getBalance();
                        double bookingPrice = bookingTime.getDuration() * bookedPlayground.getPrice();
                        if (currentBalanace >= bookingPrice) {
                            double ownerBalance = bookedPlayground.getOwner().getOwnerWallet().getBalance();
                            bookedPlayground.getBookings().add(newBooking);
                            playerBookings.add(newBooking);
                            player.getPlayerWallet().setBalance(currentBalanace - bookingPrice);
                            bookedPlayground.getOwner().getOwnerWallet().setBalance(ownerBalance + bookingPrice);
                        } else {
                            throw new InsufficientBalance();
                        }
                    } else {
                        System.out.println("Invalid time slot chosen");
                    }
                } else {
                    throw new PlaygroundUnavailable();
                }
            } else {
                throw new PlaygroundUnavailable();
            }
        } else {
            throw new PlaygroundUnavailable();
        }
    }

    public void createTeam(Verifier verifier) {
        System.out.println("Insert number of players in the team: ");
        Scanner input = new Scanner(System.in);
        int numberOfPlayers = Integer.parseInt(input.nextLine());
        String playerName;
        String playerEmail;

        Team tempTeam = new Team();
        for (int i = 0; i < numberOfPlayers; i++) {
            System.out.println("Enter name of player number: " + i);
            playerName = input.nextLine();
            System.out.println("Enter email of player number: " + i);
            playerEmail = input.nextLine().toLowerCase();

            boolean duplicateEmail = false;
            for (int j = 0; j < tempTeam.getMembers().size(); j++) {
                if (tempTeam.getMembers().get(j).getEmail().equals(playerEmail)) {
                    duplicateEmail = true;
                    break;
                }
            }

            if (verifier.verifyEmail(playerEmail) && !duplicateEmail) {
                Team.TeamMember tempTeamMember = tempTeam.new TeamMember(playerName, playerEmail);
                tempTeam.getMembers().add(tempTeamMember);
            } else {
                System.out.println("Invalid player email, please try again.");
                i--;
            }
        }

        boolean favouriteTeamNotSet = true;
        for (Team playerTeam : teams) {
            if (playerTeam.isFavouriteTeam()) {
                favouriteTeamNotSet = false;
                break;
            }
        }

        if (favouriteTeamNotSet) {
            String isFavourite = "false";
            while (true) {
                System.out.println("Is this your favourite team? enter true or false");
                isFavourite = input.nextLine();
                if (isFavourite.equalsIgnoreCase("false") || isFavourite.equalsIgnoreCase("true")) break;
            }

            if (isFavourite.equalsIgnoreCase("true")) tempTeam.setFavouriteTeam(true);
        }

        teams.add(tempTeam);

        if (tempTeam.isFavouriteTeam()) favouriteTeamIndex = teams.indexOf(tempTeam);
    }

    public void sendInvitation(Verifier verifier) {
        boolean invitationWasSuccessful = false;

        Scanner input = new Scanner(System.in);
        System.out.println("[0] Cancel");
        System.out.println("[1] Send invitation to all team member");
        System.out.println("[2] Send invitation to a specific member");
        int userChoice = getVerifier().getUserChoice(0, 2);
        if (userChoice == 1) {
            int teamMemberSize = teams.get(favouriteTeamIndex).getMembers().size();
            for (int i = 0; i < teamMemberSize; i++) {
                invitationWasSuccessful = sendEmailInvitation(teams.get(favouriteTeamIndex).getMembers().get(i).getEmail());
            }
        } else if (userChoice == 2) {
            System.out.println("Enter number of players you want to invite");
            int numberOfPlayersToInvite = 0;
            while (true) {
                try {
                    numberOfPlayersToInvite = Integer.parseInt(input.nextLine());
                    if (numberOfPlayersToInvite > 0) break;
                } catch (NumberFormatException ex) {
                    System.out.println("Please enter a valid digit");
                }
            }
            String tempEmail;
            for (int i = 0; i < numberOfPlayersToInvite; i++) {
                System.out.println("Enter email of player number " + i + " to invite");
                tempEmail = input.nextLine();
                if (verifier.verifyEmail(tempEmail)) {
                    invitationWasSuccessful = sendEmailInvitation(tempEmail);
                } else {
                    System.out.println("Invalid email address, please try again");
                    i--;
                }
            }
        }
        if (invitationWasSuccessful) System.out.println("Invitation sent successfully");

    }

    public boolean sendEmailInvitation(String email) {
        boolean emailFound = false;
        for (int i = 0; i < teams.size(); i++) {
            for (int j = 0; j < teams.get(i).getMembers().size(); j++) {
                if (teams.get(i).getMembers().get(j).getEmail() == email) emailFound = true;//then send invitation
            }
        }
        return emailFound;
    }

    public void cancelBooking(Booking booking) {
        int cancellationPeriod = booking.getPlayground().getCancelationPeriod();
        Time cancellationTime = new Time();
        cancellationTime.setCurrentTime();
        cancellationTime.extend(cancellationPeriod);

        if (!cancellationTime.conflicts(booking.getTime())) {
            int bookingDuration = booking.getTime().getDuration();
            double bookingPrice = booking.getPlayground().getPrice() * bookingDuration;
            double playerBalance = booking.getPlayer().getPlayerWallet().getBalance();
            double ownerBalance = booking.getPlayground().getOwner().getOwnerWallet().getBalance();
            booking.getPlayground().getOwner().getOwnerWallet().setBalance(ownerBalance - bookingPrice);
            booking.getPlayer().getPlayerWallet().setBalance(playerBalance + bookingPrice);
        } else {
            System.out.println("Cancellation period passed, booking value not refunded");
        }
        booking.getPlayer().getPlayerBookings().remove(booking);
        booking.getPlayground().getBookings().remove(booking);
        System.out.println("Booking cancelled");
    }

    public void modifyTeam(Team modifyTeam) throws InvalidEmail {
        System.out.println("===========================");
        System.out.println("What would you like to change: ");
        System.out.println("[0] Cancel");
        System.out.println("[1] Team Name");
        System.out.println("[2] Team Members");
        System.out.println("[3] Set as favourite team");
        int userChoice = getVerifier().getUserChoice(0, 3);
        if (userChoice == 1) {
            Scanner input = new Scanner(System.in);
            System.out.println("Current team name: " + modifyTeam.getTeamName());
            String newName = input.nextLine();
            if (newName.length() < 1) System.out.println("Invalid team name");
            else modifyTeam.setTeamName(newName);
            System.out.println("===========================");
            System.out.println("New team name: " + modifyTeam.getTeamName());
        } else if (userChoice == 2) {
            System.out.println("===========================");
            System.out.println("Team Members: ");
            System.out.println("[0] Cancel");
            for (int i = 0; i < modifyTeam.getMembers().size(); i++) {
                System.out.println('[' + String.valueOf(i + 1) + "] " + modifyTeam.getMembers().get(i));
            }
            userChoice = getVerifier().getUserChoice(0, modifyTeam.getMembers().size());
            if (userChoice != 0) {
                System.out.println("===========================");
                System.out.println("What would you like to edit: ");
                System.out.println("[0] Cancel");
                System.out.println("[1] Member Name");
                System.out.println("[2] Member Email");
                userChoice = getVerifier().getUserChoice(0, 2);
                if (userChoice == 1) {
                    Scanner input = new Scanner(System.in);
                    System.out.println("Current member name: " + modifyTeam.getMembers().get(userChoice - 1).getName());
                    String newName = input.nextLine();
                    if (newName.length() < 1) System.out.println("Invalid team name");
                    else modifyTeam.getMembers().get(userChoice - 1).setName(newName);
                    System.out.println("===========================");
                    System.out.println("New member name: " + modifyTeam.getMembers().get(userChoice - 1).getName());
                } else if (userChoice == 2) {
                    Scanner input = new Scanner(System.in);
                    System.out.println("Current member email: " + modifyTeam.getMembers().get(userChoice - 1).getEmail());
                    String newEmail = input.nextLine().toLowerCase();
                    boolean duplicateEmail = false;
                    for (int i = 0; i < modifyTeam.getMembers().size(); i++) {
                        if (modifyTeam.getMembers().get(i).getEmail().equals(newEmail)) {
                            duplicateEmail = true;
                            break;
                        }
                    }
                    if ((!getVerifier().verifyEmail(newEmail)) || duplicateEmail) throw new InvalidEmail();
                    else modifyTeam.getMembers().get(userChoice - 1).setEmail(newEmail);
                    System.out.println("===========================");
                    System.out.println("New member email: " + modifyTeam.getMembers().get(userChoice - 1).getEmail());
                }
            }
        } else if (userChoice == 3) {
            if (modifyTeam.isFavouriteTeam()) System.out.println("Team is already favourite team");
            else {
                for (int i = 0; i < teams.size(); i++) {
                    if (teams.get(i).isFavouriteTeam()) {
                        teams.get(i).setFavouriteTeam(false);
                        break;
                    }
                }
                modifyTeam.setFavouriteTeam(true);
                favouriteTeamIndex = teams.indexOf(modifyTeam);
                System.out.println("Favourite team changed successfully");
            }
        }
    }

    @Override
    public String toString() {
        return "Player user name:" + getUserName() + '\n' +
                "Player favourite team:\n" + teams.get(favouriteTeamIndex) + '\n' +
                "Player email:" + getEmail() + '\n' +
                "Player phone number:" + getPhoneNumber();
    }
}