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
     * @param playground the playground to be approved
     */
    public void approvePlayground(Playground playground) {
        playground.setAvailability(true);
        playground.setPlaygroundStatus("Activated");
    }

    /**
     * suspending a specific playground by setting the playground statues to suspended
     * @param playground the playground to be suspended
     */
    public void suspendPlayground(Playground playground) {
        playground.setPlaygroundStatus("Suspended");
    }

    /**
     * deleting a specific playground
     * @param playground the playground to be deleted
     * @param allPlaygrounds arraylist of all playgrounds
     * @param unapprovedPlaygrounds arraylist of all unapproved playgrounds
     */
    public void deletePlayground(ArrayList<Playground> allPlaygrounds, ArrayList<Playground> unapprovedPlaygrounds, Playground playground) {
        playground.getOwner().deletePlayground(allPlaygrounds, unapprovedPlaygrounds, playground);
    }

    /**
     * activating a specific playground by setting the playground statues to activated
     * @param playground the playground to be activated
     */
    public void activatePlayground(Playground playground) {
        playground.setPlaygroundStatus("Activated");
    }

}
