package ui;

import model.ParkingSpace;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ReservePage extends MainPage {

    private JList<String> list;
    private JButton choose;
    private JButton mainMenu;

    //EFFECTS : creates a page for the user to pick parking lot for the reservation
    public ReservePage(List<ParkingSpace> parkingSpaces) {
        super(parkingSpaces);
        loadTitle();
        loadList();
        loadButton();
    }

    //EFFECTS : loads the title to the screen
    protected void loadTitle() {
        JLabel title = new JLabel("Choose a Destination!");
        Font font1 = new Font("Sans Serif", Font.BOLD, 50);
        title.setFont(font1);
        title.setBounds(0, 30, 600, 60);
        add(title);
    }

    //MODIFIES : this
    //EFFECTS : loads the list to the screen
    private void loadList() {
        DefaultListModel<String> list1 = new DefaultListModel<>();
        for (ParkingSpace ps : parkingSpaces) {
            list1.addElement(ps.getLabel() + ", " + ps.getPrice() + "$/hour");
        }
        list = new JList<>(list1);
        list.setBounds(100, 130, 400, 400);
        list.setFixedCellHeight(100);
        list.setFont(new Font("Sans Serif", Font.BOLD, 32));
        add(list);
    }

    //MODIFIES : this
    //EFFECTS : loads the buttons to the screen
    private void loadButton() {
        choose = new JButton("Choose");
        choose.setBounds(520, 170, 200, 70);
        choose.addActionListener(new ButtonHandler());
        add(choose);
        mainMenu = new JButton("Back");
        mainMenu.setBounds(100, 550, 200, 70);
        mainMenu.addActionListener(new ButtonHandler());
        add(mainMenu);

    }

    //EFFECTS : finds a ParkingSpace from given name
    private ParkingSpace findParkingSpace(String name) {
        for (ParkingSpace ps : parkingSpaces) {
            if (ps.getLabel().equals(name)) {
                return ps;
            }
        }
        return null;
    }

    //Class used to handle button press in Deposit Page
    private class ButtonHandler implements ActionListener {
        //EFFECTS : handle every button action in this page
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == mainMenu) {
                setVisible(false);
                new MainMenu(parkingSpaces);
            } else if (e.getSource() == choose) {
                if (list.getSelectedIndex() != -1) {
                    String selectedName = list.getSelectedValue().split(",")[0];
                    ParkingSpace ps = findParkingSpace(selectedName);
                    setVisible(false);
                    new PickParkingSpotPage(parkingSpaces, ps);
                }
            }
        }
    }
}
