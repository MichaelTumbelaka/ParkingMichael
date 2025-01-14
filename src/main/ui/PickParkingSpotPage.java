package ui;

import model.ParkingSpace;
import model.ParkingSpot;
import model.Reservation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PickParkingSpotPage extends MainPage {
    private static String[] timeAvailability = {
            "0", "1", "2", "3", "4", "5", "6",
            "7", "8", "9", "10", "11", "12",
            "13", "14", "15", "16", "17", "18",
            "19", "20", "21", "22", "23"
    };
    private static String[] durationAvailability = {
            "1", "2", "3", "4", "5", "6"
    };
    private ParkingSpace pickedParkingSpace;
    private JList<String> list;
    private JButton mainMenu;
    private JButton pick;
    private JComboBox time;
    private JComboBox duration;

    //EFFECTS: creates a page for the user to pick a parking spot of their choice
    public PickParkingSpotPage(
            List<ParkingSpace> parkingSpaces,
            ParkingSpace pickedParkingSpace) {
        super(parkingSpaces);
        this.pickedParkingSpace = pickedParkingSpace;

        loadTitle();
        loadList();
        loadButton();
        loadComboBox();
        loadLabel();
    }

    //EFFECTS: loads title to the screen
    protected void loadTitle() {
        JLabel title = new JLabel("Pick a Parking Spot in " + pickedParkingSpace.getLabel());
        Font font = new Font("Sans Serif", Font.BOLD, 30);
        title.setFont(font);
        title.setBounds(30,10,600,60);
        add(title);
    }

    //EFFECTS: loads label to the screen
    private void loadLabel() {
        JLabel time = new JLabel("Time:");
        time.setBounds(520,60,100,50);
        add(time);
        JLabel duration = new JLabel("Duration:");
        duration.setBounds(600,60,100,50);
        add(duration);
    }

    //MODIFIES: this
    //EFFECTS: loads list to the screen
    private void loadList() {
        DefaultListModel<String> l1 = new DefaultListModel<>();
        for (ParkingSpot ps: pickedParkingSpace.getParkingSpots()) {
            l1.addElement(ps.displayStats());
        }
        list = new JList<>(l1);
        list.setBounds(100,100,400,200);
        list.setFixedCellHeight(50);
        list.setFont(new Font("Sans Serif",Font.BOLD,16));
        add(list);
    }

    //MODIFIES: this
    //EFFECTS: loads buttons to the screen
    private void loadButton() {
        mainMenu = new JButton("Back");
        mainMenu.setBounds(100,320, 200, 80);
        mainMenu.addActionListener(new ButtonHandler());
        add(mainMenu);
        pick = new JButton("Pick");
        pick.setBounds(520,200,200,80);
        pick.addActionListener(new ButtonHandler());
        add(pick);
    }

    //MODIFIES: this
    //EFFECTS: loads combo box to the screen
    private void loadComboBox() {
        time = new JComboBox(timeAvailability);
        time.setBounds(520, 100, 50, 50);
        add(time);
        duration = new JComboBox(durationAvailability);
        duration.setBounds(600,100,50,50);
        add(duration);
    }

    //A class used to handle button press in Deposit Page
    private class ButtonHandler implements ActionListener {
        @Override
        //EFFECTS: handle every button action in this page
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == mainMenu) {
                setVisible(false);
                new MainMenu(parkingSpaces);
            } else if (e.getSource() == pick) {
                if (list.getSelectedIndex() != -1) {
                    int selectedTime = Integer.parseInt((String) time.getSelectedItem());
                    int selectedDuration = Integer.parseInt((String) duration.getSelectedItem());
                    ParkingSpot ps = findParkingSpot(list.getSelectedValue());
                    assert (ps != null);
                    Reservation reservation = new Reservation(ps, selectedTime, selectedDuration);
                    if (ps.isAvailable(reservation)) {
                        setVisible(false);
                        ps.setReservation(reservation);
                        new MainMenu(parkingSpaces);
                    } else {
                        JLabel invalid = new JLabel("Time and duration is invalid");
                        invalid.setBounds(450,300,200,100);
                        add(invalid);
                        refresh();
                    }
                }
            }
        }

        //EFFECTS: updates the page regarding any changes made
        private void refresh() {
            setVisible(false);
            setVisible(true);
        }

        //EFFECTS: finds the parking spot associated with string
        private ParkingSpot findParkingSpot(String parsedString) {
            String name = parsedString.split(",")[0];
            for (ParkingSpot ps: pickedParkingSpace.getParkingSpots()) {
                if (name.equals(ps.getId())) {
                    return ps;
                }
            }
            return null;
        }
    }
}