package org.example.group404;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CustomerLoginController {

    @FXML
    private TextField txtcusUsername;

    @FXML
    private PasswordField txtcusPassword;

    @FXML
    private Button btnCusLogin;

    @FXML
    private Button btnNew;

    @FXML
    private Label lblTitle;

    @FXML
    private Label lblUsername;

    @FXML
    private Label lblPassword;

    // Initialize method (automatically called after FXML file is loaded)
    @FXML
    public void initialize() {
        lblTitle.setText("Customer Login");
        lblUsername.setText("Username");
        lblPassword.setText("Password");
    }

    @FXML
    private void handleLogin() {
        String username = txtcusUsername.getText();
        String password = txtcusPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(AlertType.ERROR, "Login Error", "Login credentials cannot be blank.");
        } else {
            Customer user = new Customer();
            if (user.validateLogin(username, password)) {
                showAlert(AlertType.INFORMATION, "Login Successful", "Welcome to the system!");

                // Load CustomerHome screen using FXMLLoader
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/group404/CustomerHome.fxml"));
                    Object root = loader.load();

                    Stage stage = new Stage();
                    stage.setTitle("Customer Home");
                    stage.setScene(new Scene((Parent) root));
                    stage.show();

                    // Close the current login window
                    closeWindow();

                } catch (Exception e) {
                    e.printStackTrace();
                    showAlert(AlertType.ERROR, "Loading Error", "Could not load the home screen.");
                }

            } else {
                showAlert(AlertType.WARNING, "Login Failed", "Invalid username or password.");
            }
        }
    }


    @FXML
    private void handleNewAccount() {
        try {
            // Load the CustomerLogin.fxml screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/Group404/CustomerLogin.fxml"));
            Parent root = loader.load();

            // Set up a new stage for the registration window
            Stage stage = new Stage();
            stage.setTitle("Register New Account");
            stage.setScene(new Scene(root));
            stage.show();

            // Close the current login window
            closeWindow();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Loading Error", "Unable to open the registration screen.");
        }
    }


    // Helper method to show alert dialog
    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Helper method to close the current window
    private void closeWindow() {
        Stage stage = (Stage) btnCusLogin.getScene().getWindow();
        stage.close();
    }
}
