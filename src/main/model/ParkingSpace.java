package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Saveable;

import java.util.ArrayList;

public class ParkingSpace implements Saveable {
    private String label;
    private int price;
    private ArrayList<ParkingSpot> parkingspots;

    //MODIFIES: this
    //EFFECTS: makes an empty list of a parking spots with a price and a label description for each of it
    public ParkingSpace(String label, int price) {
        parkingspots = new ArrayList<>();
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
        StringBuilder output = new StringBuilder();
        for (ParkingSpot parking: parkingspots) {
            output.append(parking.printStats());
            output.append("\n");
        }
        return output.toString();
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
        for (ParkingSpot ps : parkingspots) {
            array.put(ps.toJsonObject());
        }
        return array;
    }

}
