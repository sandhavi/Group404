package org.example.group404.ControllerPackage;

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
import org.example.group404.ClassPackage.Event;

import java.io.IOException;
import java.util.List;

public class CustomerEventController {

    @FXML
    private TableView<Event> tbAll;
    @FXML
    private TableColumn<Event, String> colName, colDes, colDate, colTime;
    @FXML
    private TableColumn<Event, Short> colSeats;

    @FXML
    public void initialize() {
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDes.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colSeats.setCellValueFactory(new PropertyValueFactory<>("seats_available"));

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
            showAlert("Error", "Unable to load Admin Home view.", Alert.AlertType.ERROR);
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
            showAlert("Error", "Unable to load Admin Login view.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void onViewAllEvents(ActionEvent event) {
        Event eventObj = new Event();
        List<Event> eventList = eventObj.viewAllEvents();

        // Convert the list of events to an ObservableList for JavaFX
        ObservableList<Event> eventData = FXCollections.observableArrayList(eventList);

        // Update the TableView with the data
        tbAll.setItems(eventData);
    }

    // Initialization of the TableView columns
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
