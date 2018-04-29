package matt.joslen.project.ViewController;

import java.io.IOException;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import matt.joslen.project.Model.Inventory;
import matt.joslen.project.Model.Part;
import matt.joslen.project.Model.Product;

public class AddProductController {

    @FXML
    private TextField productMinTextField;

    @FXML
    private TextField productMaxTextField;

    @FXML
    private TextField productIDTextField;

    @FXML
    private TextField productNameTextField;

    @FXML
    private TextField productInvTextField;

    @FXML
    private TextField productPriceTextField;
    
    @FXML
    private TableView<Part> partTable1;
    
    @FXML
    private TableColumn<Part, Integer> partIDColumn1;

    @FXML
    private TableColumn<Part, String> partNameColumn1;
    
    @FXML
    private TableColumn<Part, Integer> partInvColumn1;
    
    @FXML
    private TableColumn<Part, Double> partPriceColumn1;
    
    @FXML
    private TableView<Part> partTable2;
    
    @FXML
    private TableColumn<Part, Integer> partIDColumn2;
    
    @FXML
    private TableColumn<Part, String> partNameColumn2;
    
    @FXML
    private TableColumn<Part, Integer> partInvColumn2;
    
    @FXML
    private TableColumn<Part, Double> partPriceColumn2;
    
    @FXML
    private Button partSearchButton;

    @FXML
    private Button addPartButton;

    @FXML
    private Button deletePartButton;

    @FXML
    private Button addProductCancelButton;

    @FXML
    private Button addProductSaveButton;

    @FXML
    private TextField partSearchTextField;

    Inventory inventory;
    
    @FXML
    public void initialize() {
        
        setInventory(inventory);
        
        partIDColumn1.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        partNameColumn1.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        partInvColumn1.setCellValueFactory(cellData -> cellData.getValue().inStockProperty().asObject());
        partPriceColumn1.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        
        partsToProduct = partTable2.getItems();
    }
    
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
        
        partTable1.setItems(Inventory.getAllParts());
    }
    
    @FXML
    public ObservableList<Part> partsToProduct = FXCollections.observableArrayList();
    
    @FXML
    void AddPartButtonHandler(ActionEvent event) {
        Part selectedItem = partTable1.getSelectionModel().getSelectedItem();
        
        if (selectedItem != null) {                     
            partsToProduct.add(selectedItem);
                    
            partIDColumn2.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
            partNameColumn2.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
            partInvColumn2.setCellValueFactory(cellData -> cellData.getValue().inStockProperty().asObject());
            partPriceColumn2.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
                    
            partTable2.setItems(partsToProduct);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Selection");
            alert.setHeaderText("No part is selected");
            alert.setContentText("Please select a part from the top table.");
            alert.showAndWait();
        }
    }

    @FXML
    void AddProductCancelButtonHandler(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Cancel");
        alert.setContentText("Are you sure you want to cancel?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Stage stage;
            Parent root;

            stage = (Stage) addProductCancelButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();  
        }
    }

    
    @FXML
    void AddProductSaveButtonHandler(ActionEvent event) throws IOException {
        if(!partsToProduct.isEmpty()) {
            if (productIDTextField != null && productNameTextField != null && event.getSource() == addProductSaveButton) {
                String partIDString = productIDTextField.getText();
                int productID = Integer.parseInt(partIDString);
                String productName = productNameTextField.getText();
                String productInvString = productInvTextField.getText();
                int productInStock = Integer.parseInt(productInvString);
                String priceString = productPriceTextField.getText();
                double price = Double.parseDouble(priceString);
                String productMaxString = productMaxTextField.getText();
                int productMax = Integer.parseInt(productMaxString);
                String productMinString = productMinTextField.getText();
                int productMin = Integer.parseInt(productMinString);
                if(productInvTextField.getText() == null) {
                    productInStock = 0;
                }
                Product newProduct = new Product(productID, productName, price, productInStock, productMax, productMin, partsToProduct);
                
                boolean hasMatch = false;
                for(Product allProducts : inventory.getProducts()) {
                    if (allProducts.getProductID() == productID) {
                        hasMatch = true;
                        break;
                    }
                }
                boolean partsSumExceeded = false;
                double partsSum = 0;
                for(Part partPrice : partTable2.getItems()) {
                    partsSum += partPriceColumn2.getCellData(partPrice);
                }
                if (partsSum > price) {
                    partsSumExceeded = true;
                }
                if (productInStock <= productMax && productInStock >= productMin && productMax >= productMin && !hasMatch && !partsSumExceeded) {    
                    Inventory.getProducts().add(newProduct);
                    
                    Stage stage;
                    Parent root;

                    stage = (Stage) addProductSaveButton.getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
                    root = loader.load();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Cannot Modify Product");
                    alert.setHeaderText("Warning! Please ensure the following:");
                    alert.setContentText("- Inventory level is within max/min limits. \n- Max has a value higher than min. "
                            + "\n- The product costs less than its parts. \n- Product ID is unique.");
                    alert.showAndWait();
                }
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cannot Add Product");
            alert.setHeaderText("Warning!");
            alert.setContentText("You must add a part to the list before saving product.");
            alert.showAndWait();
        }
    }

    @FXML
    void DeletePartButtonHandler(ActionEvent event) {
        int selectedIndex = partTable2.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Part Selected to Delete");
            alert.setContentText("Are you sure you want to delete this part?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                partTable2.getItems().remove(selectedIndex);
            }
        }
    }

    @FXML
    void SearchPartButtonHandler(ActionEvent event) {
        String searchPart = partSearchTextField.getText();
        boolean found = false;
        
        try {
            int partIDSearch = Integer.parseInt(searchPart);
            for(Part p: Inventory.allParts) {
                if(p.getPartID() == partIDSearch) {
                    found = true;
                    
                    partsToProduct = partTable2.getItems();
                    partsToProduct.add(p);
                    
                    partIDColumn2.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
                    partNameColumn2.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
                    partInvColumn2.setCellValueFactory(cellData -> cellData.getValue().inStockProperty().asObject());
                    partPriceColumn2.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
                    
                    partTable2.setItems(partsToProduct);
                }
            }
            if(found == false) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Part Not Found");
                alert.setHeaderText("Part Not Found!");
                alert.setContentText("Part ID '" + partIDSearch + "' not found.");
            }
        }
        catch(NumberFormatException e) {
            for(Part p: Inventory.allParts) {
                if(p.getName().equals(searchPart)) {
                    found = true;
                    
                    partsToProduct = partTable2.getItems();
                    partsToProduct.add(p);
                    
                    partIDColumn2.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
                    partNameColumn2.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
                    partInvColumn2.setCellValueFactory(cellData -> cellData.getValue().inStockProperty().asObject());
                    partPriceColumn2.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
                    
                    partTable2.setItems(partsToProduct);
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Part Not Found");
                    alert.setHeaderText("Part Not Found!");
                    alert.setContentText("Part Name '" + searchPart + "' not found.");
                }
            }
        }
    }
}
