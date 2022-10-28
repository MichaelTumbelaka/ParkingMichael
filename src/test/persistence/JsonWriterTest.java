package persistence;

import model.ParkingSpace;
import model.ParkingSpot;
import model.Reservation;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonWriterTest {
    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyParking() {
        try {
            List<ParkingSpace> parkingspaces = init();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyParking.json");
            writer.open();
            writer.write(parkingspaces);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyParking.json");
            parkingspaces = reader.read();
            assertEquals(4, parkingspaces.size());
            assertEquals("Marine Drive", parkingspaces.get(0).getLabel());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralParking() {
        try {
            List<ParkingSpace> parkingspaces = init();
            addReservations(parkingspaces);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralParking.json");
            writer.open();
            writer.write(parkingspaces);

            JsonReader reader = new JsonReader("./data/testWriterGeneralParking.json");
            parkingspaces = reader.read();
            assertEquals(4, parkingspaces.size());
            int count = 0;
            for (ParkingSpace parkingSpace : parkingspaces) {
                for (ParkingSpot parkingSpot : parkingSpace.getParkingspots()) {
                    String code = "";
                    for (Reservation reservation : parkingSpot.getReservations()) {
                        if (reservation != null) {
                            if (!code.equals(reservation.getParkingSpot().getCode())) {
                                count++;
                                assertEquals(1, reservation.getTime());
                                assertEquals(1, reservation.getDuration());
                            }
                        }
                    }
                }
            }
            assertEquals(3, count);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }


    private void addReservations(List<ParkingSpace> parkingspaces) {
        ParkingSpot parkingSpotOne = parkingspaces.get(0).getParkingspots().get(0);
        Reservation reservationOne = new Reservation(parkingSpotOne, 1, 1);
        parkingSpotOne.setReservation(reservationOne);
        ParkingSpot parkingSpotTwo = parkingspaces.get(1).getParkingspots().get(1);
        Reservation reservationTwo = new Reservation(parkingSpotOne, 1, 1);
        parkingSpotTwo.setReservation(reservationTwo);
        ParkingSpot parkingSpotThree = parkingspaces.get(2).getParkingspots().get(2);
        Reservation reservationThree = new Reservation(parkingSpotThree, 1, 1);
        parkingSpotThree.setReservation(reservationThree);
    }

    private List<ParkingSpace> init() {
        List<ParkingSpace> parkingspaces = new ArrayList<>();
        ParkingSpace parkingspace1 = new ParkingSpace("Marine Drive", 4);
        ParkingSpace parkingspace2 = new ParkingSpace("UBC", 5);
        ParkingSpace parkingspace3 = new ParkingSpace("SFU", 5);
        ParkingSpace parkingspace4 = new ParkingSpace("Joyce", 4);
        parkingspaces.add(parkingspace1);
        parkingspaces.add(parkingspace2);
        parkingspaces.add(parkingspace3);
        parkingspaces.add(parkingspace4);
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
        return parkingspaces;
    }
}

