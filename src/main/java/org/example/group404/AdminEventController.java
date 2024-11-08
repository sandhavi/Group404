package org.example.group404;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminEventController {

    @FXML
    private Button btnHome, btnLogOut, btnEventAll, btnEventNew, btnEventDeleteSearch, btnEventDeleteDelete, btnEventUpdate, btnEventUpdateSearch;
    @FXML
    private TextField txtEventNewName, txtEventNewDate, txtEventNewTime, txtEventNewSeats, txtEventNewDescription;
    @FXML
    private TextField txtEventDeleteID;
    @FXML
    private TextField txtEventEditID, txtEventEditName, txtEventEditDate, txtEventEditTime, txtEditSeats, txtEventEditDescription;
    @FXML
    private TableView<Event> tbEvents;
    @FXML
    private TableView<Event> tbEventsDelete;

    @FXML
    private void btnBackActionPerformed(ActionEvent evt) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/group404/AdminHome.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Admin Home");
            stage.setScene(new Scene(root));
            stage.show();

            ((Button) evt.getSource()).getScene().getWindow().hide();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to load Admin Home view.", Alert.AlertType.ERROR);
        }
    }


    @FXML
    private void btnLogOutActionPerformed(ActionEvent evt) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/group404/AdminLogin.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Admin Home");
            stage.setScene(new Scene(root));
            stage.show();

            ((Button) evt.getSource()).getScene().getWindow().hide();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to load Admin Login view.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleViewAllEvents(ActionEvent event) {
        Event eventObj = new Event();
        ObservableList<Event> eventList = FXCollections.observableArrayList(eventObj.viewAllEvents());
        tbEvents.setItems(eventList);
    }

    @FXML
    private void handleAddNewEvent(ActionEvent event) {
        String name = txtEventNewName.getText();
        String date = txtEventNewDate.getText();
        String time = txtEventNewTime.getText();
        String description = txtEventNewDescription.getText();

        short seats;
        try {
            seats = Short.parseShort(txtEventNewSeats.getText());
        } catch (NumberFormatException e) {
            showAlert("Error", "Unable to load Admin Home view.", Alert.AlertType.ERROR);
            return;
        }

        if (name.isEmpty() || date.isEmpty() || time.isEmpty() || description.isEmpty()) {
            showAlert("Error", "Unable to load Admin Home view.", Alert.AlertType.ERROR);
            return;
        }

        Event newEvent = new Event();

        if (newEvent.addNewEvent()) {
            showAlert("Error", "Unable to load Admin Home view.", Alert.AlertType.ERROR);
        } else {
            showAlert("Error", "Unable to load Admin Home view.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleEventDeleteSearch(ActionEvent event) {
        String keyword = txtEventDeleteID.getText();

        if (keyword.isEmpty()) {
            showAlert("Error", "Unable to load Admin Home view.", Alert.AlertType.ERROR);
        } else {
            Event deleteEvent = new Event();
            ObservableList<Event> searchResult = FXCollections.observableArrayList();

            if (deleteEvent.searchEvent(keyword)) {
                searchResult.add(deleteEvent);
                tbEventsDelete.setItems(searchResult);
            } else {
                showAlert("Error", "Unable to load Admin Home view.", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void handleEventDelete(ActionEvent event) {
        String eventId = txtEventDeleteID.getText();

        if (eventId.isEmpty()) {
            showAlert("Error", "Unable to load Admin Home view.", Alert.AlertType.ERROR);
        } else {
            Event eventDelete = new Event();

            if (eventDelete.deleteEvent(eventId)) {
                showAlert("Error", "Unable to load Admin Home view.", Alert.AlertType.ERROR);
            } else {
                showAlert("Error", "Unable to load Admin Home view.", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void handleEventUpdate(ActionEvent event) {
        String name = txtEventEditName.getText();
        String date = txtEventEditDate.getText();
        String time = txtEventEditTime.getText();
        String description = txtEventEditDescription.getText();

        if (!isValidDate(date)) {
            showAlert("Error", "Unable to load Admin Home view.", Alert.AlertType.ERROR);
            return;
        }

        short seatsAvailable;
        try {
            seatsAvailable = Short.parseShort(txtEditSeats.getText());
        } catch (NumberFormatException ex) {
            showAlert("Error", "Unable to load Admin Home view.", Alert.AlertType.ERROR);
            return;
        }

        int eventId;
        try {
            eventId = Integer.parseInt(txtEventEditID.getText());
        } catch (NumberFormatException ex) {
            showAlert("Error", "Unable to load Admin Home view.", Alert.AlertType.ERROR);
            return;
        }

        Event eventToUpdate = new Event(eventId, name, description, date, time, seatsAvailable);

        if (eventToUpdate.updateEvent()) {
            showAlert("Error", "Unable to load Admin Home view.", Alert.AlertType.ERROR);
        } else {
            showAlert("Error", "Unable to load Admin Home view.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleEventUpdateSearch(ActionEvent event) {
        String keyword = txtEventEditID.getText();

        if (keyword.isEmpty()) {
            showAlert("Error", "Unable to load Admin Home view.", Alert.AlertType.ERROR);
        } else {

            Event newEvent = new Event();

            if (newEvent.searchEvent(keyword)) {
                txtEventEditName.setText(newEvent.getName());
                txtEventEditDate.setText(newEvent.getDate());
                txtEventEditTime.setText(newEvent.getTime());
                txtEditSeats.setText(String.valueOf(newEvent.getSeats_available()));
                txtEventEditDescription.setText(newEvent.getDescription());
            } else {
                showAlert("Error", "Unable to load Admin Home view.", Alert.AlertType.ERROR);
            }
        }
    }

    private boolean isValidDate(String date) {
        return date.matches("\\d{4}-\\d{2}-\\d{2}");
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
