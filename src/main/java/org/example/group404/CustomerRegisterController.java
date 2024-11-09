package org.example.group404;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomerRegisterController {

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPassword;

    // Ensure this method matches your button's "onAction" event in the FXML
    @FXML
    private void btnSignInAction() {
        // Get user input from the text fields
        String name = txtName.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Registration", "All fields are required.");
            return;
        }

        Customer newUser = new Customer();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPhone(phone);
        newUser.setUsername(username);
        newUser.setPassword(password);

        if (newUser.saveToDatabase()) {
            showAlert(Alert.AlertType.INFORMATION, "Registration", "Registration Successful!");

            try {
                Stage stage = (Stage) txtName.getScene().getWindow();
                stage.close();

                Stage loginStage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/group404/CustomerLogin.fxml"));
                Scene loginScene = new Scene(fxmlLoader.load());
                loginStage.setScene(loginScene);
                loginStage.setTitle("Customer Login");
                loginStage.show();
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to open the login screen.");
                e.printStackTrace();
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Registration", "Registration Failed. Please try again.");
        }
    }

    // Ensure this method matches your back button's "onAction" event in the FXML
    @FXML
    private void backLoginAction(ActionEvent evt) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/group404/CustomerLogin.fxml"));
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

    // Helper method to show alerts
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
