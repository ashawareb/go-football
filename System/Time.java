package System;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Time class holds the time for further usage
 */
public class Time {

    /**
     * the starting hour, stored in 24 format as integer
     */
    private int startingHour;
    /**
     * the ending hour, stored in 24 format as integer
     */
    private int endingHour;
    /**
     * the starting day, stored as integer
     */
    private int startDay;
    /**
     * the starting month, stored as integer
     */
    private int startMonth;
    /**
     * the starting year
     */
    private int startYear;
    /**
     * the ending day, stored as integer
     */
    private int endDay;
    /**
     * the ending month, stored as integer
     */
    private int endMonth;
    /**
     * the ending year
     */
    private int endYear;

    /**
     * the Time constructor, initialises all the attributes to be zero valued
     */
    public Time() {
        startingHour = 0;
        endingHour = 0;
        startDay = 0;
        startMonth = 0;
        startYear = 0;
        endDay = 0;
        endMonth = 0;
        endYear = 0;
    }

    /**
     * @return getter for startingHour
     */
    public int getStartingHour() {
        return startingHour;
    }

    /**
     * @param startingHour setter for startingHour
     */
    public void setStartingHour(int startingHour) {
        this.startingHour = startingHour;
    }

    /**
     * @return getter for endingHour
     */
    public int getEndingHour() {
        return endingHour;
    }

    /**
     * @param endingHour setter for endingHour
     */
    public void setEndingHour(int endingHour) {
        this.endingHour = endingHour;
    }

    /**
     * @return getter for startingDay
     */
    public int getStartDay() {
        return this.startDay;
    }

    /**
     * @param startDay setter for starting day
     */
    public void setStartDay(int startDay) {
        this.startDay = startDay;
    }

    /**
     * @return getter for starting month
     */
    public int getStartMonth() {
        return this.startMonth;
    }

    /**
     * @param startMonth setter for starting month
     */
    public void setStartMonth(int startMonth) {
        this.startMonth = startMonth;
    }

    /**
     * @return getter for starting year
     */
    public int getStartYear() {
        return this.startYear;
    }

    /**
     * @param startYear setter for starting year
     */
    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    /**
     * @return getter for ending day
     */
    public int getEndDay() {
        return endDay;
    }

    /**
     * @param endDay setter for ending day
     */
    public void setEndDay(int endDay) {
        this.endDay = endDay;
    }

    /**
     * @return getter for ending month
     */
    public int getEndMonth() {
        return endMonth;
    }

    /**
     * @param endMonth setter for ending month
     */
    public void setEndMonth(int endMonth) {
        this.endMonth = endMonth;
    }

    /**
     * @return getter for ending year
     */
    public int getEndYear() {
        return endYear;
    }

    /**
     * @param endYear setting for ending year
     */
    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    /**
     * setTIme method is responsible for taking the input from the user to store them
     *  it checks if the time is in the past then displays invalid time
     * @return  a boolean that holds whether the time is valid or not
     */
    public boolean setTime() {
        Scanner input = new Scanner(System.in);
        boolean flag = true;
        try {
            System.out.println("Starting Hour: ");
            setStartingHour(Integer.parseInt(input.nextLine()));
            System.out.println("Ending Hour: ");
            setEndingHour(Integer.parseInt(input.nextLine()));

            System.out.println("Starting Day: ");
            setStartDay(Integer.parseInt(input.nextLine()));
            System.out.println("Starting Month: ");
            setStartMonth(Integer.parseInt(input.nextLine()));
            System.out.println("Starting Year: ");
            setStartYear(Integer.parseInt(input.nextLine()));

            System.out.println("Ending Day: ");
            setEndDay(Integer.parseInt(input.nextLine()));
            System.out.println("Ending Month: ");
            setEndMonth(Integer.parseInt(input.nextLine()));
            System.out.println("Ending Year: ");
            setEndYear(Integer.parseInt(input.nextLine()));

            if (getEndingHour() < getStartingHour()) flag = false;

            if (getEndYear() < getStartYear()) flag = false;
            else if (getEndMonth() < getStartMonth() && getEndYear() == getStartYear()) flag = false;
            else if (getEndDay() < getStartDay() && getEndMonth() == getStartMonth()) flag = false;

            if (getStartingHour() < 0 || getStartingHour() > 24) flag = false;
            else if (getEndingHour() < 0 || getEndingHour() > 24) flag = false;
            else if (getStartDay() < 0 || getStartDay() > 30) flag = false;
            else if (getStartMonth() < 0 || getStartMonth() > 12) flag = false;
            else if (getStartYear() < 0) flag = false;
            else if (getEndDay() < 0 || getEndDay() > 30) flag = false;
            else if (getEndMonth() < 0 || getEndMonth() > 12) flag = false;
            else if (getEndYear() < 0) flag = false;

        } catch (NumberFormatException ex) {
            System.out.println("Invalid time input");
            flag = false;
        }
        return flag;
    }

    /**
     * a method responsible for setting the current time and fetching it in terms of hours, days, months and years
     */
    public void setCurrentTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd/HH");
        LocalDateTime now = LocalDateTime.now();
        String currentDateTime = dtf.format(now);

