package System;

import User.Player;
import User.Team;

import java.util.ArrayList;

/**
 * Booking class responsible for holding the booking information
 */
public class Booking {

    /**
     * playground attribute for holding the playground that was booked
     */
    private Playground playground;
    /**
     * player attribute for holding the player that booked the playground
     */
    private Player player;
    /**
     * time attribute for holding how long the playground is booked
     */
    private Time time;
    /**
     * teamMembers that are to be invited to the booked playground
     */
    private ArrayList<Team.TeamMember> teamMembers;

    /**
     * Booking constructor initiates the following:
     * a new playground
     * a new player
     * a new time
     * @param verifier takes verifier to use it to initialize the playground constructor
     */
    public Booking(Verifier verifier) {
        playground = new Playground(verifier);
        player = new Player(verifier);
        time = new Time();
    }

    /**
     * @return booked playground
     */
    public Playground getPlayground() {
        return this.playground;
    }

    /**
     * @param playground sets current booked playground with the value of playground
     */
    public void setPlayground(Playground playground) {
        this.playground = playground;
    }

    /**
     * @return current player that booked the playground
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * @param player sets current player that booked the playground
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * @return how long the playground is booked
     */
    public Time getTime() {
        return this.time;
    }

    /**
     * @param time sets the current time range with the value of time
     */
    public void setTime(Time time) {
        this.time = time;
    }

    /**
     * refunds player with money if the cancellation was within the cancellation period
     */
    public void refund() {
        Time currentTime = new Time();
        currentTime.setCurrentTime();
        if (currentTime.conflicts(getTime())) {
            int bookingDuration = getTime().getDuration();
            double price = playground.getPrice() * bookingDuration;
            double currentBalance = getPlayer().getWallet().getBalance();
            double ownerBalance = getPlayground().getOwner().getWallet().getBalance();
            getPlayground().getOwner().getWallet().setBalance(ownerBalance - (price * bookingDuration));
            getPlayer().getWallet().setBalance(currentBalance + (price * bookingDuration));
        }
    }

    /**
     * @return the information of the booking such as the booking duration and the player
     */
    @Override
    public String toString() {
        return "Booking duration:\n" + time + '\n' +
                "Playground was booked by player: " + player.getUserName();
    }
}
