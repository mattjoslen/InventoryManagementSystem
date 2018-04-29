package matt.joslen.project.ViewController;

import java.io.IOException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import matt.joslen.project.Model.Inhouse;
import matt.joslen.project.Model.Inventory;
import matt.joslen.project.Model.Part;

public class AddPart2Controller {

    @FXML
    private Button addPartSaveButton;

    @FXML
    private Button addPartCancelButton;

    @FXML
    private TextField partIDTextField;

    @FXML
    private TextField partNameTextField;

    @FXML
    private TextField partInvTextField;

    @FXML
    private TextField partPriceTextField;

    @FXML
    private TextField partMaxTextField;

    @FXML
    private TextField partMachineIDTextField;

    @FXML
    private TextField partMinTextField;
    
    @FXML
    private ToggleGroup partLocation;
    
    @FXML
    private RadioButton inHouseButton;

    @FXML
    private RadioButton outsourcedButton;
    
    private Part part;
    
    private Inventory inventory;
    
    public AddPart2Controller() {
        
    }
    
    @FXML
    public void initialize() {
        
    }
    
    @FXML
    void AddPartCancelButtonHandler(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Cancel");
        alert.setContentText("Are you sure you want to cancel?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Stage stage;
            Parent root;

            stage = (Stage) addPartCancelButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    void AddPartSaveButtonHandler(ActionEvent event) throws IOException {
        if (partIDTextField.getText() != null && partNameTextField.getText() != null && event.getSource() == addPartSaveButton) {
            String partIDString = partIDTextField.getText();
            int partID = Integer.parseInt(partIDString);
            String partName = partNameTextField.getText();
            String partInvString = partInvTextField.getText();
            int partInStock = Integer.parseInt(partInvString);
            String priceString = partPriceTextField.getText();
            double price = Double.parseDouble(priceString);
            String partMaxString = partMaxTextField.getText();
            int partMax = Integer.parseInt(partMaxString);
            String partMinString = partMinTextField.getText();
            int partMin = Integer.parseInt(partMinString);
            String machineIDString = partMachineIDTextField.getText();
            int machineID = Integer.parseInt(machineIDString);
            if(partInvTextField.getText() == null) {
                partInStock = 0;
            }
            Part newPart = new Inhouse(partID, partName, price, partInStock, partMax, partMin, machineID);
            
            boolean hasMatch = false;
            for(Part allParts : inventory.getAllParts()) {
                if (allParts.getPartID() == partID) {
                    hasMatch = true;
                    break;
                }
            }
            if (partInStock <= partMax && partInStock >= partMin && partMax >= partMin && !hasMatch) {    
                Inventory.getAllParts().add(newPart);
            
                Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
                stage.setScene(scene);
                stage.show();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Cannot Add Part");
                alert.setHeaderText("Warning!");
                alert.setContentText("Please ensure that inventory level is within max/min limits, max has a value higher than min, and Part ID is unique.");
                alert.showAndWait();
            }
        } else {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cannot Add Part");
        alert.setHeaderText("Warning!");
        alert.setContentText("Error. Please Try Again.");
        alert.showAndWait();
        }
    }

    @FXML
    void InHouseButtonHandler(ActionEvent event) throws IOException {
        
    }

    @FXML
    void outsourcedButtonHandler(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        stage = (Stage) outsourcedButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPart1.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
