package model;

import java.util.ArrayList;

public class AccountReservationManager {

    private ArrayList<Reservation> reservations;

    public AccountReservationManager() {
        this.reservations = new ArrayList<>();
    }

    //MODIFIES : this
    //EFFECTS : adds the reservation to the reservation list
    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
    }

    //MODIFIES : this
    //EFFECTS : removes the reservation from list of reservations
    public void removeReservation(Reservation reservation) {
        if (reservations != null) {
            this.reservations.remove(reservation);
            reservation.cancelReservation();
        }
    }

    public ArrayList<Reservation> getReservations() {
        return this.reservations;
    }
}
