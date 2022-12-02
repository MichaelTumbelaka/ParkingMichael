package persistence;

import model.ParkingSpace;
import model.ParkingSpot;
import model.Reservation;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonReaderTest {


    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyParking() {

        JsonReader reader = new JsonReader("./data/testReaderEmptyParking.json");
        try {
            List<ParkingSpace> parkingspaces = reader.read();
            assertEquals(4, parkingspaces.size());
            assertEquals("Marine Drive", parkingspaces.get(0).getLabel());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testWriterGeneralParking() {
        try {
            JsonReader reader = new JsonReader("./data/testReaderGeneralParking.json");
            List<ParkingSpace> parkingspaces = reader.read();
            assertEquals(3, parkingspaces.size());
            int count = 0;
            for (ParkingSpace parkingSpace : parkingspaces) {
                for (ParkingSpot parkingSpot : parkingSpace.getParkingSpots()) {
                    String code = "";
                    for (Reservation reservation : parkingSpot.getReservations()) {
                        if (reservation != null) {
                            if (!code.equals(reservation.getParkingSpot().getId())) {
                                count++;
                                assertEquals(5, reservation.getTime());
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
}


