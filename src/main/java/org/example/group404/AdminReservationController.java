package org.example.group404;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.util.List;

public class AdminReservationController {

    @FXML
    private TextField txtSearchName, txtDeleteID;
    @FXML
    private DatePicker txtSearchDate;
    @FXML
    private TableView<Reservation> tbDelete, tbAll, tbSearch;
    @FXML
    private TableColumn<Reservation, String> colAllName, colAllDate, colAllTime, colSearchName, colSearchDate, colSearchTime, colDeleteName, colDeleteDate, colDeleteTime;
    @FXML
    private TableColumn<Reservation, Integer> colAllID, colAllSeats, colSearchID, colSearchSeats, colDeleteID, colDeleteSeats;

    public void initialize() {
        colAllID.setCellValueFactory(new PropertyValueFactory<>("reservation_id"));
        colAllName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAllDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colAllTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colAllSeats.setCellValueFactory(new PropertyValueFactory<>("no_of_people"));

        colSearchID.setCellValueFactory(new PropertyValueFactory<>("reservation_id"));
        colSearchName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSearchDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colSearchTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colSearchSeats.setCellValueFactory(new PropertyValueFactory<>("no_of_people"));

        colDeleteID.setCellValueFactory(new PropertyValueFactory<>("reservation_id"));
        colDeleteName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDeleteDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDeleteTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colDeleteSeats.setCellValueFactory(new PropertyValueFactory<>("no_of_people"));
    }

    @FXML
    private void handleSearchReservation(ActionEvent event) {
        String keyword = txtDeleteID.getText();
        if (keyword.isEmpty()) {
            showAlert(AlertType.ERROR, "Search Reservations", "Enter a valid reservation ID.");
            return;
        }
        Reservation searchID = new Reservation();
        ObservableList<Reservation> data = FXCollections.observableArrayList();
        tbDelete.getItems().clear();

        if (searchID.searchID(keyword)) {
            data.add(searchID);
            tbDelete.setItems(data);
        } else {
            showAlert(AlertType.ERROR, "Search Reservation", "No reservations found.");
        }
    }
    @FXML
    private void handleSearchByCustomerName(ActionEvent event) {
        String keyword = txtSearchName.getText();

        if (keyword.isEmpty()) {
            showAlert(AlertType.ERROR, "Search Reservations", "Sorry! Have to enter valid customer name");
        } else {
            Reservation searchCus = new Reservation();
            ObservableList<Reservation> data = FXCollections.observableArrayList();
            tbSearch.getItems().clear();

            if (searchCus.searchName(keyword)) {
                data.add(searchCus);
                tbSearch.setItems(data);
            } else {
                showAlert(AlertType.ERROR, "Search Reservation", "Sorry! We do not have any reservations");
            }
        }
    }

    @FXML
    private void handleViewAllReservations(ActionEvent event) {
        Admin reservationsAll = new Admin();
        List<Reservation> ReservationList = reservationsAll.viewAllReservation();
        tbAll.setItems(FXCollections.observableArrayList(ReservationList));
    }

    @FXML
    private void handleSearchByDate(ActionEvent event) {
        if (txtSearchDate.getValue() == null) {
            showAlert(AlertType.ERROR, "Search Reservations", "Enter a valid date.");
            return;
        }
        String keyword = txtSearchDate.getValue().toString();
        Reservation searchDate = new Reservation();
        ObservableList<Reservation> data = FXCollections.observableArrayList();
        tbSearch.getItems().clear();

        if (searchDate.searchDate(keyword)) {
            data.add(searchDate);
            tbSearch.setItems(data);
        } else {
            showAlert(AlertType.ERROR, "Search Reservation", "No reservations found.");
        }
    }

    @FXML
    private void handleDeleteReservation(ActionEvent event) {
        String reservationId = txtDeleteID.getText();
        if (reservationId.isEmpty()) {
            showAlert(AlertType.ERROR, "Delete Reservation", "Enter a reservation ID.");
            return;
        }
        Reservation resDelete = new Reservation();
        if (resDelete.deleteReservation(reservationId)) {
            showAlert(AlertType.INFORMATION, "Delete Reservation", "Reservation deleted successfully.");
            tbDelete.getItems().clear();
        } else {
            showAlert(AlertType.ERROR, "Delete Reservation", "No matching reservation found.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
