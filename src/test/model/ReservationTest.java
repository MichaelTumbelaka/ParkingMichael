package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationTest {
    public Reservation test;
    public ParkingSpot testParkingSpot;

    @BeforeEach
    public void setUp() {
        ParkingSpace testParkingSpace = new ParkingSpace("test", 5);
        testParkingSpot = new ParkingSpot("A");
        testParkingSpot.setParkingSpace(testParkingSpace);
        test = new Reservation(testParkingSpot,1, 5);
        testParkingSpot.setReservation(test);
    }

    @Test
    public void testGetPrice() {
        assertEquals(25, test.getPrice());
    }

    @Test
    public void testConstructor() {
        assertTrue(test.getParkingSpot().equals(testParkingSpot));
        assertEquals(1, test.getTime());
        assertEquals(5, test.getDuration());
    }

    @Test
    public void testPrint(){
        assertEquals("Parking Spot: A\nTime: 1\nDuration: 5\n\n", test.toString());
    }

    @Test
    public void testCancelReservation() {
        test.cancelReservation();
        for (Reservation res: testParkingSpot.getReservations()) {
            assertNull(res);
        }
    }
}
