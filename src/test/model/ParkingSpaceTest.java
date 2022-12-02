package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParkingSpaceTest {
    public ParkingSpace test;

    @BeforeEach
    public void setUp() {
        test = new ParkingSpace("ParkingSpace", 3);
    }

    @Test
    public void testConstructor() {
        for (ParkingSpot ps : test.getParkingSpots()) {
            assertNull(ps);
        }
        assertEquals(3, test.getPrice());
        assertEquals("ParkingSpace", test.getLabel());
    }

    @Test
    public void testAddParkingSpot() {
        ParkingSpot testparkingspot = new ParkingSpot("test");
        test.addParkingSpot(testparkingspot);
        assertTrue(test.getParkingSpots().contains(testparkingspot));
    }

    @Test
    public void testAddParkingSpotMany() {
        ParkingSpot testparkingspot1 = new ParkingSpot("Spot1");
        ParkingSpot testparkingspot2 = new ParkingSpot("Spot2");
        ParkingSpot testparkingspot3 = new ParkingSpot("Spot3");
        ParkingSpot testparkingspot4 = new ParkingSpot("Spot4");
        test.addParkingSpot(testparkingspot1);
        assertEquals(1, test.getParkingSpots().size());
        test.addParkingSpot(testparkingspot2);
        assertEquals(2, test.getParkingSpots().size());
        test.addParkingSpot(testparkingspot3);
        assertEquals(3, test.getParkingSpots().size());
        test.addParkingSpot(testparkingspot4);
        assertEquals(4, test.getParkingSpots().size());
        assertTrue(test.getParkingSpots().contains(testparkingspot1));
        assertTrue(test.getParkingSpots().contains(testparkingspot2));
        assertTrue(test.getParkingSpots().contains(testparkingspot3));
        assertTrue(test.getParkingSpots().contains(testparkingspot4));
    }

    @Test
    public void testListParkingSpotBase() {
        assertEquals("", test.displayParkingSpots());
    }

    @Test
    public void testListParkingSpotSimple() {
        ParkingSpot testparkingspot = new ParkingSpot("Spot");
        test.addParkingSpot(testparkingspot);
        assertEquals("Spot, availability: ( 0 - 23 )\n", test.displayParkingSpots());
    }

    @Test
    public void testListParkingSpotComplex() {
        ParkingSpot testparkingspot1 = new ParkingSpot("Spot1");
        ParkingSpot testparkingspot2 = new ParkingSpot("Spot2");
        test.addParkingSpot(testparkingspot1);
        test.addParkingSpot(testparkingspot2);
        String expected = "";
        expected += "Spot1, availability: ( 0 - 23 )\n";
        expected += "Spot2, availability: ( 0 - 23 )\n";
        System.out.println(test.displayParkingSpots());
        assertEquals(expected, test.displayParkingSpots());
    }
}

