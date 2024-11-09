package org.example.group404;

import javafx.event.ActionEvent;
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

import java.io.IOException;

public class CustomerLoginController {

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPassword;

    @FXML
    private Button btnLogin;

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(AlertType.ERROR, "Login Error", "Login credentials cannot be blank.");
        } else {
            Customer customer = new Customer();

            if (customer.validateLogin(username, password)) {
                showAlert(AlertType.INFORMATION, "Login Success", "Login Successful.");
                openAdminHome();
            } else {
                showAlert(AlertType.WARNING, "Login Failed", "Invalid username or password.");
            }
        }
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void openAdminHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/group404/CustomerHome.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Customer Home");
            stage.show();

            Stage loginStage = (Stage) btnLogin.getScene().getWindow();
            loginStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Error", "Unable to load Customer Home.");
        }
    }

    @FXML
    private void backSignUpAction(ActionEvent evt) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/group404/CustomerRegister.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Admin Home");
            stage.setScene(new Scene(root));
            stage.show();

            ((Button) evt.getSource()).getScene().getWindow().hide();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Sorry! Try Again");
        }
    }
}
