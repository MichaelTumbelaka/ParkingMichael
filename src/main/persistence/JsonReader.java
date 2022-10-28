package persistence;

import model.ParkingSpace;
import model.ParkingSpot;
import model.Reservation;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

// Represents a reader that reads parking space from JSON data stored in file
public class JsonReader {
    private String source;
    //private List<ParkingSpace> parkingspaces;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
        //this.parkingspaces = parkingspaces;
    }

    // EFFECTS: reads parking space from file and returns it;
    // throws IOException if an error occurs reading data from file
    public List<ParkingSpace> read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseParkingSpace(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses parking space from JSON object and returns it
    private List<ParkingSpace> parseParkingSpace(JSONObject object) {
        JSONArray jsonArray = (JSONArray) object.get("parkingspaces");
        ArrayList<ParkingSpace> parkingspaces = new ArrayList<>();
        for (Object o : jsonArray) {
            JSONObject obj = (JSONObject) o;
            ParkingSpace parkingSpace = new ParkingSpace(obj.getString("label"), obj.getInt("price"));
            addParkingSpots(parkingSpace, obj.getJSONArray("parkingSpots"));
            parkingspaces.add(parkingSpace);
        }
        return parkingspaces;
    }

    private void addParkingSpots(ParkingSpace parkingSpace, JSONArray parkingSpots) {
        for (Object obj : parkingSpots) {
            addParkingSpot(parkingSpace, (JSONObject) obj);
        }
    }

    private void addParkingSpot(ParkingSpace parkingSpace, JSONObject obj) {
        ParkingSpot parkingSpot = new ParkingSpot(obj.getString("code"));
        parkingSpot.setParkingSpace(parkingSpace);
        for (Object reservation : obj.getJSONArray("reservations")) {
            addReservation(parkingSpot, (JSONObject) reservation);
        }
    }

    private void addReservation(ParkingSpot parkingSpot, JSONObject reservation) {
        int time = reservation.getInt("time");
        int duration = reservation.getInt("duration");
        Reservation reserved = new Reservation(parkingSpot, time, duration);
        parkingSpot.setReservation(reserved);
    }

}