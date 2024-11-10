package org.example.group404.ControllerPackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Spinner;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.group404.ClassPackage.Reservation;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomerReservationController {

    @FXML
    private TextField txtNewName, txtEditName, txtEditID, txtDeleteID;

    @FXML
    private DatePicker txtNewDate, txtEditDate;
    @FXML
    private ComboBox<String> txtNewTime, txtEditTime;
    @FXML
    private Spinner<Integer> txtEditSeats, txtNewSeats;

    @FXML
    private TableView<Reservation> tbDelete;

    @FXML
    private TableColumn<Reservation, String> colName, colID, colDate, colTime, colSeats;
    @FXML
    public void initialize() {
        colID.setCellValueFactory(new PropertyValueFactory<>("reservation_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colSeats.setCellValueFactory(new PropertyValueFactory<>("no_of_people"));

        txtNewTime.getItems().addAll("08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00",
                "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00");
        txtNewTime.setPromptText("Select a time");

        txtEditTime.getItems().addAll("08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00",
                "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00");

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, 1);
        txtNewSeats.setValueFactory(valueFactory);

        SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, 1);
        txtEditSeats.setValueFactory(valueFactory2);
    }

    @FXML
    private void btnBackActionPerformed(ActionEvent evt) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/group404/CustomerHome.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Admin Home");
            stage.setScene(new Scene(root));
            stage.show();

            ((Button) evt.getSource()).getScene().getWindow().hide();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Error", "Sorry! Try Again");
        }
    }


    @FXML
    private void btnLogOutActionPerformed(ActionEvent evt) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/group404/Home.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Admin Home");
            stage.setScene(new Scene(root));
            stage.show();

            ((Button) evt.getSource()).getScene().getWindow().hide();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Error", "Sorry! Try Again");
        }
    }

    @FXML
    private void btnNewAction() {
        String name = txtNewName.getText();
        String date = txtNewDate.getValue().toString();
        String time = txtNewTime.getValue();
        short seats;

        try {
            seats = txtNewSeats.getValue().shortValue();
        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Invalid Input", "Invalid number of seats.");
            return;
        }

        if (name.isEmpty() || date.isEmpty() || time.isEmpty()) {
            showAlert(AlertType.ERROR, "New Reservation", "All fields are required.");
            return;
        }

        Reservation resNew = new Reservation();
        resNew.setName(name);
        resNew.setDate(date);
        resNew.setTime(time);
        resNew.setNo_of_people(seats);

        int reservationId = resNew.addNewRes();

        if (reservationId != -1) {
            showAlert(AlertType.INFORMATION, "New Reservation",
                    "Reservation Placed Successfully.\nYour Reservation ID is: " + reservationId);
        } else {
            showAlert(AlertType.ERROR, "New Reservation", "Failed to place reservation. Please try again.");
        }
    }

    @FXML
    private void btnUpdateAction() {
        String name = txtEditName.getText();
        String date = txtEditDate.getValue().toString();
        String time = txtEditTime.getValue();

        if (!isValidDate(date)) {
            showAlert(AlertType.ERROR, "Invalid Input", "Please enter a valid date in YYYY-MM-DD format.");
            return;
        }

        short seatsAvailable;
        try {
            seatsAvailable = txtEditSeats.getValue().shortValue();
        } catch (Exception ex) {
            showAlert(AlertType.ERROR, "Invalid Input", "Please enter a valid number for guests.");
            return;
        }

        int restId;
        try {
            restId = Integer.parseInt(txtEditID.getText());
        } catch (NumberFormatException ex) {
            showAlert(AlertType.ERROR, "Invalid Input", "Please enter a valid Reservation ID.");
            return;
        }

        Reservation resEdit = new Reservation();
        resEdit.setName(name);
        resEdit.setDate(date);
        resEdit.setTime(time);
        resEdit.setNo_of_people(seatsAvailable);

        if (resEdit.updateRes(restId)) {
            showAlert(AlertType.INFORMATION, "Update Reservation", "Reservation updated successfully!");
        } else {
            showAlert(AlertType.ERROR, "Update Reservation", "Failed to update reservation.");
        }
    }

    @FXML
    private void btnEditSearchAction() {
        String keyword = txtEditID.getText();

        if (keyword.isEmpty()) {
            showAlert(AlertType.ERROR, "Search Reservation", "Please enter a valid reservation ID.");
        } else {
            Reservation res = new Reservation();
            if (res.searchID(keyword)) {
                txtEditName.setText(res.getName());
                if (res.getDate() != null && !res.getDate().isEmpty()) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate eventDate = LocalDate.parse(res.getDate(), formatter);
                    txtEditDate.setValue(eventDate);
                }

                txtEditTime.setValue(res.getTime());
                txtEditSeats.getValueFactory().setValue(res.getNo_of_people());
            } else {
                showAlert(AlertType.ERROR, "Search Reservation",
                        "No reservation found with the provided ID.");
            }
        }
    }

    @FXML
    private void btnDeleteSearchAction() {
        String keyword = txtDeleteID.getText();

        if (keyword.isEmpty()) {
            showAlert(AlertType.ERROR, "Search Reservations", "Please enter a valid reservation ID.");
        } else {
            Reservation searchID = new Reservation();
            if (searchID.searchID(keyword)) {
                tbDelete.getItems().clear();
                tbDelete.getItems().add(searchID);
            } else {
                showAlert(AlertType.ERROR, "Search Reservation", "No reservations found with the provided ID.");
            }
        }
    }

    @FXML
    private void btnDeleteAction() {
        String reservationId = txtDeleteID.getText();

        if (reservationId.isEmpty()) {
            showAlert(AlertType.ERROR, "Delete Reservation", "Please enter a reservation ID.");
        } else {
            Reservation resDelete = new Reservation();
            if (resDelete.deleteReservation(reservationId)) {
                showAlert(AlertType.INFORMATION, "Delete Reservation", "Reservation deleted successfully.");
            } else {
                showAlert(AlertType.ERROR, "Delete Reservation", "No matching reservation found.");
            }
        }
    }

    private boolean isValidDate(String date) {
        return date.matches("\\d{4}-\\d{2}-\\d{2}");
    }

    private void showAlert(AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
