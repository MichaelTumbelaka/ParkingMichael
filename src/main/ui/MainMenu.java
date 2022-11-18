package ui;

import model.ParkingSpace;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainMenu extends MainPage {
    private JButton reserve;
    private JButton view;
    private JButton save;

    //EFFECTS : creates a main page that the user can view, reserve, and deposit
    public MainMenu(List<ParkingSpace> parkingspaces) {
        super(parkingspaces);
        loadTitle();
        loadButtons();
        loadImage();
    }

    //EFFECTS : loads the title to the screen
    @Override
    protected void loadTitle() {
        JLabel title = new JLabel("Main Menu");
        Font font1 = new Font("Sans Serif", Font.BOLD, 75);
        title.setFont(font1);
        title.setBounds(40, 40, 750, 60);
        add(title);
    }

    //MODIFIES: this
    //EFFECTS : loads the button to the screen
    protected void loadButtons() {
        reserve = new JButton("Reserve");
        reserve.setBounds(70, 190, 250, 150);
        reserve.addActionListener(new ButtonHandler());
        add(reserve);
        view = new JButton("View");
        view.setBounds(370, 190, 250, 150);
        view.addActionListener(new ButtonHandler());
        add(view);
        save = new JButton("Save");
        save.setBounds(670, 190, 250, 150);
        save.addActionListener(new ButtonHandler());
        add(save);
    }

    //MODIFIES : this
    //EFFECTS : loads images into screen
    private void loadImage() {
        ImageIcon img = new ImageIcon(".data/quikpark.png");
        JLabel image = new JLabel();
        image.setIcon(img);
        image.setBounds(400,0,600,200);
        add(image);
        repaint();
    }

    //Class used to handle button press in Deposit Page
    private class ButtonHandler implements ActionListener {
        //EFFECTS : handle every button action in this page
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == view) {
                setVisible(false);
                new ViewPage(parkingspaces);
            } else if (e.getSource() == reserve) {
                setVisible(false);
                new ReservePage(parkingspaces);
            } else if (e.getSource() == save) {
                saveAccounts();
            }
        }
    }
}
