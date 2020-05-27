package User;

import System.Playground;
import System.Ewallet;
import System.Application;
import System.Time;
import System.Verifier;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * PLaygroundOwner class, responsible for holding all the important attributes and operations of the playground owner
 */
public class PlaygroundOwner extends Account {

    /**
     * the playground owner address
     */
    private String address;
    /**
     * the playground owner arraylist of playgrounds that he holds
     */
    private ArrayList<Playground> playgrounds;
    /**
     * the playground owner wallet
     */
    private Ewallet ownerWallet;

    /**
     * PLaygroundOwner constructor, responsible for initialising the address, playgrounds and wallet of the playground owner
     * @param verifier to call the account constructor
     */
    public PlaygroundOwner(Verifier verifier) {
        super(verifier);
        address = "";
        playgrounds = new ArrayList<>();
        ownerWallet = new Ewallet();
    }

    /**
     * @return getter for playground owner address
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * @param address setter for playground owner address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return getter for playground owner arrayList of playgrounds
     */
    public ArrayList<Playground> getPlaygrounds() {
        return this.playgrounds;
    }

    /**
     * @return getter for playground owner wallet
     */
    public Ewallet getOwnerWallet() {
        return this.ownerWallet;
    }

    /**
     * method responsible for viewing all the playgrounds that were not yet approved
     */
    public void viewPending() {
        for (int i = 0; i < playgrounds.size(); i++) {
            System.out.println("===========================");
            if (!playgrounds.get(i).getAvailability()) {
                System.out.println(playgrounds.get(i).getPlaygroundName());
            }
            System.out.println("===========================");
        }
    }

    /**
     * adding a playground method
     * the playground owner is required to enter the information of the playground such as the following:
     * the playground name
     * playground address
     * playground gps location (logitude and latitude)
     * price per hour
     * url
     * description
     * cancellation period
     * availableAtTime
     * if the playground owner enters any invalid attributes, the method displays an error message explaining which attribute was entered incorrectly
     * @param allPlaygrounds it takes all the playgrounds that are in the database to compare whether the new playground exists or not
     * @param unapprovedPlaygrounds adds this new playground to the unApproved playgrounds until admin approval
     *
     */
    public void addPlayground(ArrayList<Playground> allPlaygrounds, ArrayList<Playground> unapprovedPlaygrounds) {
        ArrayList<Playground> playgroundsCheck = allPlaygrounds;
        playgroundsCheck.addAll(unapprovedPlaygrounds);
        Scanner input = new Scanner(System.in);
        int variablesSet = 0;
        boolean flag = true;
        Playground newPlayground = new Playground(getVerifier());
        System.out.println("===========================");
        System.out.println("Please enter playground information");
        System.out.println("===========================");
        System.out.println("Playground Name: ");
        boolean uniqueName = true;
        newPlayground.setPlaygroundName(input.nextLine());
        for (int i = 0; i < playgroundsCheck.size(); i++) {
            if (playgroundsCheck.get(i).getPlaygroundName().equals(newPlayground.getPlaygroundName())) {
                uniqueName = false;
                break;
            }
        }
        if (uniqueName) {
            variablesSet++;
        } else {
            System.out.println("Playground with same name already exists");
        }

        if (variablesSet == 1) {
            System.out.println("Playground Address: ");
            boolean uniqueAddress = true;
            newPlayground.setAddress(input.nextLine());
            for (int i = 0; i < playgroundsCheck.size(); i++) {
                if (allPlaygrounds.get(i).getAddress().equalsIgnoreCase(newPlayground.getAddress())) {
                    uniqueAddress = false;
                    break;
                }
            }
            if (uniqueAddress) {
                variablesSet++;
            } else {
                System.out.println("Playground with same address already exists");
            }
        }

        if (variablesSet == 2) {
            System.out.println("Playground GPS Location: ");
            try {
                System.out.println("Latitude: ");
                newPlayground.getGps().add(Double.parseDouble(input.nextLine()));
                System.out.println("Longitude: ");
                newPlayground.getGps().add(Double.parseDouble(input.nextLine()));
                boolean uniqueGPS = true;
                for (int i = 0; i < playgroundsCheck.size(); i++) {
                    double originalLatitude = playgroundsCheck.get(i).getGps().get(0);
                    double originalLongitude = playgroundsCheck.get(i).getGps().get(1);
                    double dupedLatitude = newPlayground.getGps().get(0);
                    double dupedLongitude = newPlayground.getGps().get(1);
                    if (originalLatitude == dupedLatitude && originalLongitude == dupedLongitude) {
                        uniqueGPS = false;
                        break;
                    }
                }
                if (uniqueGPS) {
                    variablesSet++;
                    variablesSet++;
                } else {
                    System.out.println("Playground with same GPS coordinates already exists");
                }
            } catch (NumberFormatException ex) {
                System.out.println("Invalid GPS location");
            }
        }

        if (variablesSet == 4) {
            System.out.println("Playground Price per Hour: ");
            try {
                newPlayground.setPrice(Double.parseDouble(input.nextLine()));
                variablesSet++;
            } catch (NumberFormatException ex) {
                System.out.println("Invalid price per hour");
            }
        }
        if (variablesSet == 5) {
            System.out.println("Playground URL: ");
            newPlayground.setUrl(input.nextLine());
            boolean uniqueURL = true;
            for (int i = 0; i < playgroundsCheck.size(); i++) {
                if (playgroundsCheck.get(i).getUrl().equals(newPlayground.getUrl())) {
                    uniqueURL = false;
                    break;
                }
            }
            if (uniqueURL) {
                variablesSet++;
            } else {
                System.out.println("Playground with same URL already exists");
            }
        }

        if (variablesSet == 6) {
            System.out.println("Playground Description: ");
            newPlayground.setDescription(input.nextLine());
            variablesSet++;
            System.out.println("Playground Cancellation Period (hours): ");
            try {
                newPlayground.setCancelationPeriod(Integer.parseInt(input.nextLine()));
                variablesSet++;
            } catch (NumberFormatException ex) {
                System.out.println("Invalid cancellation period");
            }
        }

        if (variablesSet == 8) {
            System.out.println("Playground Available at Time: ");
            newPlayground.setAvailableAtTime(new Time());
            boolean validTime = newPlayground.getAvailableAtTime().setTime();
            Time currentTime = new Time();
            currentTime.setCurrentTime();
            currentTime.setStartingHour(newPlayground.getAvailableAtTime().getStartingHour());
            currentTime.setEndingHour(newPlayground.getAvailableAtTime().getEndingHour());
            if (validTime && !newPlayground.getAvailableAtTime().startsBefore(currentTime)) {
                newPlayground.setPlaygroundStatus("Suspended");
                newPlayground.setOwner(this);
                playgrounds.add(newPlayground);
                unapprovedPlaygrounds.add(newPlayground);
                System.out.println("Playground added successfully, pending admin's approval");
            } else {
                System.out.println("Invalid time");
            }
        }

    }

