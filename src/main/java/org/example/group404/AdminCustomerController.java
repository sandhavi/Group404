package org.example.group404;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.List;

public class AdminCustomerController {

    @FXML
    private TextField txtName;
    @FXML
    private TableView<Customer> tbAll;
    @FXML
    private TableColumn<Customer, String> colID, colName, colEmail, colPhone;

    // Initialize TableView Columns
    public void initialize() {
        colID.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
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
            showAlert(AlertType.ERROR, "Failed", "Sorry! Try Again");
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
            showAlert(AlertType.ERROR, "Log Out Failed", "Sorry! Try Again");
        }
    }

    @FXML
    private void handleSearchCustomer(ActionEvent event) {
        String keyword = txtName.getText();

        if (keyword.isEmpty()) {
            showAlert(AlertType.ERROR, "Search Customer", "Sorry! Have to enter Name");
        } else {
            Customer searchCustomer = new Customer();
            ObservableList<Customer> data = FXCollections.observableArrayList();
            tbAll.getItems().clear();

            if (searchCustomer.searchCustomer(keyword)) {
                data.add(searchCustomer);
                tbAll.setItems(data);
            } else {
                showAlert(AlertType.ERROR, "Search Customer", "Sorry! We do not have any matching Customer");
            }
        }
    }

    @FXML
    private void handleViewAllCustomers(ActionEvent event) {
        Admin admin = new Admin();
        List<Customer> customerList = admin.viewAllUsers();
        ObservableList<Customer> data = FXCollections.observableArrayList(customerList);

        tbAll.getItems().clear();
        tbAll.setItems(data);
    }

    @FXML
    private void handleDeleteCustomer(ActionEvent event) {
        String name = txtName.getText();

        if (name.isEmpty()) {
            showAlert(AlertType.ERROR, "Delete Customer", "Sorry! Have to enter Name");
        } else {
            Customer deleteCustomer = new Customer();

            if (deleteCustomer.deleteCustomer(name)) {
                showAlert(AlertType.INFORMATION, "Delete Customer", "Customer deleted successfully.");
                handleViewAllCustomers(null); // Refresh the table after deletion
            } else {
                showAlert(AlertType.ERROR, "Delete Customer", "Sorry! No matching customer found.");
            }
        }
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
