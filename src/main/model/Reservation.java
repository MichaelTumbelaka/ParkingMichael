package model;

import org.json.JSONObject;
import persistence.Saveable;

public class Reservation implements Saveable {
    private ParkingSpot parkingSpot;
    private int time;
    private int duration; //hours
    private int price;

    // REQUIRES: time between 0 and 23, and duration over 0 and duration + time >= 24
    // EFFECTS: makes a reservation with a parking spot, time of reservation, and duration
    public Reservation(ParkingSpot parkingSpot, int time, int duration) {
        this.parkingSpot = parkingSpot;
        this.time = time;
        this.duration = duration;
        this.price = parkingSpot.getPrice() * this.duration;
    }

    @Override
    //EFFECTS: returns reservation stats
    public String toString() {
        String display = "";
        display += "Parking Spot: " + this.parkingSpot.getId() + "\n";
        display += "Time: " + getTime() + "\n";
        display += "Duration: " + getDuration() + "\n";
        display += "\n";
        return display;
    }


    //MODIFIES: this
    //EFFECTS: cancel the reservation from the parking spot.
    public void  cancelReservation() {
        for (int time = this.time; time < this.time + duration; time++) {
            this.parkingSpot.getReservations().set(time, null);
        }
        Event e = new Event("The Reservation is canceled");
        EventLog.getInstance().logEvent(e);
    }

    public int getTime() {
        return time;
    }

    public int getDuration() {
        return duration;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject json = new JSONObject();
        json.put("time", time);
        json.put("duration", duration);
        return json;
    }
}


