package org.example.group404;

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

import java.io.IOException;
import java.util.List;

public class CustomerMenuController {


    @FXML private TextField txtName;
    @FXML private TableView<Menu> tbAll;
    @FXML private TableView<Menu> tbSearch;
    @FXML private ComboBox<String> cmbCat;
    @FXML private Button btnAll;
    @FXML private Button btnName;
    @FXML private Button btnCat;

    @FXML private TableColumn<Menu, String> colName, colCategory, colPrice, colDescription;
    @FXML private TableColumn<Menu, String> colNameSearch, colCategorySearch, colPriceSearch, colDescriptionSearch;

    @FXML
    public void initialize() {
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));


        cmbCat.getItems().addAll("Starters", "Mains", "Desserts", "Sides", "Drinks", "Snacks", "Breakfast", "Lunch", "Dinner");
        cmbCat.setPromptText("Select a category");

        colNameSearch.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDescriptionSearch.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPriceSearch.setCellValueFactory(new PropertyValueFactory<>("price"));
        colCategorySearch.setCellValueFactory(new PropertyValueFactory<>("category"));
    }

    // Method to handle the "View All" button action
    public void btnAllActionPerformed(ActionEvent event) {
        Menu menu = new Menu();
        List<Menu> menuItems = menu.viewAllMenu();

        ObservableList<Menu> menuData = FXCollections.observableArrayList(menuItems);

        // Set the data to the TableView
        tbAll.setItems(menuData);
    }

    // Method to handle "Search by Name" button action
    public void btnNameSearchActionPerformed(ActionEvent event) {
        String keyword = txtName.getText();

        if (keyword.isEmpty()) {
            showAlert(AlertType.ERROR, "Search Menu Item", "Sorry! Have to enter the name of the item.");
        } else {
            Menu searchMenu = new Menu();

            if (searchMenu.searchMenu(keyword)) {
                ObservableList<Menu> searchResults = FXCollections.observableArrayList();
                searchResults.add(searchMenu); // Assuming searchMenu holds the result

                // Update the TableView with search result
                tbSearch.setItems(searchResults);
            } else {
                showAlert(AlertType.ERROR, "Search Menu Item", "Sorry! We do not have any matching item.");
            }
        }
    }

    // Method to handle "Search by Category" button action
    public void btnCatSearchActionPerformed(ActionEvent event) {
        String selectedCategory = cmbCat.getValue();

        if (selectedCategory == null || selectedCategory.isEmpty()) {
            showAlert(AlertType.ERROR, "Search Menu Item", "Sorry! Please select a category.");
        } else {
            Menu menu = new Menu();
            List<Menu> items = menu.getItemsByCategory(selectedCategory);

            if (!items.isEmpty()) {
                ObservableList<Menu> categoryResults = FXCollections.observableArrayList(items);
                tbSearch.setItems(categoryResults);
            } else {
                showAlert(AlertType.ERROR, "Search Menu Item", "Sorry! No items found under this category.");
            }
        }
    }

    // Utility method to show alerts
    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
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

}
