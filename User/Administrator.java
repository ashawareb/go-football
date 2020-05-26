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
        playground.getOwner().getPlaygrounds().add(playground);
        playground.setPlaygroundStatus("activated");
    }

    /**
     * @param playground
     */
    public void suspendPlayground(Playground playground) {
        playground.setPlaygroundStatus("suspended");
    }

    /**
     * @param playground
     */
    public void deletePlayground(ArrayList<Playground> allPlaygrounds, Playground playground) {
        allPlaygrounds.remove(playground);
        playground.getOwner().getPlaygrounds().remove(playground);

        Time currentTime = new Time();
        currentTime.setCurrentTime();
        currentTime.setEndingHour(24);
        currentTime.setEndYear(playground.getAvailableAtTime().getEndYear());
        currentTime.setEndMonth(playground.getAvailableAtTime().getEndMonth());
        currentTime.setEndDay(playground.getAvailableAtTime().getEndDay());

        for (int i = 0; i < playground.getBookings().size(); i++) {
            if (currentTime.conflicts(playground.getBookings().get(i).getTime())) {
                int bookingDuration = playground.getBookings().get(i).getTime().getDuration();
                double price = playground.getPrice() * bookingDuration;
                double currentBalance = playground.getBookings().get(i).getPlayer().getPlayerWallet().getBalance();
                double ownerBalance = playground.getBookings().get(i).getPlayground().getOwner().getOwnerWallet().getBalance();
                playground.getBookings().get(i).getPlayground().getOwner().getOwnerWallet().setBalance(ownerBalance - (price * bookingDuration));
                playground.getBookings().get(i).getPlayer().getPlayerBookings().remove(playground.getBookings().get(i));
                playground.getBookings().get(i).getPlayer().getPlayerWallet().setBalance(currentBalance + (price * bookingDuration));
            }
        }
    }

    /**
     * @param playground
     */
    public void activatePlayground(Playground playground) {
        playground.setPlaygroundStatus("activated");
    }

}
