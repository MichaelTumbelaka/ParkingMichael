package ui;

import model.ParkingSpace;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public abstract class MainPage extends JFrame {
    protected List<ParkingSpace> parkingspaces;
    protected JsonWriter jsonWriter;

    public MainPage(List<ParkingSpace> parkingspaces) {
        this.parkingspaces = parkingspaces;
        this.jsonWriter = new JsonWriter("account");

        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(InstantParkingGUI.WIDTH, InstantParkingGUI.HEIGHT);
        setLocationRelativeTo(null); // puts the project to the center of the screen
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                saveAccounts();
            }
        });
    }

    // EFFECTS : saves all the account in to the app including the most recent one with all the new changes if there is
    protected void saveAccounts() {
        try {
            jsonWriter.open();
            jsonWriter.write(parkingspaces);
            jsonWriter.close();
            System.out.println("Saved the parking spaces to " + InstantParking.JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file : " + InstantParking.JSON_STORE);
        }
    }

    //EFFECTS : loads the title to the screen
    protected abstract void loadTitle();
}
