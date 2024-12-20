package org.example.group404.ControllerPackage;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.group404.ClassPackage.Admin;

import java.io.IOException;

public class AdminAdminController {

    @FXML
    private TextField txtNewName;
    @FXML
    private TextField txtNewEmail;
    @FXML
    private TextField txtNewUsername;
    @FXML
    private TextField txtNewPasswords;

    @FXML
    private TextField txtDeleteUsername;
    @FXML
    private TableView<Admin> tbAdmin;
    @FXML
    private TableColumn<Admin, Integer> colAdminId;
    @FXML
    private TableColumn<Admin, String> colAdminName;
    @FXML
    private TableColumn<Admin, String> colAdminEmail;
    @FXML
    private TableColumn<Admin, String> colAdminUserName;
    @FXML
    private TableColumn<Admin, String> colAdminPassword;

    public void initialize() {
        colAdminId.setCellValueFactory(new PropertyValueFactory<>("admin_id"));
        colAdminName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAdminEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAdminUserName.setCellValueFactory(new PropertyValueFactory<>("username"));
        colAdminPassword.setCellValueFactory(new PropertyValueFactory<>("password"));

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
    private void btnNewAdminActionPerformed(ActionEvent evt) {
        String name = txtNewName.getText();
        String email = txtNewEmail.getText();
        String username = txtNewUsername.getText();
        String password = txtNewPasswords.getText();

        if (name.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
            showAlert("Admin Registration", "All fields are required.", AlertType.ERROR);
            return;
        }

        if (!isValidEmail(email)) {
            showAlert("Admin Registration", "Please enter a valid email address.", AlertType.ERROR);
            return;
        }

        if (password.length() < 8) {
            showAlert("Admin Registration", "Password must be at least 8 characters long.", AlertType.ERROR);
            return;
        }

        Admin newAdmin = new Admin();
        newAdmin.setName(name);
        newAdmin.setEmail(email);
        newAdmin.setUsername(username);
        newAdmin.setPassword(password);

        if (newAdmin.addNewAdmin()) {
            showAlert("Admin Registration", "Admin Registration Successful!", AlertType.INFORMATION);
            clearNewAdminFields();
        } else {
            showAlert("Admin Registration", "Admin Registration Failed. Please try again.", AlertType.ERROR);
        }
    }

    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    @FXML
    private void btnDeleteSearchActionPerformed(ActionEvent evt) {
        String keyword = txtDeleteUsername.getText();

        if (keyword.isEmpty()) {
            showAlert("Search Admin", "Sorry! You need to enter a Username.", AlertType.ERROR);
            return;
        }

        Admin adminToSearch = new Admin();
        ObservableList<Admin> adminData = FXCollections.observableArrayList();

        if (adminToSearch.searchAdmin(keyword)) {
            adminData.add(adminToSearch);
            tbAdmin.setItems(adminData);
        } else {
            showAlert("Search Admin", "Sorry! No matching admin found.", AlertType.ERROR);
            tbAdmin.getItems().clear();
        }
    }

    @FXML
    private void btnDeleteDeleteActionPerformed(ActionEvent evt) {
        String username = txtDeleteUsername.getText();

        if (username.isEmpty()) {
            showAlert("Delete Admin", "Sorry! You need to enter a Username.", AlertType.ERROR);
            return;
        }

        Admin adminToDelete = new Admin();

        if (adminToDelete.deleteAdmin(username)) {
            showAlert("Delete Admin", "Admin deleted successfully.", AlertType.INFORMATION);
            tbAdmin.getItems().clear();
        } else {
            showAlert("Delete Admin", "Sorry! No matching admin found.", AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearNewAdminFields() {
        txtNewName.clear();
        txtNewEmail.clear();
        txtNewUsername.clear();
        txtNewPasswords.clear();
    }
}
