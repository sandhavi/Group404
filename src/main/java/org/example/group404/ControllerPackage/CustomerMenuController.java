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
import org.example.group404.ClassPackage.Menu;

import java.io.IOException;
import java.util.List;

public class CustomerMenuController {


    @FXML private TextField txtName;
    @FXML private TableView<org.example.group404.ClassPackage.Menu> tbAll;
    @FXML private TableView<org.example.group404.ClassPackage.Menu> tbSearch;
    @FXML private ComboBox<String> cmbCat;
    @FXML private Button btnAll;
    @FXML private Button btnName;
    @FXML private Button btnCat;

    @FXML private TableColumn<org.example.group404.ClassPackage.Menu, String> colName, colCategory, colPrice, colDescription;
    @FXML private TableColumn<org.example.group404.ClassPackage.Menu, String> colNameSearch, colCategorySearch, colPriceSearch, colDescriptionSearch;

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

    public void btnAllActionPerformed(ActionEvent event) {
        org.example.group404.ClassPackage.Menu menu = new org.example.group404.ClassPackage.Menu();
        List<org.example.group404.ClassPackage.Menu> menuItems = menu.viewAllMenu();

        ObservableList<org.example.group404.ClassPackage.Menu> menuData = FXCollections.observableArrayList(menuItems);

        tbAll.setItems(menuData);
    }

    public void btnNameSearchActionPerformed(ActionEvent event) {
        String keyword = txtName.getText();

        if (keyword.isEmpty()) {
            showAlert(AlertType.ERROR, "Search Menu Item", "Sorry! Have to enter the name of the item.");
        } else {
            org.example.group404.ClassPackage.Menu searchMenu = new org.example.group404.ClassPackage.Menu();

            if (searchMenu.searchMenu(keyword)) {
                ObservableList<org.example.group404.ClassPackage.Menu> searchResults = FXCollections.observableArrayList();
                searchResults.add(searchMenu);

                tbSearch.setItems(searchResults);
            } else {
                showAlert(AlertType.ERROR, "Search Menu Item", "Sorry! We do not have any matching item.");
            }
        }
    }

    public void btnCatSearchActionPerformed(ActionEvent event) {
        String selectedCategory = cmbCat.getValue();

        if (selectedCategory == null || selectedCategory.isEmpty()) {
            showAlert(AlertType.ERROR, "Search Menu Item", "Sorry! Please select a category.");
        } else {
            org.example.group404.ClassPackage.Menu menu = new org.example.group404.ClassPackage.Menu();
            List<org.example.group404.ClassPackage.Menu> items = menu.getItemsByCategory(selectedCategory);

            if (!items.isEmpty()) {
                ObservableList<Menu> categoryResults = FXCollections.observableArrayList(items);
                tbSearch.setItems(categoryResults);
            } else {
                showAlert(AlertType.ERROR, "Search Menu Item", "Sorry! No items found under this category.");
            }
        }
    }

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
