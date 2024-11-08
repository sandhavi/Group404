package org.example.group404;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;

public class HomeController{

    @FXML
    private Button btnAdmin;

    @FXML
    private Button btnCustomer;

    @FXML
    private void handleCustomerLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/group404/CustomerLogin.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Admin Login");
            stage.show();

            Stage currentStage = (Stage) btnAdmin.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            showAlert("Error", "Could not load Admin Login screen: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleAdminLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/group404/AdminLogin.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Admin Login");
            stage.show();

            Stage currentStage = (Stage) btnAdmin.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            showAlert("Error", "Could not load Admin Login screen: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
