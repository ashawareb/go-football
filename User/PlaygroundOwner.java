package User;

import System.Playground;
import System.Ewallet;
import System.Application;
import System.Time;
import System.Verifier;

import java.util.ArrayList;
import java.util.Scanner;

public class PlaygroundOwner extends Account {

    private String address;
    private ArrayList<Playground> playgrounds;
    private Ewallet ownerWallet;

    public PlaygroundOwner(Verifier verifier) {
        super(verifier);
        address = "";
        playgrounds = new ArrayList<>();
        ownerWallet = new Ewallet();
    }

    public String getAddress() {
        return this.address;
    }

    /**
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<Playground> getPlaygrounds() {
        return this.playgrounds;
    }

    public Ewallet getOwnerWallet() {
        return this.ownerWallet;
    }

    public void viewPending() {
        for (int i = 0; i < playgrounds.size(); i++) {
            System.out.println("===========================");
            if (!playgrounds.get(i).getAvailability()) {
                System.out.println(playgrounds.get(i));
            }
            System.out.println("===========================");
        }
    }

    /**
     * @param unapprovedPlaygrounds
     */
    public void addPlayground(ArrayList<Playground> unapprovedPlaygrounds) {
        Scanner input = new Scanner(System.in);
        boolean flag = true;
        Playground newPlayground = new Playground(getVerifier());
        System.out.println("===========================");
        System.out.println("Please enter playground information");
        System.out.println("===========================");
        System.out.println("Playground Name: ");
        newPlayground.setPlaygroundName(input.nextLine());
        System.out.println("Playground Address: ");
        newPlayground.setAddress(input.nextLine());
        System.out.println("Playground GPS Location: ");
        try {
            System.out.println("Latitude: ");
            newPlayground.getGps().add(input.nextDouble());
            System.out.println("Longitude: ");
            newPlayground.getGps().add(input.nextDouble());
        } catch (NumberFormatException ex) {
            System.out.println("Invalid GPS location");
        }
        System.out.println("Playground Price per Hour: ");
        try {
            newPlayground.setPrice(Double.parseDouble(input.nextLine()));
        } catch (NumberFormatException ex) {
            System.out.println("Invalid price per hour");
        }
        System.out.println("Playground URL: ");
        newPlayground.setUrl(input.nextLine());
        System.out.println("Playground Description: ");
        newPlayground.setDescription(input.nextLine());
        System.out.println("Playground Cancellation Period (hours): ");
        try {
            newPlayground.setCancelationPeriod(Integer.parseInt(input.nextLine()));
        } catch (NumberFormatException ex) {
            System.out.println("Invalid cancellation period");
        }
        System.out.println("Playground Available at Time: ");
        newPlayground.setAvailableAtTime(new Time());
        boolean validTime = newPlayground.getAvailableAtTime().setTime();
        if (validTime) {
            playgrounds.add(newPlayground);
            unapprovedPlaygrounds.add(newPlayground);
            System.out.println("Playground added successfully, pending admin's approval");
        } else {
            System.out.println("Invalid time");
        }
    }

    public void updatePlayground(Playground playground) {
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
        userChoice = getVerifier().getUserChoice(1, 5);
        if (userChoice == 1) {
            System.out.println("===========================");
            System.out.println("What would you like to do:");
            System.out.println("[1] Change playground status");
            System.out.println("[2] Change available hours");
            System.out.println("[3] Cancel");
            userChoice = getVerifier().getUserChoice(1, 3);
            if (userChoice == 1) {
                String currentStatus = playground.getPlaygroundStatus();
                String newStatus = (currentStatus == "Active") ? "Suspended" : "Active";
                System.out.println("Current Playground Status: " + currentStatus);
                playground.setPlaygroundStatus(newStatus);
                System.out.println("===========================");
                System.out.println("New Playground Status: " + newStatus);
            } else if (userChoice == 2) {
                Time newTime = new Time();
                System.out.println("Current Playground Available Hours: ");
                System.out.println(playground.getAvailableAtTime());
                System.out.println("Please Enter New Playground Available Hours: ");
                boolean validTime = newTime.setTime();
                if (validTime) playground.setAvailableAtTime(newTime);
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
            playground.setUrl(input.nextLine());
            System.out.println("===========================");
            System.out.println("New playground URL: ");
            System.out.println(playground.getUrl());
        } else if (userChoice == 6) {
            System.out.println("Are you sure you want to delete this playground?");
            System.out.println("[1] Yes");
            System.out.println("[2] No");
            userChoice = getVerifier().getUserChoice(1, 2);
            if (userChoice == 1) {
                getPlaygrounds().remove(playground);
                System.out.println("Playground deleted");
            }
        }
    }

    public void checkEwallet() {
        System.out.println("===========================");
        System.out.println(getUserName() + "'s e-wallet balance: " + ownerWallet.getBalance() + " EGP");
        System.out.println("===========================");
    }

    public void viewBookings(Playground playground) {
        System.out.println("===========================");
        System.out.println(playground.getPlaygroundName() + ':');
        for (int i = 0; i < playground.getBookings().size(); i++) {
            System.out.println(playground.getBookings().get(i));
        }
        System.out.println("===========================");
    }

    @Override
    public String toString() {
        return "Playground owner user name:" + getUserName() + '\n' +
                "Playground owner address:" + address + '\n' +
                "Playground owner email:" + getEmail() + '\n' +
                "Playground owner phone number:" + getPhoneNumber();
    }
}