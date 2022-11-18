package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Saveable;

import java.util.ArrayList;

public class Account implements Saveable {
    private String name;
    private String email;
    private AccountReservationManager reservationManager;
    private int balance;

    //EFFECTS : creates an account with name, email, and 0 balance, with an empty reservations
    public Account(String name, String email) {
        this.name = name;
        this.email = email;
        this.balance = 0;
        reservationManager = new AccountReservationManager();
    }

    //REQUIRES : amount > 0
    //MODIFIES : this
    //EFFECTS : adds the amount to the balance
    public void deposit(int amount) {
        this.balance += amount;
    }

    //REQUIRES: amount <= balance
    //MODIFIES: this
    //EFFECTS: decreas the balance with the amount
    public void withdraw(int amount) {
        this.balance -= amount;
    }

    //MODIFIES: this
    //EFFECTS: add a reservation to the reservation list
    public void addReservation(Reservation reservation) {
        this.reservationManager.addReservation(reservation);
    }

    //MODIFIES: this
    //EFFECTS: removes the reservation from reservation list
    public void removeReservation(Reservation reservation) {
        this.reservationManager.removeReservation(reservation);
    }

    //EFFECTS: returns a clean, readable string representation of this account
    @Override
    public String toString() {
        String output = "";
        output += "account: " + this.name + "\n";
        output += "balance: " + this.balance + "\n";
        output += "Reservations:\n";
        for (Reservation res: getReservations()) {
            output += getReservations().indexOf(res) + 1 + ")\n";
            output += res.toString();
        }
        return output;
    }

    public int getBalance() {
        return balance;
    }

    public ArrayList<Reservation> getReservations() {
        return this.reservationManager.getReservations();
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    //code implemented from https://www.journaldev.com/12668/json-simple-example#json-simple-example-to-write-json-to-file
    //EFFECTS: returns a JSONObject representation of this account.
    @Override
   //unchecked
    public JSONObject toJsonObject() {
        JSONObject acc = new JSONObject();
        acc.put("name", this.getName());
        acc.put("email", this.getEmail());
        acc.put("balance", this.getBalance());

        JSONArray reservations = new JSONArray();
        for (Reservation reservation: this.getReservations()) {
            JSONObject res = new JSONObject();
            res.put("parkingspot", reservation.getParkingSpot().getCode());
            res.put("time", reservation.getTime());
            res.put("duration", reservation.getDuration());
            reservations.put(res);
        }
        acc.put("reservations", reservations);

        return acc;
    }




}
