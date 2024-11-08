package org.example.group404;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AdminEventController {

    @FXML
    private TextField txtNewName;
    @FXML
    private TextArea txtNewDescription, txtEditDescription;
    @FXML
    private TextField txtDeleteID;
    @FXML
    private ComboBox<String> txtNewTime, txtEditTime;
    @FXML
    private DatePicker txtNewDate, txtEditDate;
    @FXML
    private TextField txtEditID, txtEditName;
    @FXML
    private TableColumn<Event, Integer> colAllID;
    @FXML
    private TableColumn<Event, Integer> colDeleteID;
    @FXML
    private TableColumn<Event, String> colAllName, colAllDate, colAllTime, colAllSeats, colAllDescription;
    @FXML
    private TableColumn<Event, String> colDeleteName, colDeleteDate, colDeleteTime, colDeleteSeats, colDeleteDescription;
    @FXML
    private Spinner<Integer> txtNewSeats, txtEditSeats;

    @FXML
    private TableView<Event> tbAll;
    @FXML
    private TableView<Event> tbDelete;

    @FXML
    private void initialize() {
        colAllID.setCellValueFactory(new PropertyValueFactory<>("event_id"));
        colAllName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAllDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colAllTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colAllSeats.setCellValueFactory(new PropertyValueFactory<>("seats_available"));
        colAllDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        colDeleteID.setCellValueFactory(new PropertyValueFactory<>("event_id"));
        colDeleteName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDeleteDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDeleteTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colDeleteSeats.setCellValueFactory(new PropertyValueFactory<>("seats_available"));
        colDeleteDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        txtNewTime.getItems().addAll("08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00",
                "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00");
        txtNewTime.setPromptText("Select a time");

        txtEditTime.getItems().addAll("08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00",
                "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00");
        txtEditTime.setPromptText("Select a time");

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, 1); // Min, Max, Initial value
        txtNewSeats.setValueFactory(valueFactory);

        SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, 1); // Min, Max, Initial value
        txtEditSeats.setValueFactory(valueFactory2);
    }

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
            stage.setTitle("Admin Login");
            stage.setScene(new Scene(root));
            stage.show();

            ((Button) evt.getSource()).getScene().getWindow().hide();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to load Admin Login view.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleViewAllEvents(ActionEvent evt) {
        Event event = new Event();
        ObservableList<Event> eventItems = FXCollections.observableArrayList(event.viewAllEvents());

        tbAll.getItems().clear();
        tbAll.setItems(eventItems);

        if (eventItems.isEmpty()) {
            showAlert("No Events", "No events found.", Alert.AlertType.INFORMATION);
        }
    }
@FXML
    private void handleAddNewEvent(ActionEvent event) {
        String name = txtNewName.getText();
        String date = txtNewDate.getValue() != null ? txtNewDate.getValue().toString() : "";
        String time = txtNewTime.getValue();
        String description = txtNewDescription.getText();

        short seats;
        try {
            seats = txtNewSeats.getValue().shortValue();
        } catch (Exception e) {
            showAlert("Error", "Invalid number of seats entered.", Alert.AlertType.ERROR);
            return;
        }

        // Check if required fields are empty
        if (name.isEmpty() || date.isEmpty() || time == null || time.isEmpty() || description.isEmpty()) {
            showAlert("Error", "All fields must be filled, including time.", Alert.AlertType.ERROR);
            return;
        }

        Event newEvent = new Event(name, description, date, time, seats);

        if (newEvent.addNewEvent()) {
            showAlert("Success", "Event added successfully.", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Error", "Failed to add event.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleEventDeleteSearch(ActionEvent event) {
        String keyword = txtDeleteID.getText();
        if (keyword.isEmpty()) {
            showAlert("Error", "Please enter an event ID to search.", Alert.AlertType.ERROR);
        } else {
            Event deleteEvent = new Event();
            ObservableList<Event> searchResult = FXCollections.observableArrayList();
            if (deleteEvent.searchEvent(keyword)) {
                searchResult.add(deleteEvent);
                tbDelete.setItems(searchResult);
            } else {
                showAlert("Error", "Event not found.", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void handleEventDelete(ActionEvent event) {
        String eventId = txtDeleteID.getText();
        if (eventId.isEmpty()) {
            showAlert("Error", "Please enter an event ID to delete.", Alert.AlertType.ERROR);
        } else {
            Event eventDelete = new Event();
            if (eventDelete.deleteEvent(eventId)) {
                showAlert("Success", "Event deleted successfully.", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Error", "Failed to delete event.", Alert.AlertType.ERROR);
            }
        }
    }


    @FXML
    private void handleEventUpdate(ActionEvent event) {
        String name = txtEditName.getText();
        String date = txtEditDate.getValue().toString();
        String time = txtEditTime.getValue();
        String description = txtEditDescription.getText();

        if (!isValidDate(date)) {
            showAlert("Error", "Invalid date format. Please use YYYY-MM-DD.", Alert.AlertType.ERROR);
            return;
        }

        short seatsAvailable;
        try {
            seatsAvailable = txtEditSeats.getValue().shortValue();
        } catch (NumberFormatException ex) {
            showAlert("Error", "Invalid number of seats entered.", Alert.AlertType.ERROR);
            return;
        }

        int eventId;
        try {
            eventId = Integer.parseInt(txtEditID.getText());
        } catch (NumberFormatException ex) {
            showAlert("Error", "Invalid event ID entered.", Alert.AlertType.ERROR);
            return;
        }

        Event eventToUpdate = new Event(eventId, name, description, date, time, seatsAvailable);

        if (eventToUpdate.updateEvent()) {
            showAlert("Success", "Event updated successfully.", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Error", "Failed to update event.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleEventUpdateSearch(ActionEvent event) {
        String keyword = txtEditID.getText();

        if (keyword.isEmpty()) {
            showAlert("Error", "Please enter an event ID to search.", Alert.AlertType.ERROR);
        } else {
            Event newEvent = new Event();

            if (newEvent.searchEvent(keyword)) {
                txtEditName.setText(newEvent.getName());
                if (newEvent.getDate() != null && !newEvent.getDate().isEmpty()) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate eventDate = LocalDate.parse(newEvent.getDate(), formatter);
                    txtEditDate.setValue(eventDate);
                }

                txtEditTime.setValue(newEvent.getTime());
                txtEditSeats.getValueFactory().setValue(newEvent.getSeats_available());
                txtEditDescription.setText(newEvent.getDescription());
            } else {
                showAlert("Error", "Event not found.", Alert.AlertType.ERROR);
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
