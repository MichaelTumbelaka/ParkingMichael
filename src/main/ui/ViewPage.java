package ui;

import model.ParkingSpace;
import model.ParkingSpot;
import model.Reservation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.List;

public class ViewPage extends MainPage {
    private JTable table;
    private JButton mainmenu;
    private JButton cancel;

    //EFFECTS : creates a page that lets the user view their reservation
    public ViewPage(List<ParkingSpace> parkingspaces) {
        super(parkingspaces);
        loadTitle();
        loadTable();
        loadButton();
        loadLabel();
    }

    //EFFECTS : loads the titles to the screen
    @Override
    protected void loadTitle() {
        JLabel title = new JLabel("Reservations");
        Font font1 = new Font("Sans Serif", Font.BOLD, 75);
        title.setFont(font1);
        title.setBounds(30,10,600,60);
        add(title);
    }

    //EFFECTS : loads the label to the screen
    private void loadLabel() {
        JLabel cancelreservation = new JLabel("Cancel any reservation? (pick the id)");
        cancelreservation.setBounds(550,100,400,50);
        add(cancelreservation);
    }

    //MODIFIES : this
    //EFFECTS : loads the labels to the screen
    private void loadTable() {
        String[] column = {"ID", "CODENAME", "TIME", "DURATION"};
        Object[][] rows = parseReservation();
        table = new JTable(rows, column);
        table.setRowHeight(30);
        JScrollPane panel = new JScrollPane(table);
        panel.setBounds(50,100,400,200);
        add(panel);
    }

    //MODIFIES: this
    //EFFECTS: loads the buttons to the screen
    private void loadButton() {
        mainmenu = new JButton("Back");
        mainmenu.setBounds(50,350,200,50);
        mainmenu.addActionListener(new ButtonHandler());
        add(mainmenu);

        cancel = new JButton("Cancel");
        cancel.setBounds(550,220,100,50);
        cancel.addActionListener(new ButtonHandler());
        add(cancel);
    }

    private void doViewReservation(java.util.List<Reservation> reservations) {
        for (ParkingSpace parkingSpace: parkingspaces) {
            for (ParkingSpot parkingSpot: parkingSpace.getParkingspots()) {
                String code = "";
                for (Reservation reservation: parkingSpot.getReservations()) {
                    if (reservation != null) {
                        if (!code.equals(reservation.getParkingSpot().getCode())) {
                            reservations.add(reservation);
                            code = reservation.getParkingSpot().getCode();
                        }
                    }
                }
            }
        }
    }

    //EFFECTS: parses the reservation into readable format to choose
    private String[][] parseReservation() {
        java.util.List<Reservation> reservations = new ArrayList<>();
        doViewReservation(reservations);
        String[][] data = new String[reservations.size()][5];
        int id = 1;
        for (Reservation current: reservations) {
            String[] curparse = {
                    Integer.toString(id),
                    current.getParkingSpot().getCode(),
                    Integer.toString(current.getTime()),
                    Integer.toString(current.getDuration())
            };
            data[id - 1] = curparse;
            id++;
        }
        return data;
    }

    //A class for handling button press. (login button and create account button)
    private class ButtonHandler implements ActionListener {
        //EFFECTS: handle every button action in this page
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == mainmenu) {
                setVisible(false);
                new MainMenu(parkingspaces);
            } else if (e.getSource() == cancel) {
                if (table.getSelectedRow() != -1) {
                    List<Reservation> reservations = new ArrayList<>();
                    doViewReservation(reservations);
                    int row = table.getSelectedRow();
                    Integer id = Integer.parseInt((String) table.getModel().getValueAt(row, 0));
                    Reservation selectedreservation = reservations.get(id - 1);
                    // cancel
                    selectedreservation.cancelReservation();
                    loadTable();
                }
            }
        }
    }
}
