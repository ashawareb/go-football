package System;

import User.Player;

public class Booking {

    private Playground playground;
    private Player player;
    private Time time;

    public Booking(Verifier verifier) {
        playground = new Playground(verifier);
        player = new Player(verifier);
        time = new Time();
    }

    public Playground getPlayground() {
        return this.playground;
    }

    /**
     * @param playground
     */
    public void setPlayground(Playground playground) {
        this.playground = playground;
    }

    public Player getPlayer() {
        return this.player;
    }

    /**
     * @param player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    public Time getTime() {
        return this.time;
    }

    /**
     * @param time
     */
    public void setTime(Time time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Booking duration:\n" + time + '\n' +
                "Playground was booked by player: " + player.getUserName();
    }
}