        setStartingHour(Integer.parseInt(currentDateTime.substring(11)));
        setEndingHour(Integer.parseInt(currentDateTime.substring(11)));

        setStartDay(Integer.parseInt(currentDateTime.substring(8, 10)));
        setStartMonth(Integer.parseInt(currentDateTime.substring(5, 7)));
        setStartYear(Integer.parseInt(currentDateTime.substring(0, 4)));

        setEndDay(Integer.parseInt(currentDateTime.substring(8, 10)));
        setEndMonth(Integer.parseInt(currentDateTime.substring(5, 7)));
        setEndYear(Integer.parseInt(currentDateTime.substring(0, 4)));
    }

    /**
     * method for extending time given a specific duration
     * @param duration to be added to the time attributes within the class Time
     */
    public void extend(int duration) {
        double doubleDays = duration / 24.0;
        int days = (int) doubleDays;
        int hours = (int) ((doubleDays - days) * 24);
        setEndingHour(getEndingHour() + hours);
        if (getEndingHour() > 24) {
            setEndingHour(getEndingHour() - 24);
            setEndDay(getEndDay() + 1);
        }
        setEndDay(getEndDay() + days);
        if (getEndDay() > 30) {
            setEndDay(getEndDay() - 30);
            setEndMonth(getEndMonth() + 1);
        }
        if (getEndMonth() > 12) {
            setEndMonth(getEndMonth() - 12);
            setEndYear(getEndYear() + 1);
        }
    }

    /**
     * conflicts method is responsible for findind whether the time is within the same period as in the time in the Time class attributes
     * @param time the time that needs to be checked if within the same time as the class attributes
     * @return boolean expressing whether it exists within the same time or not
     */
    public boolean conflicts(Time time) {
        boolean flag = true;
        if (getEndYear() < time.getStartYear()) {
            flag = false;
        } else if (getEndYear() == time.getStartYear()) {
            if (getEndMonth() < time.getStartMonth()) {
                flag = false;
            } else if (getEndMonth() == time.getStartMonth()) {
                if (getEndDay() < time.getStartDay()) {
                    flag = false;
                }
            }
        } else {
            if (getStartYear() > time.getEndYear()) {
                flag = false;
            } else if (getStartYear() == time.getEndYear()) {
                if (getStartMonth() > time.getEndMonth()) {
                    flag = false;
                } else if (getStartMonth() == time.getEndMonth()) {
                    if (getStartDay() > time.getEndDay()) {
                        flag = false;
                    }
                }
            }
        }
        if (flag) {
            if (getEndingHour() <= time.getStartingHour()) {
                flag = false;
            } else if (getStartingHour() >= time.getEndingHour()) {
                flag = false;
            }
        }
        return flag;
    }

    /**
     * @param time checks if the time exists before the time attributes in the class
     * @return true or false based on whether or not it exists before or after the time attributes in the class
     */
    public boolean startsBefore(Time time) {
        boolean flag = false;
        if (getStartYear() < time.getStartYear()) flag = true;
        else if (getStartYear() == time.getStartYear()) {
            if (getStartMonth() < time.getStartMonth()) flag = true;
            else if (getStartMonth() == time.getStartMonth()) {
                if (getStartDay() < time.getStartDay()) flag = true;
            }
        }
        if (!flag) {
            if (getStartingHour() < time.getStartingHour()) flag = true;
        }
        return flag;
    }

    /**
     * @param time checks if the time exists after the time attributes in the class
     * @return true or false based on whether or not it exists after or before the time attributes in the class
     */
    public boolean endsAfter(Time time) {
        boolean flag = false;
        if (getEndYear() > time.getEndYear()) flag = true;
        else if (getEndYear() == time.getEndYear()) {
            if (getEndMonth() > time.getEndMonth()) flag = true;
            else if (getEndMonth() == time.getEndMonth()) {
                if (getEndDay() > time.getEndDay()) flag = true;
            }
        }
        if (!flag) {
            if (getEndingHour() > time.getEndingHour()) flag = true;
        }
        return flag;
    }

    /**
     * method responsible for calculating the duration of the time by subtracting the ending time from the starting time
     * @return the duration of the whole time
     */
    public int getDuration() {
        int years = (getEndYear() - getStartYear()) * 12 * 30;
        int months = (getEndMonth() - getStartMonth()) * 30;
        int days = getEndDay() - getStartDay();
        int hours = getEndingHour() - getStartingHour();
        return (hours * (years + months + days + 1));
    }

    /**
     * @return a String containing all info of the Time class
     * such as:
     * the starting hour
     * ending hout
     * starting date
     * ending date
     */
    @Override
    public String toString() {
        return "Starting Hour: " + startingHour + '\n' +
                "Ending Hour: " + endingHour + '\n' +
                "Starting Date: " + startDay + '\\' + startMonth + '\\' + startYear + '\n' +
                "Ending Date: " + endDay + '\\' + endMonth + '\\' + endYear + '\n';
    }
}
