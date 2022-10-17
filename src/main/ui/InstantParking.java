package ui;

import model.ParkingSpace;
import model.ParkingSpot;
import model.Reservation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


// Bank teller application
public class InstantParking {
    private Scanner input;
    private ArrayList<ParkingSpace> parkingspaces;

    // EFFECTS: runs the parking application
    public InstantParking() {
        runParking();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runParking() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.nextLine();
            command = command.toLowerCase();

            if (command.equals("exit")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("reserve")) {
            doReservation();
        } else if (command.equals("view reservation")) {
            doViewReservation();
        } else if (command.equals("cancel reservation")) {
            doCancelReservation();
        } else {
            System.out.println("Not Valid");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes accounts
    private void init() {
        parkingspaces = new ArrayList<>();
        input = new Scanner(System.in);
        ParkingSpace parkingspace1 = new ParkingSpace("Joyce Collingwood", 3);
        ParkingSpace parkingspace2 = new ParkingSpace("SFU", 4);
        ParkingSpace parkingspace3 = new ParkingSpace("UBC", 5);
        ParkingSpace parkingspace4 = new ParkingSpace("Marine Drive", 3);
        parkingspaces.add(parkingspace1);
        parkingspaces.add(parkingspace2);
        parkingspaces.add(parkingspace3);
        parkingspaces.add(parkingspace4);
        for (int n = 0; n < 4; n++) {
            ParkingSpot parkingSpot1 = new ParkingSpot("X" + (n + 1));
            parkingspace1.addParkingSpot(parkingSpot1);
            ParkingSpot parkingSpot2 = new ParkingSpot("X" + (n + 1));
            parkingspace2.addParkingSpot(parkingSpot2);
            ParkingSpot parkingSpot3 = new ParkingSpot("X" + (n + 1));
            parkingspace3.addParkingSpot(parkingSpot3);
            ParkingSpot parkingSpot4 = new ParkingSpot("X" + (n + 1));
            parkingspace4.addParkingSpot(parkingSpot4);
        }
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nChoice :");
        System.out.println("\tReserve : Make Reservation");
        System.out.println("\tView Reservation : View Reservations");
        System.out.println("\tCancel Reservation : Cancel Reservation");
        System.out.println("\tExit : Exit From the App");
    }

    // MODIFIES: this
    // EFFECTS: makes a new reservation
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void doReservation() {
        input = new Scanner(System.in);
        System.out.println("Choose Parking Space:");
        for (ParkingSpace parkingSpace : parkingspaces) {
            System.out.println(parkingSpace.getLabel() + ": " + parkingSpace.getPrice());
        }
        String parkingSpaceLabel = input.nextLine();
        for (ParkingSpace parkingSpace : parkingspaces) {
            if (parkingSpace.getLabel().equals(parkingSpaceLabel)) {
                System.out.println(parkingSpace.getLabel());
                System.out.println("Choose Parking Spot:");
                System.out.println(parkingSpace.listParkingSpots());
                String parkingSpotLabel = input.nextLine();
                int time = input.nextInt();
                input.nextLine();
                int duration = input.nextInt();
                input.nextLine();
                for (ParkingSpot parkingSpot : parkingSpace.getParkingspots()) {
                    if (parkingSpotLabel.equals(parkingSpot.getCode())) {
                        Reservation reservation = new Reservation(parkingSpot, time, duration);
                        parkingSpot.setReservation(reservation);
                    }
                }
            }
        }

    }


    // MODIFIES: this
    // EFFECTS: view the reservations that the user reserved
    private void doViewReservation() {
        for (ParkingSpace parkingSpace : parkingspaces) {
            for (ParkingSpot parkingSpot : parkingSpace.getParkingspots()) {
                String code = "";
                for (Reservation reservation : parkingSpot.getReservations()) {
                    if (reservation != null) {
                        if (!code.equals(reservation.getParkingSpot().getCode())) {
                            System.out.println(parkingSpace.getLabel());
                            System.out.println(reservation);
                            code = reservation.getParkingSpot().getCode();
                        }
                    }
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: cancels the reservation chose by the user
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void doCancelReservation() {
        input = new Scanner(System.in);
        List<Reservation> reservationList = new ArrayList<>();
        for (ParkingSpace parkingSpace : parkingspaces) {
            for (ParkingSpot parkingSpot : parkingSpace.getParkingspots()) {
                List<Reservation> reservations = parkingSpot.getReservations();
                for (int i = 0; i < reservations.size(); i++) {
                    reservationList.add(reservations.get(i));
                    for (Reservation reservation : parkingSpot.getReservations()) {
                        reservationList.add(reservation);
                    }
                }
                reservationList.removeAll(Collections.singleton(null));
                String code = "";
                for (int i = 0; i < reservationList.size(); i++) {
                    if (reservationList.get(i) != null) {
                        if (!code.equals(reservationList.get(i).getParkingSpot().getCode())) {
                            System.out.println(parkingSpace.getLabel());
                            System.out.println(i + ": " + reservationList.get(i));
                            code = reservationList.get(i).getParkingSpot().getCode();
                        }
                    }
                }
            }
            System.out.println("Which reservation do you want to cancel? 0-" + (reservationList.size() - 1));
            int choice = input.nextInt();
            input.nextLine();
            reservationList.get(choice).cancelReservation();
        }
    }
}

