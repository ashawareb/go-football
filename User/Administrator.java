package User;

import System.Playground;
import System.Time;
import System.Verifier;

import java.util.ArrayList;

public class Administrator extends Account {

    public Administrator(Verifier verifier) {
        super(verifier);
        setUserName("admin");
        setPassword("admin");
        setEmail("admin@gmail.com");
        setPhoneNumber("00000000000");
    }

    /**
     * @param playground
     */
    public void approvePlayground(Playground playground) {
        playground.setAvailability(true);
        playground.setPlaygroundStatus("Activated");
    }

    /**
     * @param playground
     */
    public void suspendPlayground(Playground playground) {
        playground.setPlaygroundStatus("Suspended");
    }

    /**
     * @param playground
     */
    public void deletePlayground(ArrayList<Playground> allPlaygrounds, ArrayList<Playground> unapprovedPlaygrounds, Playground playground) {
        playground.getOwner().deletePlayground(allPlaygrounds, unapprovedPlaygrounds, playground);
    }

    /**
     * @param playground
     */
    public void activatePlayground(Playground playground) {
        playground.setPlaygroundStatus("Activated");
    }

}