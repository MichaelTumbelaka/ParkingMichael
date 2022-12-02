package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Saveable;

import java.util.ArrayList;

public class ParkingSpace implements Saveable {
    private String label;
    private int price;
    private ArrayList<ParkingSpot> parkingSpots;

    //MODIFIES: this
    //EFFECTS: makes an empty list of a parking spots with a price and a label description for each of it
    public ParkingSpace(String label, int price) {
        parkingSpots = new ArrayList<>();
        this.label = label;
        this.price = price;
    }

    //MODIFIES: this
    //EFFECTS: add a Parking Spot to this
    public void addParkingSpot(ParkingSpot parkingSpot) {
        if (!this.parkingSpots.contains(parkingSpot)) {
            parkingSpots.add(parkingSpot);
            parkingSpot.setParkingSpace(this);
        }
    }

    //EFFECTS: make a list of the parking spots
    public String displayParkingSpots() {
        StringBuilder display = new StringBuilder();
        for (ParkingSpot parking: parkingSpots) {
            display.append(parking.displayStats());
            display.append("\n");
        }
        return display.toString();
    }

    public String getLabel() {
        return label;
    }

    public ArrayList<ParkingSpot> getParkingSpots() {
        return parkingSpots;
    }

    public int getPrice() {
        return price;
    }


    @Override
    public JSONObject toJsonObject() {
        JSONObject json = new JSONObject();
        json.put("label", label);
        json.put("price", price);
        json.put("parkingSpots", parkingSpotsToJson());
        return json;
    }

    private JSONArray parkingSpotsToJson() {
        JSONArray array = new JSONArray();
        for (ParkingSpot ps : parkingSpots) {
            array.put(ps.toJsonObject());
        }
        return array;
    }

}
