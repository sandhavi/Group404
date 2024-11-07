package org.example.group404;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class CustomerHomeController {

    @FXML
    private Label welcomeLabel;
    @FXML
    private Button btnMenu;
    @FXML
    private Button btnEvent;
    @FXML
    private Button btnReservation;
    @FXML
    private Button btnProfile;
    @FXML
    private Button btnLogOut;


    // Utility method to show JavaFX Alerts
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void initialize() {
        welcomeLabel.setText("Welcome to Our Restaurant");
    }

    @FXML
    private void handleMenuButton(ActionEvent event) {
        openNewWindow("/org/example/group404/AdminMenu.fxml");  // Replace with correct FXML path
    }

    @FXML
    private void handleEventButton(ActionEvent event) {
        openNewWindow("/org/example/group404/AdminEvents.fxml");  // Replace with correct FXML path
    }

    @FXML
    private void handleReservationButton(ActionEvent event) {
        openNewWindow("/org/example/group404/AdminReservation.fxml");  // Replace with correct FXML path
    }

    @FXML
    private void handleProfileButton(ActionEvent event) {
        openNewWindow("/org/example/group404/AdminProfile.fxml");  // Replace with correct FXML path
    }

    @FXML
    private void handleLogOutButton(ActionEvent event) {
        openNewWindow("/org/example/group404/CustomerLogin.fxml");  // Replace with correct FXML path
    }

    private void openNewWindow(String fxmlPath) {
        try {
            // Load the FXML file for the new window
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Create a new stage (window) for the scene
            Stage stage = new Stage();
            stage.setTitle("New Window");  // Set a title for the window
            stage.setScene(new Scene(root));
            stage.show();

            // Close the current window
            closeCurrentWindow();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Could not load the new window.", Alert.AlertType.ERROR);
        }
    }

    private void closeCurrentWindow() {
        Stage stage = (Stage) btnLogOut.getScene().getWindow();
        stage.close();
    }

}
