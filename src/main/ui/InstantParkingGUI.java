package ui;

import model.ParkingSpace;
import model.ParkingSpot;
import org.json.JSONException;
import persistence.JsonReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InstantParkingGUI {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 750;
    private List<ParkingSpace> parkingSpaces;
    private JsonReader jsonReader;

    public InstantParkingGUI() {
        init();

        jsonReader = new JsonReader(InstantParking.JSON_STORE);
        try {
            parkingSpaces = jsonReader.read();
            System.out.println("Loaded successfully from " + InstantParking.JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + InstantParking.JSON_STORE);
            init();
        } catch (JSONException e) {
            System.out.println("File is not valid");
            init();
        }
        new MainMenu(parkingSpaces);

    }

    private void init() {
        parkingSpaces = new ArrayList<>();
        ParkingSpace parkingSpace1 = new ParkingSpace("Marine Drive", 3);
        ParkingSpace parkingSpace2 = new ParkingSpace("UBC", 5);
        ParkingSpace parkingSpace3 = new ParkingSpace("SFU", 5);
        ParkingSpace parkingSpace4 = new ParkingSpace("Joyce", 4);
        parkingSpaces.add(parkingSpace1);
        parkingSpaces.add(parkingSpace3);
        parkingSpaces.add(parkingSpace4);
        parkingSpaces.add(parkingSpace2);
        for (int i = 0; i < 3; i++) {
            ParkingSpot parkingSpot1 = new ParkingSpot("X" + (i + 1));
            parkingSpace1.addParkingSpot(parkingSpot1);
            ParkingSpot parkingSpot2 = new ParkingSpot("X" + (i + 1));
            parkingSpace2.addParkingSpot(parkingSpot2);
            ParkingSpot parkingSpot3 = new ParkingSpot("X" + (i + 1));
            parkingSpace3.addParkingSpot(parkingSpot3);
            ParkingSpot parkingSpot4 = new ParkingSpot("X" + (i + 1));
            parkingSpace4.addParkingSpot(parkingSpot4);
        }
    }
}
