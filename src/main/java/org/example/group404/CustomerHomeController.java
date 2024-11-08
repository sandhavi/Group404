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

import java.io.IOException;

public class CustomerHomeController {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnLogout;
    @FXML
    private Button btnMenu, btnEvent, btnReservation, btnProfile;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        String fxmlFile = null;
        String title = null;

        if (event.getSource() == btnMenu) {
            fxmlFile = "/org/example/group404/CustomerMenu.fxml";
            title = "Customer Menu";
        } else if (event.getSource() == btnEvent) {
            fxmlFile = "/org/example/group404/CustomerMenu.fxml";
            title = "Customer Event";
        } else if (event.getSource() == btnReservation) {
            fxmlFile = "/org/example/group404/CustomerMenu.fxml";
            title = "Customer Reservation";
        } else if (event.getSource() == btnProfile) {
            fxmlFile = "/org/example/group404/CustomerMenu.fxml";
            title = "Customer Customer";
        } else if (event.getSource() == btnLogout) {
            fxmlFile = "/org/example/group404/Home.fxml";
            title = "Customer Reservation";
        } else if (event.getSource() == btnBack) {
            fxmlFile = "/org/example/group404/CustomerHome.fxml";
            title = "Customer Customer";
        }

        if (fxmlFile != null && title != null) {
            openWindow(fxmlFile, title);
            closeCurrentStage();
        }
    }

    private void openWindow(String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showAlert("Error", "Could not load screen: "+"\n"+ e.getMessage(), Alert.AlertType.ERROR);
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void closeCurrentStage() {
        Stage currentStage = (Stage) btnLogout.getScene().getWindow();
        currentStage.close();
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
