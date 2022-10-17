package model;

public class Reservation {
    private ParkingSpot parkingSpot;
    private int time;
    private int duration; //hours
    private int price;

    // REQUIRES: 0 <= time <= 23, and duration >0 and duration + time >= 24
    // EFFECTS: makes a reservation with a parkingspot, username, email, time of reservation, and duration
    public Reservation(ParkingSpot parkingSpot, int time, int duration) {
        this.parkingSpot = parkingSpot;
        this.time = time;
        this.duration = duration;
        this.price = parkingSpot.getPrice() * this.duration;
    }

    @Override
    //EFFECTS: returns reservation stats
    public String toString() {
        String output = "";
        output += "Parking Spot: " + this.parkingSpot.getCode() + "\n";
        output += "Time: " + getTime() + "\n";
        output += "Duration: " + getDuration() + "\n";
        output += "\n";
        return output;
    }


    //MODIFIES: this
    //EFFECTS: cancel the reservation from the parking spot.
    public void  cancelReservation() {
        for (int t = time; t < time + duration; t++) {
            this.parkingSpot.getReservations().set(t, null);
        }
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
}


