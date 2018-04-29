package matt.joslen.project.ViewController;

import java.io.IOException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import matt.joslen.project.MattJoslenProject;
import matt.joslen.project.Model.Inventory;
import matt.joslen.project.Model.Outsourced;
import matt.joslen.project.Model.Part;
import matt.joslen.project.Model.Product;

/**
 * FXML Controller class
 *
 * @author Matt
 */
public class MainScreenController {

    @FXML
    private Button partSearch;

    @FXML
    private TextField partSearchTextField;
    
    @FXML
    private TextField productSearchTextField;

    @FXML
    private TableView<Part> partsTable;
    
    @FXML
    private TableView<Product> productsTable;

    @FXML
    private TableColumn<Part, Integer> partIDColumn;

    @FXML
    private TableColumn<Part, String> partNameColumn;
    
    @FXML
    private TableColumn<Part, Integer> partInvColumn;
    
    @FXML
    private TableColumn<Part, Double> partPriceColumn;
    
    @FXML
    private TableColumn<Product, Integer> productIDColumn;
    
    @FXML
    private TableColumn<Product, String> productNameColumn;
    
    @FXML
    private TableColumn<Product, Integer> productInvColumn;
    
    @FXML
    private TableColumn<Product, Double> productPriceColumn;

    @FXML
    private Button addPartButton;

    @FXML
    private Button modifyPartButton;

    @FXML
    private Button deletePartButton;

    @FXML
    private Button productSearch;

    @FXML
    private Button addProductButton;

    @FXML
    private Button modifyProductButton;

    @FXML
    private Button deleteProductButton;

    @FXML
    private Button exitMainScreen;
    
    private MattJoslenProject project;
    
    private Inventory inventory;
    private Outsourced outsourced;
    
    public MainScreenController() {
    }
    
    @FXML
    public void initialize() {

        setInventory(inventory);
        // Part Table
        partIDColumn.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        partNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        partInvColumn.setCellValueFactory(cellData -> cellData.getValue().inStockProperty().asObject());
        partPriceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        
        // Product Table
        productIDColumn.setCellValueFactory(cellData -> cellData.getValue().productIDProperty().asObject());
        productNameColumn.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        productInvColumn.setCellValueFactory(cellData -> cellData.getValue().productInStockProperty().asObject());
        productPriceColumn.setCellValueFactory(cellData -> cellData.getValue().productPriceProperty().asObject());
    }
    
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
        
        partsTable.setItems(Inventory.getAllParts());
        productsTable.setItems(Inventory.getProducts());
    }
    
    @FXML
    void AddPartButtonHandler(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        stage = (Stage) addPartButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPart1.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void AddProductButtonHandler(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        stage = (Stage) addProductButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddProduct.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void DeletePartButtonHandler(ActionEvent event) {
        int selectedIndex = partsTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Part Selected to Delete");
            alert.setContentText("Are you sure you want to delete this part?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                partsTable.getItems().remove(selectedIndex);
            }
        }
    }

    @FXML
    void DeleteProductButtonHandler(ActionEvent event) {
        int selectedIndex = productsTable.getSelectionModel().getSelectedIndex();
        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
        
        if (selectedIndex >= 0) {
            if (selectedProduct.associatedParts != null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Cannot Delete Product");
                alert.setHeaderText("Warning!");
                alert.setContentText("This product contains a part and cannot be deleted.");
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Product Selected to Delete");
                alert.setContentText("Are you sure you want to delete this product?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    productsTable.getItems().remove(selectedIndex);
                }
            }
        }
    }

    @FXML
    void ExitMainScreenButtonHandler(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void ModifyPartButtonHandler(ActionEvent event) throws IOException {

        Part part = partsTable.getSelectionModel().getSelectedItem();
        
        if (part == null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Warning");
            alert.setContentText("Please select a part to modify.");
            alert.showAndWait();
        }
        else {
            if (part instanceof Outsourced) {
                Stage stage;
                Parent root;

                stage = (Stage) modifyPartButton.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyPart1.fxml"));
                root = loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                
                ModifyPart1Controller controller = loader.getController();
                controller.setPart(part);
            }
            else {
                Stage stage;
                Parent root;

                stage = (Stage) modifyPartButton.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyPart2.fxml"));
                root = loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                
                stage.show();
                ModifyPart2Controller controller = loader.getController();
                controller.setPart(part);
            }
        }
    }

    @FXML
    void ModifyProductButtonHandler(ActionEvent event) throws IOException {
        
        Product product = productsTable.getSelectionModel().getSelectedItem();
        if (product == null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Warning");
            alert.setContentText("Please select a product to modify.");
            alert.showAndWait();
        }
        else {
            Stage stage;
            Parent root;

            stage = (Stage) modifyProductButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyProduct.fxml"));
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            
            ModifyProductController controller = loader.getController();
            controller.setProduct(product);
        }
    }
    
    @FXML
    void SearchParts(ActionEvent event) {
        
        String searchPart = partSearchTextField.getText();
        boolean found = false;
        
        try {
            int partIDSearch = Integer.parseInt(searchPart);
            for(Part p: Inventory.allParts) {
                if(p.getPartID() == partIDSearch) {
                    found = true;
                    
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Part Found");
                    alert.setHeaderText("Part Found!");
                    alert.setContentText("Part ID " + partIDSearch + " found. Part name: " + p.getName());
                    alert.showAndWait();
                }
            }
            if(found == false) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Part Not Found");
                alert.setHeaderText("Part Not Found!");
                alert.setContentText("Part ID '" + partIDSearch + "' not found.");
                alert.showAndWait();
            }
        }
        catch(NumberFormatException e) {
            for(Part p: Inventory.allParts) {
                if(p.getName().equals(searchPart)) {
                    found = true;
                    
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Part Found");
                    alert.setHeaderText("Part Found!");
                    alert.setContentText("Part Name " + searchPart + " found. Part ID: " + p.getPartID());
                    alert.showAndWait();
                }
            }
            if(found == false) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Part Not Found");
                alert.setHeaderText("Part Not Found!");
                alert.setContentText("Part Name '" + searchPart + "' not found.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    void SearchProducts(ActionEvent event) {
        String searchProduct = productSearchTextField.getText();
        boolean found = false;
        
        try {
            int productIDSearch = Integer.parseInt(searchProduct);
            for(Product p: Inventory.products) {
                if(p.getProductID() == productIDSearch) {
                    found = true;
                    
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Product Found");
                    alert.setHeaderText("Product Found!");
                    alert.setContentText("Product ID " + productIDSearch + " found. Product name: " + p.getProductName());
                    alert.showAndWait();
                }
            }
            if(found == false) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Product Not Found");
                alert.setHeaderText("Product Not Found!");
                alert.setContentText("Product ID '" + productIDSearch + "' not found.");
                alert.showAndWait();
            }
        }
        catch(NumberFormatException e) {
            for(Product p: Inventory.products) {
                if(p.getProductName().equals(searchProduct)) {
                    found = true;
                    
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Product Found");
                    alert.setHeaderText("Product Found!");
                    alert.setContentText("Product Name " + searchProduct + " found. Product ID: " + p.getProductID());
                    alert.showAndWait();
                }
            }
            if(found == false) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Product Not Found");
                alert.setHeaderText("Product Not Found!");
                alert.setContentText("Product Name '" + searchProduct + "' not found.");
                alert.showAndWait();
            }
        }
    }
}
