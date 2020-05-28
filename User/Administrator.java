package User;

import System.Playground;
import System.Time;
import System.Verifier;

import java.util.ArrayList;

/**
 * Administrator class responsible for conducting all the important methods of the administrator
 */
public class Administrator extends Account {

    /**
     * Administrator constructor initialises the username, password, email and phone number of the admin
     *
     * @param verifier to call the account constructor
     */
    public Administrator(Verifier verifier) {
        super(verifier);
        setUserName("admin");
        setPassword("admin");
        setEmail("admin@gmail.com");
        setPhoneNumber("00000000000");
    }

    /**
     * approving a specific playground by setting the playground statues to activated
     *
     * @param playground the playground to be approved
     * @return whether or not the playground was approved
     */
    public boolean approvePlayground(Playground playground) {
        System.out.println("Playground information:\n" + playground);
        System.out.println("Would you like to approve this playground?");
        System.out.println("===========================");
        System.out.println("[1] Yes");
        System.out.println("[2] No");
        System.out.println("===========================");
        System.out.println("Your Choice: ");
        int userChoice = getVerifier().getUserChoice(1, 2);
        if (userChoice == 1) {
            playground.setAvailability(true);
            playground.setPlaygroundStatus("Activated");
            return true;
        } else {
            return false;
        }
    }

    /**
     * suspending a specific playground by setting the playground statues to suspended
     *
     * @param playground the playground to be suspended
     */
    public void suspendPlayground(Playground playground) {
        playground.setPlaygroundStatus("Suspended");
    }

    /**
     * deleting a specific playground
     *
     * @param playground            the playground to be deleted
     * @param allPlaygrounds        arraylist of all playgrounds
     * @param unapprovedPlaygrounds arraylist of all unapproved playgrounds
     */
    public void deletePlayground(ArrayList<Playground> allPlaygrounds, ArrayList<Playground> unapprovedPlaygrounds, Playground playground) {
        playground.getOwner().deletePlayground(allPlaygrounds, unapprovedPlaygrounds, playground);
    }

    /**
     * activating a specific playground by setting the playground statues to activated
     *
     * @param playground the playground to be activated
     */
    public void activatePlayground(Playground playground) {
        playground.setPlaygroundStatus("Activated");
    }

}
