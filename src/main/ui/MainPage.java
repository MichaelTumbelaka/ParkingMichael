package ui;

import model.Event;
import model.EventLog;
import model.ParkingSpace;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.util.List;

public abstract class MainPage extends JFrame {
    protected List<ParkingSpace> parkingSpaces;
    protected JsonWriter jsonWriter;

    public MainPage(List<ParkingSpace> parkingSpaces) {
        this.parkingSpaces = parkingSpaces;
        this.jsonWriter = new JsonWriter("account");

        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(InstantParkingGUI.WIDTH, InstantParkingGUI.HEIGHT);
        setLocationRelativeTo(null); // puts the project to the center of the screen
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveParkingSpaces();
                EventLog el = EventLog.getInstance();
                for (Event event : el) {
                    System.out.println(event);
                }
            }
        });
    }

    // EFFECTS : saves all the account in to the app including the most recent one with all the new changes if there is
    protected void saveParkingSpaces() {
        try {
            jsonWriter.open();
            jsonWriter.write(parkingSpaces);
            jsonWriter.close();
            System.out.println("Saved the parking spaces to " + InstantParking.JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file : " + InstantParking.JSON_STORE);
        }
    }

    //EFFECTS : loads the title to the screen
    protected abstract void loadTitle();
}
