package org.example.group404;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomerProfileController {

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPhone;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtUsername;


    @FXML
    private void btnUpdateAction() {
        String name = txtName.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();
        String password = txtPassword.getText();
        String username = txtUsername.getText();
        String currentUsername = txtUsername.getText();

        Customer updateCustomer = new Customer();
        updateCustomer.setName(name);
        updateCustomer.setEmail(email);
        updateCustomer.setPhone(phone);
        updateCustomer.setPassword(password);
        updateCustomer.setUsername(username);

        if (updateCustomer.updateProfile(currentUsername)) {
            showAlert(AlertType.INFORMATION, "Profile Update", "Profile updated successfully!");
        } else {
            showAlert(AlertType.ERROR, "Profile Update", "Failed to update profile.");
        }
    }

    @FXML
    private void btnProfileAction() {
        String username = txtUsername.getText();

        if (username.isEmpty()) {
            showAlert(AlertType.ERROR, "Search Profile", "Please enter a valid Username.");
            return;
        }

        Customer profile = new Customer();

        if (profile.searchProfile(username)) {
            txtName.setText(profile.getName());
            txtEmail.setText(profile.getEmail());
            txtPhone.setText(profile.getPhone());
            txtPassword.setText(profile.getPassword());
        } else {
            showAlert(AlertType.ERROR, "Search Profile", "Profile not found. Please try again.");
        }
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
    private void showAlert(AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
