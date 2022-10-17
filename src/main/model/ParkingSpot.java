package model;

import java.util.ArrayList;

public class ParkingSpot {

    private String code;
    private int price;
    private ParkingSpace parkingSpace;

    private ArrayList<Reservation> reservations;

    //EFFECTS: make a new parking spot with a code identifier and empty reservation
    public ParkingSpot(String code) {
        this.code = code;
        reservations = new ArrayList<>();
        for (int t = 0; t < 24; t++) {
            reservations.add(null);
        }
    }

    //MODIFIES: this
    //EFFECTS: sets the parkingLot and the price to this
    public void setParkingSpace(ParkingSpace parkingSpace) {
        this.parkingSpace = parkingSpace;
        this.price = this.parkingSpace.getPrice();
        parkingSpace.addParkingSpot(this);
    }

    public int getPrice() {
        return price;
    }

    //MODIFIES: this
    //EFFECTS: if reserved, return false, if not then return true
    public boolean setReservation(Reservation reservation) {
        int t;
        for (t = reservation.getTime(); t < reservation.getTime() + reservation.getDuration(); t++) {
            if (reserved(t)) {
                return false;
            } else {
                reservations.set(t,reservation);
                reservations.set(t, reservation);
            }
        }
        return true;
    }

    //EFFECTS: return true if it is already reserved for the span of time
    public boolean reserved(int t) {
        if (reservations.get(t) != null) {
            return true;
        }
        return false;
    }

    //EFFECTS: prints out the code and availability
    public String printStats() {
        String output = "";
        output += this.code + ", ";
        output += "availability: " + this.whenAvailable();

        return output;
    }

    //EFFECTS: returns the time available for reservation
    //         in the form : "[0 - 1], [9 - 17], [8]"
    public String whenAvailable() {
        boolean flag = false;
        int min = 0;
        int max;
        String output = "";
        for (int i = 0; i < 24; i++) {
            if (!flag && reservations.get(i) == null) {
                flag = true;
                min = i;
            }
            if (flag && (i == 23 || reservations.get(i + 1) != null)) {
                flag = false;
                max = i;
                if (min == max) {
                    output += "( " + max + " ), ";
                } else {
                    output += "( " + min + " - " + max + " ), ";
                }
            }
        }
        return output.substring(0, output.length() - 2);
    }

    //EFfECTS: return true if that time and duration is available
    public boolean availability(Reservation reservation) {
        for (int i = reservation.getTime(); i < reservation.getTime() + reservation.getDuration(); i++) {
            if (reserved(i)) {
                return false;
            }
        }
        return true;
    }

    public String getCode() {
        return code;
    }

    public ArrayList<Reservation> getReservations() {
        return reservations;
    }
}

