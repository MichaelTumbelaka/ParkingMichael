package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Saveable;

import java.util.ArrayList;

public class ParkingSpot implements Saveable {

    private String id;
    private int price;
    private ParkingSpace parkingSpace;

    private ArrayList<Reservation> reservations;

    //EFFECTS: make a new parking spot with an identifier and empty reservation
    public ParkingSpot(String id) {
        this.id = id;
        reservations = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
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
        int time;
        for (time = reservation.getTime(); time < reservation.getTime() + reservation.getDuration(); time++) {
            if (isReserved(time)) {
                return false;
            } else {
                reservations.set(time,reservation);
                reservations.set(time, reservation);
            }
        }
        Event e = new Event("New Reservation is made to the parking spot");
        EventLog.getInstance().logEvent(e);
        return true;
    }

    //EFFECTS: return true if at the span of time is already reserved
    public boolean isReserved(int time) {
        return reservations.get(time) != null;
    }

    //EFFECTS: prints the code and availability to choose
    public String displayStats() {
        String display = "";
        display += this.id + ", ";
        display += "availability: " + this.displayAvailability();

        return display;
    }

    //EFFECTS: returns the time that is available for reservation
    //         in the form : "(6), (7 - 10)"
    public String displayAvailability() {
        boolean status = false;
        int minimum = 0;
        int maximum;
        StringBuilder output = new StringBuilder();
        for (int time = 0; time < 24; time++) {
            if (!status && reservations.get(time) == null) {
                status = true;
                minimum = time;
            }
            if (status && (time == 23 || reservations.get(time + 1) != null)) {
                status = false;
                maximum = time;
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

    public String getId() {
        return id;
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
        json.put("code", id);
        json.put("reservations", reservationsToJson());
        return json;
    }
}

