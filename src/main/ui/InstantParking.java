package ui;

import model.ParkingSpace;
import model.ParkingSpot;
import model.Reservation;
import org.json.JSONException;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


// Bank teller application
public class InstantParking {
    public static final String JSON_STORE = "./data/parking.json";
    private Scanner scanner;
    private List<ParkingSpace> parkingSpaces;
    private JsonWriter writer;
    private JsonReader reader;

    // EFFECTS: runs the parking application
    public InstantParking() {
        initialParking();
    }

    private void initialParking() {
        writer = new JsonWriter(JSON_STORE);
        reader = new JsonReader(JSON_STORE);
        try {
            parkingSpaces = reader.read();
            System.out.println("Loaded successfully from " + JSON_STORE);
            runParking(true);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
            runParking(false);
        } catch (JSONException e) {
            System.out.println("File is not valid");
            runParking(false);
        }

    }

    // MODIFIES: this
    // EFFECTS: processes user input
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void runParking(boolean loaded) {
        scanner = new Scanner(System.in);
        boolean keepGoing = true;
        String command;

        if (!loaded) {
            init();
        }

        while (keepGoing) {
            displayMenu();
            command = scanner.nextLine();
            command = command.toLowerCase();

            if (command.equals("exit")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        try {
            writer.open();
            writer.write(parkingSpaces);
            writer.close();
            System.out.println("Saved parking spaces to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        switch (command) {
            case "reserve":
                reserve();
                break;
            case "view reservation":
                viewReservation();
                break;
            case "cancel reservation":
                cancelReservation();
                break;
            default:
                System.out.println("Selection not valid...");
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes accounts
    private void init() {
        parkingSpaces = new ArrayList<>();
        scanner = new Scanner(System.in);
        ParkingSpace parkingspace1 = new ParkingSpace("Marine Drive", 3);
        ParkingSpace parkingspace2 = new ParkingSpace("UBC", 5);
        ParkingSpace parkingspace3 = new ParkingSpace("SFU", 5);
        ParkingSpace parkingspace4 = new ParkingSpace("Joyce", 4);
        parkingSpaces.add(parkingspace1);
        parkingSpaces.add(parkingspace3);
        parkingSpaces.add(parkingspace4);
        parkingSpaces.add(parkingspace2);
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

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tReserve -> Make New Reservation");
        System.out.println("\tView Reservation -> View My Reservations");
        System.out.println("\tCancel Reservation -> Cancel My Reservation");
        System.out.println("\texit -> Exit");
    }

    // MODIFIES: this
    // EFFECTS: makes a new reservation
    private void reserve() {
        scanner = new Scanner(System.in);
        System.out.println("Choose Parking Space:");
        for (ParkingSpace parkingSpace : parkingSpaces) {
            System.out.println(parkingSpace.getLabel() + ": " + parkingSpace.getPrice());
        }
        String parkingSpaceLabel = scanner.nextLine();
        for (ParkingSpace parkingSpace : parkingSpaces) {
            if (parkingSpace.getLabel().equals(parkingSpaceLabel)) {
                System.out.println("Choose Parking Spot:");
                System.out.println(parkingSpace.displayParkingSpots());
                String parkingSpotLabel = scanner.nextLine();
                int time = scanner.nextInt();
                scanner.nextLine();
                int duration = scanner.nextInt();
                scanner.nextLine();
                for (ParkingSpot parkingSpot : parkingSpace.getParkingSpots()) {
                    if (parkingSpotLabel.equals(parkingSpot.getId())) {
                        Reservation reservation = new Reservation(parkingSpot, time, duration);
                        parkingSpot.setReservation(reservation);
                    }
                }
            }
        }

    }


    // MODIFIES: this
    // EFFECTS: view the reservations that the user reserved
    private void viewReservation() {
        for (ParkingSpace parkingSpace : parkingSpaces) {
            for (ParkingSpot parkingSpot : parkingSpace.getParkingSpots()) {
                String code = "";
                for (Reservation reservation : parkingSpot.getReservations()) {
                    if (reservation != null) {
                        if (!code.equals(reservation.getParkingSpot().getId())) {
                            System.out.println(parkingSpace.getLabel());
                            System.out.println(reservation);
                            code = reservation.getParkingSpot().getId();
                        }
                    }
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: cancels the reservation the user chooses
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void cancelReservation() {
        scanner = new Scanner(System.in);
        List<Reservation> reservationList = new ArrayList<>();
        for (ParkingSpace parkingSpace : parkingSpaces) {
            for (ParkingSpot parkingSpot : parkingSpace.getParkingSpots()) {
                for (Reservation reservation : parkingSpot.getReservations()) {
                    if (!reservationList.contains(reservation)) {
                        reservationList.add(reservation);
                    }
                }
            }
            reservationList.removeAll(Collections.singleton(null));
        }

        for (int i = 0; i < reservationList.size(); i++) {
            if (reservationList.get(i) != null) {
                System.out.println(reservationList.get(i).getParkingSpot().getParkingSpace().getLabel());
                System.out.println(i + ": " + reservationList.get(i));
            }
        }
        System.out.println("Which reservation do you want to cancel? 0-" + (reservationList.size() - 1));
        int choice = scanner.nextInt();
        scanner.nextLine();
        reservationList.get(choice).cancelReservation();
    }
}

