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

/**
 * Player class responsible for holding all the important methods of the player
 */
public class Player extends Account {
    /**
     * favourtie team index
     */
    private int favouriteTeamIndex = -1;
    /**
     * player arrayList of teams
     */
    private ArrayList<Team> teams;
    /**
     * player arrayList of bookings
     */
    private ArrayList<Booking> playerBookings;

    /**
     * PLayer constructor for initialising the attributes of the player wallet, teams and bookings
     * @param verifier verifier object from verifier class to call super class of Account
     */
    public Player(Verifier verifier) {
        super(verifier);
        teams = new ArrayList<>();
        playerBookings = new ArrayList<>();
    }



    /**
     * @return getter for player teams
     */
    public ArrayList<Team> getTeams() {
        return this.teams;
    }

    /**
     * @return getter for playerg bookings
     */
    public ArrayList<Booking> getPlayerBookings() {
        return this.playerBookings;
    }

    /**
     * @param playgrounds takes all playgrounds and loops through them all then print one by one
     */
    public void viewPlaygrounds(ArrayList<Playground> playgrounds) {
        for (int i = 0; i < playgrounds.size(); i++) {
            System.out.println('[' + String.valueOf(i + 1) + "] " + playgrounds.get(i));
        }
    }

    /**
     * filter playground method is used to filter playground based on the criterion
     * if the criterion is timeSlot, then it calls the function filterByTimeSlot
     * if the criterion is location, then it calls the function filterByLocationAndDate
     * @param playground all the active playgrounds to be filtered
     * @param criterion the criterion on which the playgrounds will be filtered
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
     * filterByTimeSlot takes all the playgrounds and then the specific time that the player wants
     * then checks which of the playgrounds satisfies the time slot and adds it to the filteredPlaygrounds
     * @param playgrounds all the playgrounds to be filtered
     * @param time the time slot on which the user wants
     * @return the filteredPlaygrounds
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

    /**
     * filterByLocationAndDate takes all the playgrounds and then the specific time and location that the player wants
     * then checks which of the playgrounds satisfies the time slot and location then adds it to the filteredPlaygrounds
     * @param playgrounds all the playgrounds to be filtered
     * @param addressLocation the location to be compared with
     * @param time the time slot on which the user wants
     * @return the filteredPlaygrounds
     */
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
     * method responsible for booking a playground
     * it initiates a new time
     * then requires the player to enter the desired time slot to be booked
     * the method checks whether the balance is enough for the booking process or not
     * the method checks if this playground is booked previously or not
     * the method then checks whether the time slot is within the range of the time that the playground is available
     * if the time slot is within the available time slot of the playground, the method adds the booked playground to
     * the user booking arraylist
     * the method checks if this playground is booked previously or not
     * otherwise throws an exception
     * @param player player that wants to book
     * @param bookedPlayground playground that the user want to book
     * @throws InsufficientBalance throws exception if balance is insuffiecnt for the booking process
     * @throws PlaygroundUnavailable throws exception if the playground is already booked before
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
                        double currentBalanace = player.getWallet().getBalance();
                        double bookingPrice = bookingTime.getDuration() * bookedPlayground.getPrice();
                        if (currentBalanace >= bookingPrice) {
                            double ownerBalance = bookedPlayground.getOwner().getWallet().getBalance();
                            bookedPlayground.getBookings().add(newBooking);
                            playerBookings.add(newBooking);
                            player.getWallet().setBalance(currentBalanace - bookingPrice);
                            bookedPlayground.getOwner().getWallet().setBalance(ownerBalance + bookingPrice);
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

    /**
     * create team method is responsible for adding a new team to the player attributes
     * it takes how many players the user wants to add, then loops through them all to take the name and email
     * then check if valid team members
     * @param verifier verified used to verify the team member attributes
     */
    public void createTeam(Verifier verifier) {
        Scanner input = new Scanner(System.in);
        Team tempTeam = new Team();
        System.out.println("What would you like to call your team: ");
        String teamName = input.nextLine();
        while (teamName.length() < 1) {
            System.out.println("Invalid team name, please try again");
            teamName = input.nextLine();
        }
        tempTeam.setTeamName(teamName);
        System.out.println("===========================");
        System.out.println("Insert number of players in the team: ");
        int numberOfPlayers = 0;
        while (numberOfPlayers < 1) {
            try {
                numberOfPlayers = Integer.parseInt(input.nextLine());
                if (numberOfPlayers < 1) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                System.out.println("Invalid number, please try again");
            }
        }

        String playerName;
        String playerEmail;

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
        System.out.println("Team created successfully");
        if (tempTeam.isFavouriteTeam()) favouriteTeamIndex = teams.indexOf(tempTeam);
    }

    /**
     * send invitation method is responsible for sending invitation to the team and team member
     * based on what the player chooses to invite
     * if the player chose to invite the favourite team, then the method calls the sendInvitationMethod to send invitation to all favourite team members
     * if the player chose to invite only a specific number of members, the player enters how many players and the method then invites these team members
     */
    public void sendInvitation() {
        Team teamToInvite = pickTeam();
        boolean invitationWasSuccessful = false;
        if (teamToInvite != null) {
            System.out.println("===========================");
            System.out.print("Sending invitations to team: " + teamToInvite.getTeamName());
            System.out.println("\n===========================");
            System.out.println("[0] Cancel");
            System.out.println("[1] Send invitation to all team members");
            System.out.println("[2] Send invitation to a specific member");
            int userChoice = getVerifier().getUserChoice(0, 2);
            if (userChoice == 1) {
                int teamMemberSize = teamToInvite.getMembers().size();
                for (int i = 0; i < teamMemberSize; i++) {
                    invitationWasSuccessful = sendEmailInvitation(teamToInvite.getMembers().get(i).getEmail());
                }
                System.out.println("Successfully invited all team members");
            } else if (userChoice == 2) {
                Team.TeamMember memberToInvite = pickTeamMember(teamToInvite);
                if (memberToInvite != null) {
                    sendEmailInvitation(memberToInvite.getEmail());
                    System.out.println("Invitation sent successfully to " + memberToInvite.getEmail());
                } else {
                    System.out.println("Invitation not sent");
                }
            }
        } else {
            System.out.println("No teams invited");
        }
    }

    /**
     * @param email checks if that email exists in the team, if it is, sends an invitation
     * @return whether or not the email was found
     */
    public boolean sendEmailInvitation(String email) {
        boolean emailFound = false;
        for (int i = 0; i < teams.size(); i++) {
            for (int j = 0; j < teams.get(i).getMembers().size(); j++) {
                if (teams.get(i).getMembers().get(j).getEmail().equalsIgnoreCase(email))
                    emailFound = true;//then send invitation
            }
        }
        return emailFound;
    }

    /**
     * a method for cancelling booking
     * the method takes a booking as a parameter then compares current time with the booking initial time
     * if the current time passes the cancellation period, the method displays a message to the player that the
     * cancellation period was passed and will not refund the player, if not, then the booking is cancelled and the player is refunded.
     * @param booking booking to be cancelled
     */
    public void cancelBooking(Booking booking) {
        int cancellationPeriod = booking.getPlayground().getCancelationPeriod();
        Time cancellationTime = new Time();
        cancellationTime.setCurrentTime();
        cancellationTime.extend(cancellationPeriod);

        if (!cancellationTime.conflicts(booking.getTime())) {
            int bookingDuration = booking.getTime().getDuration();
            double bookingPrice = booking.getPlayground().getPrice() * bookingDuration;
            double playerBalance = booking.getPlayer().getWallet().getBalance();
            double ownerBalance = booking.getPlayground().getOwner().getWallet().getBalance();
            booking.getPlayground().getOwner().getWallet().setBalance(ownerBalance - bookingPrice);
            booking.getPlayer().getWallet().setBalance(playerBalance + bookingPrice);
        } else {
            System.out.println("Cancellation period passed, booking value not refunded");
        }
        booking.getPlayer().getPlayerBookings().remove(booking);
        booking.getPlayground().getBookings().remove(booking);
        System.out.println("Booking cancelled");
    }

    /**
     * a method for modifying team
     * the method displays a menu asking the player whether he wants to modify name or team members or favourite team
     * @param modifyTeam team to be modified
     * @throws InvalidEmail throws exception if email is invalid
     */
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
            Team.TeamMember member = pickTeamMember(modifyTeam);
            if (member != null) {
                System.out.println("===========================");
                System.out.println("What would you like to edit: ");
                System.out.println("[0] Cancel");
                System.out.println("[1] Member Name");
                System.out.println("[2] Member Email");
                userChoice = getVerifier().getUserChoice(0, 2);
                if (userChoice == 1) {
                    Scanner input = new Scanner(System.in);
                    System.out.println("Current member name: " + member.getName());
                    String newName = input.nextLine();
                    if (newName.length() < 1) System.out.println("Invalid team name");
                    else member.setName(newName);
                    System.out.println("===========================");
                    System.out.println("New member name: " + member.getName());
                } else if (userChoice == 2) {
                    Scanner input = new Scanner(System.in);
                    System.out.println("Current member email: " + member.getEmail());
                    String newEmail = input.nextLine().toLowerCase();
                    boolean duplicateEmail = false;
                    for (int i = 0; i < modifyTeam.getMembers().size(); i++) {
                        if (modifyTeam.getMembers().get(i).getEmail().equals(newEmail)) {
                            duplicateEmail = true;
                            break;
                        }
                    }
                    if ((!getVerifier().verifyEmail(newEmail)) || duplicateEmail) throw new InvalidEmail();
                    else member.setEmail(newEmail);
                    System.out.println("===========================");
                    System.out.println("New member email: " + member.getEmail());
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

    /**
     * displays all teams and requires the player to choose one of them then returns that chosen team
     * @return the chosen team
     */
    public Team pickTeam() {
        System.out.println("===========================");
        System.out.println("Your Teams");
        System.out.println("===========================");
        System.out.println("[0] Cancel");
        for (int i = 0; i < getTeams().size(); i++) {
            String isFavourite = (getTeams().get(i).isFavouriteTeam()) ? "[FAVOURITE] " : "";
            System.out.println('[' + String.valueOf(i + 1) + "] " + isFavourite + getTeams().get(i).getTeamName());
        }
        System.out.println("===========================");
        System.out.println("Please choose a number:");
        int userChoice = getVerifier().getUserChoice(0, getTeams().size());
        if (userChoice == 0) return null;
        return getTeams().get(userChoice - 1);
    }

    /**
     * takes a specific team, then requires the player to choose one of the team members, then returns that member
     * @param team the team to be chosen from
     * @return the chosen team member
     */
    public Team.TeamMember pickTeamMember(Team team) {
        System.out.println("===========================");
        System.out.println(team.getTeamName() + " team Members: ");
        System.out.println("[0] Cancel");
        for (int i = 0; i < team.getMembers().size(); i++) {
            System.out.println('[' + String.valueOf(i + 1) + "] " + team.getMembers().get(i));
        }
        int userChoice = getVerifier().getUserChoice(0, team.getMembers().size());
        if (userChoice == 0) return null;
        return team.getMembers().get(userChoice - 1);
    }

    /**
     * @return String containing all the player information such as the name, favourite team, email and phone number
     */
    @Override
    public String toString() {
        return "Player user name:" + getUserName() + '\n' +
                "Player favourite team:\n" + teams.get(favouriteTeamIndex) + '\n' +
                "Player email:" + getEmail() + '\n' +
                "Player phone number:" + getPhoneNumber();
    }
}
