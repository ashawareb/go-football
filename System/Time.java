package System;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Time {

    private int startingHour;
    private int endingHour;
    private int startDay;
    private int startMonth;
    private int startYear;
    private int endDay;
    private int endMonth;
    private int endYear;

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

    public int getStartingHour() {
        return startingHour;
    }

    public void setStartingHour(int startingHour) {
        this.startingHour = startingHour;
    }

    public int getEndingHour() {
        return endingHour;
    }

    public void setEndingHour(int endingHour) {
        this.endingHour = endingHour;
    }

    public int getStartDay() {
        return this.startDay;
    }

    /**
     * @param startDay
     */
    public void setStartDay(int startDay) {
        this.startDay = startDay;
    }

    public int getStartMonth() {
        return this.startMonth;
    }

    /**
     * @param startMonth
     */
    public void setStartMonth(int startMonth) {
        this.startMonth = startMonth;
    }

    public int getStartYear() {
        return this.startYear;
    }

    /**
     * @param startYear
     */
    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public int getEndDay() {
        return endDay;
    }

    /**
     * @param endDay
     */
    public void setEndDay(int endDay) {
        this.endDay = endDay;
    }

    public int getEndMonth() {
        return endMonth;
    }

    /**
     * @param endMonth
     */
    public void setEndMonth(int endMonth) {
        this.endMonth = endMonth;
    }

    public int getEndYear() {
        return endYear;
    }

    /**
     * @param endYear
     */
    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

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
            else if (getEndMonth() < getStartMonth()) flag = false;
            else if (getEndDay() < getStartDay()) flag = false;

            if (getStartingHour() < 0 || getStartingHour() > 24) flag = false;
            else if (getEndingHour() < 0 || getEndingHour() > 24) flag = false;
            else if (getStartDay() < 0 || getStartDay() > 31) flag = false;
            else if (getStartMonth() < 0 || getStartMonth() > 12) flag = false;
            else if (getStartYear() < 0) flag = false;
            else if (getEndDay() < 0 || getEndDay() > 31) flag = false;
            else if (getEndMonth() < 0 || getEndMonth() > 12) flag = false;
            else if (getEndYear() < 0) flag = false;

        } catch (NumberFormatException ex) {
            System.out.println("Invalid time input");
        }
        return flag;
    }

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

    public int getDuration() {
        int years = getEndYear() - getStartYear();
        int months = getEndMonth() - getStartMonth();
        int days = getEndDay() - getStartDay();
        int hours = getEndingHour() - getStartingHour();
        return (hours * ((years * 12 * 30) + (months * 30) + days));
    }

    @Override
    public String toString() {
        return "Starting Hour: " + startingHour + '\n' +
                "Ending Hour: " + endingHour + '\n' +
                "Starting Date: " + startDay + '\\' + startMonth + '\\' + startYear + '\n' +
                "Ending Date: " + endDay + '\\' + endMonth + '\\' + endYear + '\n';
    }
}