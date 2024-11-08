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

public class AdminMenuController {

    @FXML private TextField txtMenuIDDelete,txtSearchName, txtEditName,txtEditPrice, txtEditID,txtNewPrice, txtNewName;
    @FXML private ComboBox<String> cmbCatSearch,cmbEditCategory,cmbNewCategory;
    @FXML private TextArea txtNewDescription, txtEditDescription;
    @FXML private TableView<Menu> tbDelete, menuTable,tbSearch, tbSearchView, tbDeletehView, tbAll;
    @FXML private TableColumn<Menu, Integer> colMenuIDDelete, colMenuID, colMenuIDSearch, colMenuCat, colSearchMenuID, colSearchName, colSearchDescription;
    @FXML private TableColumn<Menu, String> colNameDelete, colMenuName, colMenuNameSearch, colMenuNameDelete, colSearchCategory;
    @FXML private TableColumn<Menu, String> colMenuDescription, colMenuDescriptionSearch, colMenuDescriptionDelete;
    @FXML private TableColumn<Menu, Double> colMenuPrice, colMenuPriceSearch, colMenuPriceDelete, colSearchPrice;
    @FXML private TableColumn<Menu, String> colMenuCategory, colMenuCategorySearch, colMenuCategoryDelete;
    @FXML private Button btnIDearch, btnIDDelete,btnBack,btnEditSearch, btnEditUpdate, btnCatSearch, btnNameSearch, btnNewMenu, btnLogout, btnViewAll;


