package System;

import User.*;

import java.util.ArrayList;

public class Playground {

    private String playgroundName;
    private String address;
    private ArrayList<Double> gps;
    private boolean availability;
    private int bookingNumber;
    private Double price;
    private String url;
    private ArrayList<Booking> bookings;
    private String playgroundStatus;
    private String description;
    private PlaygroundOwner owner;
    private int cancellationPeriod;
    private Time availableAtTime;

    public Playground(Verifier verifier) {
        playgroundName = "";
        address = "";
        gps = new ArrayList<>();
        availability = false;
        bookingNumber = 0;
        price = 0.0;
        url = "";
        bookings = new ArrayList<>();
        playgroundStatus = "";
        description = "";
        owner = new PlaygroundOwner(verifier);
        cancellationPeriod = 0;
        availableAtTime = new Time();
    }

    public Time getAvailableAtTime() {
        return availableAtTime;
    }

    public void setAvailableAtTime(Time availableAtTime) {
        this.availableAtTime = availableAtTime;
    }

    public String getPlaygroundName() {
        return this.playgroundName;
    }

    /**
     * @param playgroundName
     */
    public void setPlaygroundName(String playgroundName) {
        this.playgroundName = playgroundName;
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

    public ArrayList<Double> getGps() {
        return this.gps;
    }

    public boolean getAvailability() {
        return this.availability;
    }

    /**
     * @param availability
     */
    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public int getBookingNumber() {
        return this.bookingNumber;
    }

    /**
     * @param bookingNumber
     */
    public void setBookingNumber(int bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    public Double getPrice() {
        return this.price;
    }

    /**
     * @param price
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    public String getUrl() {
        return this.url;
    }

    /**
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    public ArrayList<Booking> getBookings() {
        return this.bookings;
    }

    public String getPlaygroundStatus() {
        return this.playgroundStatus;
    }

    /**
     * @param playgroundStatus
     */
    public void setPlaygroundStatus(String playgroundStatus) {
        this.playgroundStatus = playgroundStatus;
    }

    public String getDescription() {
        return this.description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public PlaygroundOwner getOwner() {
        return this.owner;
    }

    /**
     * @param owner
     */
    public void setOwner(PlaygroundOwner owner) {
        this.owner = owner;
    }

    public int getCancelationPeriod() {
        return this.cancellationPeriod;
    }

    /**
     * @param cancelationPeriod
     */
    public void setCancelationPeriod(int cancelationPeriod) {
        this.cancellationPeriod = cancelationPeriod;
    }

    @Override
    public String toString() {
        String availabilityString = (availability) ? "Approved" : "Unapproved";
        return "Playground Name: " + playgroundName + '\n' +
                "Address: " + address + '\n' +
                "GPS: [" + gps.get(0) + ',' + gps.get(1) + "]\n" +
                "Availability: " + availabilityString + '\n' +
                "Booking Number: " + bookingNumber + '\n' +
                "Price: " + price + '\n' +
                "URL: " + url + '\n' +
                "Playground Status: " + playgroundStatus + '\n' +
                "Description: " + description + '\n' +
                "Owner: " + owner + '\n' +
                "Cancellation Period: " + cancellationPeriod + '\n' +
                "Available At Time: " + availableAtTime;
    }
}