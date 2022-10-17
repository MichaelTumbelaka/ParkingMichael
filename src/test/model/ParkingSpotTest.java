package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ParkingSpotTest {

    ParkingSpot test;

    @BeforeEach
    public void setUp(){
        test = new ParkingSpot("A");
    }

    @Test
    public void testSetParkingSpace(){
        ParkingSpace parkingSpace = new ParkingSpace("Space1", 5);
        test.setParkingSpace(parkingSpace);
        assertEquals(5, parkingSpace.getPrice());
    }

    @Test
    public void testPrintStats() {
        assertEquals("A, availability: (0 - 23)", test.printStats());
    }

    @Test
    public void testConstructor() {
        assertEquals("A", test.getCode());
    }

    @Test
    public void testSetReservationEmpty(){
        Reservation testreservation = new Reservation(test, 1, 4);
        assertTrue(test.setReservation(testreservation));
        assertTrue(test.isReserved(1));
        assertTrue(test.isReserved(2));
        assertTrue(test.isReserved(3));
        assertTrue(test.isReserved(4));
    }

    @Test
    public void testSetReservationBooked(){
        Reservation testreservation = new Reservation(test,1, 4);
        test.setReservation(testreservation);
        Reservation anotherreservation = new Reservation(test,1, 4);
        assertFalse(test.setReservation(anotherreservation));
    }

    @Test
    public void testSetReservationPartialConflict(){
        Reservation testreservation = new Reservation(test, 1, 4);
        test.setReservation(testreservation);
        Reservation anotherreservation = new Reservation(test, 2, 2);
        assertFalse(test.setReservation(anotherreservation));
    }

    @Test
    public void testAvailabilityReservation() {
        Reservation testreservation = new Reservation(test, 5, 3);
        test.setReservation(testreservation);
        assertFalse(test.isAvailable(testreservation));
        Reservation different = new Reservation(test, 1, 2);
        assertTrue(test.isAvailable(different));
        Reservation union = new Reservation(test,4,3);
        assertFalse(test.isAvailable(union));
    }

    @Test
    public void testReserved() {
        Reservation testreservation = new Reservation(test, 2, 2);
        test.setReservation(testreservation);
        assertFalse(test.isReserved(1));
        assertTrue(test.isReserved(2));
        assertTrue(test.isReserved(3));
        assertFalse(test.isReserved(4));
        assertFalse(test.isReserved(5));
    }

    @Test
    public void testWhenAvailable() {
        assertEquals("(0 - 23)", test.whenAvailable());
    }
    @Test
    public void testWhenAvailableSimple() {
        Reservation testreservation = new Reservation(test, 5, 3);
        test.setReservation(testreservation);
        assertEquals("(0 - 4), (8 - 23)", test.whenAvailable());
    }

    @Test
    public void testWhenAvailableComplex() {
        Reservation testreservation1 = new Reservation(test, 4, 1);
        Reservation testreservation2 = new Reservation(test, 13, 2);
        Reservation testreservation3 = new Reservation(test, 18, 3);
        test.setReservation(testreservation1);
        test.setReservation(testreservation2);
        test.setReservation(testreservation3);
        assertEquals("(0 - 3), (5 - 12), (15 - 17), (21 - 23)", test.whenAvailable());
    }

    @Test
    public void testOneHourAvailableWindow() {
        Reservation testreservation1 = new Reservation(test, 4, 2);
        Reservation testreservation2 = new Reservation(test, 7, 1);
        Reservation testreservation3 = new Reservation(test, 19, 3);
        test.setReservation(testreservation1);
        test.setReservation(testreservation2);
        test.setReservation(testreservation3);
        assertEquals("(0 - 3), (6), (8 - 18), (22 - 23)", test.whenAvailable());
    }

    @Test
    public void testGetReservations() {
        ArrayList<Reservation> reservations = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            reservations.add(null);
        }
        assertEquals(reservations, test.getReservations());
    }

}