    @FXML
    public void initialize() {
        colMenuID.setCellValueFactory(new PropertyValueFactory<>("item_id"));
        colMenuName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colMenuDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colMenuPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colMenuCat.setCellValueFactory(new PropertyValueFactory<>("category"));

        cmbNewCategory.getItems().addAll("Starters", "Mains", "Desserts", "Sides", "Drinks", "Snacks", "Breakfast", "Lunch", "Dinner");
        cmbNewCategory.setPromptText("Select a category");
        cmbEditCategory.getItems().addAll("Starters", "Mains", "Desserts", "Sides", "Drinks", "Snacks", "Breakfast", "Lunch", "Dinner");
        cmbEditCategory.setPromptText("Select a category");
        cmbCatSearch.getItems().addAll("Starters", "Mains", "Desserts", "Sides", "Drinks", "Snacks", "Breakfast", "Lunch", "Dinner");
        cmbCatSearch.setPromptText("Select a category");


        colSearchMenuID.setCellValueFactory(new PropertyValueFactory<>("item_id"));
        colSearchName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSearchDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colSearchPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colSearchCategory.setCellValueFactory(new PropertyValueFactory<>("category"));

        colMenuIDDelete.setCellValueFactory(new PropertyValueFactory<>("item_id"));
        colNameDelete.setCellValueFactory(new PropertyValueFactory<>("name"));
        colMenuDescriptionDelete.setCellValueFactory(new PropertyValueFactory<>("description"));
        colMenuPriceDelete.setCellValueFactory(new PropertyValueFactory<>("price"));
        colMenuCategoryDelete.setCellValueFactory(new PropertyValueFactory<>("category"));


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
    private void btnEditUpdateActionPerformed(ActionEvent evt) {
        String name = txtEditName.getText();
        String description = txtEditDescription.getText();

        double price;
        try {
            price = Double.parseDouble(txtEditPrice.getText());
        } catch (NumberFormatException ex) {
            showAlert("Invalid Input", "Please enter a valid price.", AlertType.ERROR);
            return;
        }

        String category = cmbEditCategory.getSelectionModel().getSelectedItem();

        int itemId;
        try {
            itemId = Integer.parseInt(txtEditID.getText());
        } catch (NumberFormatException ex) {
            showAlert("Invalid Input", "Please enter a valid Item ID.", AlertType.ERROR);
            return;
        }

        Menu menuToUpdate = new Menu(itemId, name, description, price, category);

        if (menuToUpdate.updateMenu()) {
            showAlert("Success", "Menu item updated successfully!", AlertType.INFORMATION);
        } else {
            showAlert("Error", "Failed to update menu item.", AlertType.ERROR);
        }
    }

    @FXML
    private void btnDeleteDeleteActionPerformed(ActionEvent evt) {
        String itemid = txtMenuIDDelete.getText();

        if (itemid.isEmpty()) {
            showAlert("Delete Item", "Sorry! Have to enter item ID.", AlertType.ERROR);
        } else {
            Menu menuDelete = new Menu();

            if (menuDelete.deleteMenu(itemid)) {
                showAlert("Delete Menu Item", "Menu Item deleted successfully.", AlertType.INFORMATION);
            } else {
                showAlert("Delete Menu Item", "Sorry! No matching menu item found.", AlertType.ERROR);
            }
        }
    }

    @FXML
    private void btnViewMenuActionPerformed(ActionEvent evt) {
        Menu menu = new Menu();
        ObservableList<Menu> menuItems = FXCollections.observableArrayList(menu.viewAllMenu());

        System.out.println("Items to display: " + menuItems.size()); // Debugging output

        tbAll.getItems().clear();
        tbAll.setItems(menuItems);

        if (menuItems.isEmpty()) {
            showAlert("No Items", "No menu items found.", AlertType.INFORMATION);
        }
    }

    @FXML
    private void btnNewItemActionPerformed(ActionEvent evt) {
        String name = txtNewName.getText();
        String category = cmbNewCategory.getSelectionModel().getSelectedItem();
        double price;
        try {
            price = Double.parseDouble(txtNewPrice.getText());
        } catch (NumberFormatException e) {
            showAlert("Add New Menu Item", "Please enter a valid price.", AlertType.ERROR);
            return;
        }
        String description = txtNewDescription.getText();

        if (name.isEmpty() || category == null || description.isEmpty()) {
            showAlert("Add New Menu Item", "All fields are required.", AlertType.ERROR);
            return;
        }

        Menu newMenu = new Menu();
        newMenu.setName(name);
        newMenu.setCategory(category);
        newMenu.setPrice(price);
        newMenu.setDescription(description);

        if (newMenu.addNewItem()) {
            showAlert("Add New Menu Item", "Successfully Added New Menu Item.", AlertType.INFORMATION);
        } else {
            showAlert("Add New Menu Item", "Adding New Item Failed. Please try again.", AlertType.ERROR);
        }
    }

    @FXML
    private void btnSearchNameActionPerformed(ActionEvent evt) {
        String keyword = txtSearchName.getText();

        if (keyword.isEmpty()) {
            showAlert("Search Menu Item", "Please enter the name of the item to search.", AlertType.ERROR);
            return;
        }

        Menu menu = new Menu();
        List<Menu> searchResults = menu.viewAllMenu().stream()
                .filter(item -> item.getName().toLowerCase().contains(keyword.toLowerCase()))
                .toList();

        if (searchResults.isEmpty()) {
            showAlert("Search Menu Item", "No matching items found.", AlertType.ERROR);
        } else {
            ObservableList<Menu> searchResultList = FXCollections.observableArrayList(searchResults);
            tbSearch.getItems().clear();
            tbSearch.setItems(searchResultList);
        }
    }


    @FXML
    private void btnSearchCatActionPerformed(ActionEvent evt) {
        String selectedCategory = cmbCatSearch.getSelectionModel().getSelectedItem();

        if (selectedCategory == null || selectedCategory.isEmpty()) {
            showAlert("Search Menu Item", "Please select a category", AlertType.ERROR);
        } else {
            Menu menu = new Menu();
            ObservableList<Menu> items = FXCollections.observableArrayList(menu.getItemsByCategory(selectedCategory));

            tbSearch.getItems().clear();
            tbSearch.setItems(items);

            if (items.isEmpty()) {
                showAlert("Search Menu Item", "No items found under this category", AlertType.ERROR);
            }
        }
    }

    @FXML
    private void btnDeleteSearchActionPerformed(ActionEvent evt) {
        String keyword = txtMenuIDDelete.getText();

        if (keyword.isEmpty()) {
            showAlert("Search Menu Item", "Please enter the id of the item to search.", AlertType.ERROR);
            return;
        }

        Menu menu = new Menu();

        List<Menu> searchResults = menu.viewAllMenu().stream()
                .filter(item -> item.getItem_id() == Integer.parseInt(keyword))
                .toList();

        if (searchResults.isEmpty()) {
            showAlert("Search Menu Item", "No matching items found.", AlertType.ERROR);
        } else {
            ObservableList<Menu> searchResultList = FXCollections.observableArrayList(searchResults);
            tbDelete.getItems().clear();
            tbDelete.setItems(searchResultList);
        }
    }

    @FXML
    private void btnEditSearchActionPerformed(ActionEvent evt) {
        String keyword = txtEditID.getText();

        if (keyword.isEmpty()) {
            showAlert("Search Menu", "Please enter a valid Menu ID", AlertType.ERROR);
        } else {
            Menu menu = new Menu();

            if (menu.searchMenu2(keyword)) {
                txtEditName.setText(menu.getName());
                cmbEditCategory.getSelectionModel().select(menu.getCategory());
                txtEditPrice.setText(String.valueOf(menu.getPrice()));
                txtEditDescription.setText(menu.getDescription());
            } else {
                showAlert("Search Menu", "No matching menu item found.", AlertType.ERROR);
            }
        }
    }

    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
