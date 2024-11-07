package org.example.group404;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class AdminHomeController {

    @FXML
    private Button btnLogout;

    @FXML
    private void handleMenuAction(ActionEvent event) {
        openWindow("/org/example/Group404/AdminMenu.fxml", "Menu Items");
    }

    @FXML
    private void handleEventAction(ActionEvent event) {
        openWindow("/org/example/Group404/EventAdmin.fxml", "Special Events");
    }

    @FXML
    private void handleReservationAction(ActionEvent event) {
        openWindow("/org/example/Group404/ReservationAdmin.fxml", "Reservations");
    }

    @FXML
    private void handleCustomerAction(ActionEvent event) {
        openWindow("/org/example/Group404/CustomerAdmin.fxml", "Customers");
    }

    @FXML
    private void handleAdminAction(ActionEvent event) {
        openWindow("/org/example/Group404/AdminAdmin.fxml", "Admin");
    }

    @FXML
    private void handleLogoutAction(ActionEvent event) {
        openWindow("/org/example/group404/AdminLogin.fxml", "Admin Login");
        // Close the current AdminHome window
        Stage stage = (Stage) btnLogout.getScene().getWindow();
        stage.close();
    }

    // Helper method to open new windows
    private void openWindow(String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
