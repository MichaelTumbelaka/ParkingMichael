package model;

import java.util.ArrayList;

public class ParkingSpace {
    private String label;
    private int price;
    private ArrayList<ParkingSpot> parkingspots;

    //MODIFIES: this
    //EFFECTS: makes an empty list of a parking spots with a price and a label description for each of it
    public ParkingSpace(String label, int price) {
        parkingspots = new ArrayList<ParkingSpot>();
        this.label = label;
        this.price = price;
    }

    //MODIFIES: this
    //EFFECTS: add a Parking Spot to this
    public void addParkingSpot(ParkingSpot parkingSpot) {
        if (!this.parkingspots.contains(parkingSpot)) {
            parkingspots.add(parkingSpot);
            parkingSpot.setParkingSpace(this);
        }
    }

    //EFFECTS: make a list of the parking spots
    public String listParkingSpots() {
        String output = "";
        for (ParkingSpot parking: parkingspots) {
            output += parking.printStats();
            output += "\n";
        }
        return output;
    }

    public String getLabel() {
        return label;
    }

    public ArrayList<ParkingSpot> getParkingspots() {
        return parkingspots;
    }

    public int getPrice() {
        return price;
    }

}
