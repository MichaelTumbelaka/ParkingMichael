package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Event class
 */
public class EventTest {
    private Event e;
    private Date d;

    @BeforeEach
    public void runBefore() {
        e = new Event("The Reservation is canceled");
        d = Calendar.getInstance().getTime();
    }

    @Test
    public void testEvent() {
        assertEquals("The Reservation is canceled", e.getDescription());
        assertEquals(d.toString(), e.getDate().toString());
    }

    @Test
    void testEquals() {
        assertEquals(13 * e.getDate().hashCode() + e.getDescription().hashCode(), e.hashCode());
        assertFalse(e.equals(null));
        assertFalse(e.equals(new Date()));
        assertTrue(e.equals(e));
    }

    @Test
    public void testToString() {
        assertEquals(d.toString() + "\n" + "The Reservation is canceled", e.toString());
    }
}