    /**
     * updatePLayground responsible for updating a specific playground information
     * a menu is first displayed for the playgroundOwner to choose from, which is one of the follwing:
     * 1.updating availability
     * 2. changin description
     * 3. updating price
     * 4. update booking number
     * 5. updating url
     * 6. deleting the playground
     * 7. cancel
     * the playground owner then chooses one of the previous choices, based on which choice was chosen, the method asks the playgroundOwner to
     * alter the new attribute
     * then checks whether it is valid and correct or not
     * if not, an error message is displayed based on what went wrong
     * if correct, the playground is successfully updated
     * @param allPlaygrounds takes all the playgrounds to use it in comparing the updated playground and ensure zero duplication
     * @param unapprovedPlaygrounds to add the updated playground
     * @param playground playground that needs to be updated
     */
    public void updatePlayground(ArrayList<Playground> allPlaygrounds, ArrayList<Playground> unapprovedPlaygrounds, Playground playground) {
        ArrayList<Playground> playgroundsCheck = allPlaygrounds;
        playgroundsCheck.addAll(unapprovedPlaygrounds);
        int userChoice = 0;
        Scanner input = new Scanner(System.in);
        System.out.println("===========================");
        System.out.println("What would you like to do:");
        System.out.println("[1] Update availability");
        System.out.println("[2] Change description");
        System.out.println("[3] Update price");
        System.out.println("[4] Update Booking Number");
        System.out.println("[5] Update URL");
        System.out.println("[6] Delete Playground");
        System.out.println("[7] Cancel");
        userChoice = getVerifier().getUserChoice(1, 7);
        if (userChoice == 1) {
            System.out.println("===========================");
            System.out.println("What would you like to do:");
            System.out.println("[1] Change playground status");
            System.out.println("[2] Change available hours");
            System.out.println("[3] Cancel");
            userChoice = getVerifier().getUserChoice(1, 3);
            if (userChoice == 1) {
                String currentStatus = playground.getPlaygroundStatus();
                String newStatus = (currentStatus == "Activated") ? "Suspended" : "Activated";
                System.out.println("Current Playground Status: " + currentStatus);
                if (playground.getAvailability()) {
                    playground.setPlaygroundStatus(newStatus);
                } else {
                    System.out.println("Playground hasn't been approved by administrator yet, can't change status");
                }
                System.out.println("===========================");
                System.out.println("New Playground Status: " + playground.getPlaygroundStatus());
            } else if (userChoice == 2) {
                Time newTime = new Time();
                System.out.println("Current Playground Available Hours: ");
                System.out.println(playground.getAvailableAtTime());
                System.out.println("Please Enter New Playground Available Hours: ");
                boolean validTime = newTime.setTime();
                Time currentTime = new Time();
                currentTime.setCurrentTime();
                currentTime.setStartingHour(newTime.getStartingHour());
                currentTime.setEndingHour(newTime.getEndingHour());
                if (validTime && !newTime.startsBefore(currentTime)) playground.setAvailableAtTime(newTime);
                else System.out.println("Invalid time");
                System.out.println("===========================");
                System.out.println("New Playground Available Hours: ");
                System.out.println(playground.getAvailableAtTime());
            }
        } else if (userChoice == 2) {
            System.out.println("Current playground description: ");
            System.out.println(playground.getDescription());
            System.out.println("Please enter new description: ");
            playground.setDescription(input.nextLine());
            System.out.println("===========================");
            System.out.println("New playground description: ");
            System.out.println(playground.getDescription());
        } else if (userChoice == 3) {
            System.out.println("Current playground price per hour: ");
            System.out.println(playground.getPrice());
            System.out.println("Please enter new price per hour: ");
            try {
                playground.setPrice(Double.parseDouble(input.nextLine()));
                System.out.println("===========================");
                System.out.println("New playground price per hour: ");
                System.out.println(playground.getPrice());
            } catch (NumberFormatException ex) {
                System.out.println("Invalid price per hour");
            }
        } else if (userChoice == 4) {
            System.out.println("Current playground booking number: ");
            System.out.println(playground.getBookingNumber());
            System.out.println("Please enter new booking number: ");
            try {
                playground.setBookingNumber(Integer.parseInt(input.nextLine()));
                System.out.println("===========================");
                System.out.println("New playground booking number: ");
                System.out.println(playground.getBookingNumber());
            } catch (NumberFormatException ex) {
                System.out.println("Invalid booking number");
            }
        } else if (userChoice == 5) {
            System.out.println("Current playground URL: ");
            System.out.println(playground.getUrl());
            System.out.println("Please enter new URL: ");
            String newURL = input.nextLine();
            boolean uniqueURL = true;
            for (int i = 0; i < playgroundsCheck.size(); i++) {
                if (playgroundsCheck.get(i).getUrl().equals(newURL)) {
                    uniqueURL = false;
                    break;
                }
            }
            if (uniqueURL) {
                playground.setUrl(newURL);
            } else {
                System.out.println("A playground with this URL already exists");
            }
            System.out.println("===========================");
            System.out.println("New playground URL: ");
            System.out.println(playground.getUrl());
        } else if (userChoice == 6) {
            System.out.println("Are you sure you want to delete this playground?");
            System.out.println("[1] Yes");
            System.out.println("[2] No");
            userChoice = getVerifier().getUserChoice(1, 2);
            if (userChoice == 1) {
                deletePlayground(allPlaygrounds, unapprovedPlaygrounds, playground);
                System.out.println("Playground deleted");
            }
        }
    }

