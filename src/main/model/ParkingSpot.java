package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Saveable;

import java.util.ArrayList;

public class ParkingSpot implements Saveable {

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

    public ParkingSpace getParkingSpace() {
        return parkingSpace;
    }

    //MODIFIES: this
    //EFFECTS: sets the parkingSpace and the price to this
    public void setParkingSpace(ParkingSpace parkingSpace) {
        this.parkingSpace = parkingSpace;
        this.price = this.parkingSpace.getPrice();
        parkingSpace.addParkingSpot(this);
    }

    //MODIFIES: this
    //EFFECTS: if reserved it will return false, if not then it will return true
    public boolean setReservation(Reservation reservation) {
        int t;
        for (t = reservation.getTime(); t < reservation.getTime() + reservation.getDuration(); t++) {
            if (isReserved(t)) {
                return false;
            } else {
                reservations.set(t,reservation);
                reservations.set(t, reservation);
            }
        }
        return true;
    }

    //EFFECTS: return true if at the span of time is already reserved
    public boolean isReserved(int t) {
        return reservations.get(t) != null;
    }

    //EFFECTS: prints the code and availability to choose
    public String printStats() {
        String output = "";
        output += this.code + ", ";
        output += "availability: " + this.whenAvailable();

        return output;
    }

    //EFFECTS: returns the time that is available for reservation
    //         in the form : "(6), (7 - 10)"
    public String whenAvailable() {
        boolean status = false;
        int minimum = 0;
        int maximum;
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < 24; i++) {
            if (!status && reservations.get(i) == null) {
                status = true;
                minimum = i;
            }
            if (status && (i == 23 || reservations.get(i + 1) != null)) {
                status = false;
                maximum = i;
                if (minimum == maximum) {
                    output.append("(").append(maximum).append("), ");
                } else {
                    output.append("(").append(minimum).append(" - ").append(maximum).append("), ");
                }
            }
        }
        return output.substring(0, output.length() - 2);
    }

    //EFFECTS: return true if it is available at that time and for some
    //         amount of duration
    public boolean isAvailable(Reservation reservation) {
        for (int i = reservation.getTime(); i < reservation.getTime() + reservation.getDuration(); i++) {
            if (isReserved(i)) {
                return false;
            }
        }
        return true;
    }

    public String getCode() {
        return code;
    }

    public int getPrice() {
        return price;
    }

    public ArrayList<Reservation> getReservations() {
        return reservations;
    }

    private JSONArray reservationsToJson() {
        JSONArray array = new JSONArray();
        for (Reservation reservation : reservations) {
            if (reservation != null) {
                array.put(reservation.toJsonObject());
            }
        }
        return array;
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("reservations", reservationsToJson());
        return json;
    }
}

