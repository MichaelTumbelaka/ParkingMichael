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
    //private ArrayList<ParkingSpace> parkingspaces;
    private List<ParkingSpace> parkingspaces;
    private JsonReader jsonReader;

    public InstantParkingGUI() {
        init();

        jsonReader = new JsonReader(InstantParking.JSON_STORE);
        try {
            parkingspaces = jsonReader.read();
            System.out.println("Loaded successfully from " + InstantParking.JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + InstantParking.JSON_STORE);
            init();
        } catch (JSONException e) {
            System.out.println("File is not valid");
            init();
        }
        new MainMenu(parkingspaces);

    }

    private void init() {
        parkingspaces = new ArrayList<>();
        ParkingSpace parkingspace1 = new ParkingSpace("Marine Drive", 3);
        ParkingSpace parkingspace2 = new ParkingSpace("UBC", 5);
        ParkingSpace parkingspace3 = new ParkingSpace("SFU", 5);
        ParkingSpace parkingspace4 = new ParkingSpace("Joyce", 4);
        parkingspaces.add(parkingspace1);
        parkingspaces.add(parkingspace3);
        parkingspaces.add(parkingspace4);
        parkingspaces.add(parkingspace2);
        for (int i = 0; i < 3; i++) {
            ParkingSpot parkingSpot1 = new ParkingSpot("X" + (i + 1));
            parkingspace1.addParkingSpot(parkingSpot1);
            ParkingSpot parkingSpot2 = new ParkingSpot("X" + (i + 1));
            parkingspace2.addParkingSpot(parkingSpot2);
            ParkingSpot parkingSpot3 = new ParkingSpot("X" + (i + 1));
            parkingspace3.addParkingSpot(parkingSpot3);
            ParkingSpot parkingSpot4 = new ParkingSpot("X" + (i + 1));
            parkingspace4.addParkingSpot(parkingSpot4);
        }
    }


}