    /**
     * delets a specific playground
     * @param allPlaygrounds removes the playground from the arraylist of all the playgrounds in the database
     * @param unapprovedPlaygrounds removes the playground from the unApproved playground arraylist
     * @param playground the playground to be removed and deleted from the GoFo database
     */
    public void deletePlayground(ArrayList<Playground> allPlaygrounds, ArrayList<Playground> unapprovedPlaygrounds, Playground playground) {
        if (playground.getAvailability()) allPlaygrounds.remove(playground);
        else unapprovedPlaygrounds.remove(playground);

        playground.getOwner().getPlaygrounds().remove(playground);

        Time currentTime = new Time();
        currentTime.setCurrentTime();
        currentTime.setEndingHour(24);
        currentTime.setEndYear(playground.getAvailableAtTime().getEndYear());
        currentTime.setEndMonth(playground.getAvailableAtTime().getEndMonth());
        currentTime.setEndDay(playground.getAvailableAtTime().getEndDay());

        for (int i = 0; i < playground.getBookings().size(); i++) {
            playground.getBookings().get(i).refund();
            playground.getBookings().get(i).getPlayer().getPlayerBookings().remove(playground.getBookings().get(i));
        }
    }

    /**
     * displays Ewallet balance
     */
    public void checkEwallet() {
        System.out.println("===========================");
        System.out.println(getUserName() + "'s e-wallet balance: " + ownerWallet.getBalance() + " EGP");
        System.out.println("===========================");
    }

    /**
     * @param playground views all bookings of that playground
     */
    public void viewBookings(Playground playground) {
        System.out.println("===========================");
        System.out.println(playground.getPlaygroundName() + ':');
        for (int i = 0; i < playground.getBookings().size(); i++) {
            System.out.println('[' + String.valueOf(i + 1) + "] " + playground.getBookings().get(i));
        }
        System.out.println("===========================");
    }

    /**
     * @return a string containing all the playground info such as the username, address, email and phone number
     */
    @Override
    public String toString() {
        return "Playground owner user name:" + getUserName() + '\n' +
                "Playground owner address:" + address + '\n' +
                "Playground owner email:" + getEmail() + '\n' +
                "Playground owner phone number:" + getPhoneNumber();
    }
}
