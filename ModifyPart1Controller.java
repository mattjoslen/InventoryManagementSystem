package matt.joslen.project.ViewController;

import java.io.IOException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import matt.joslen.project.Model.Outsourced;
import matt.joslen.project.Model.Part;

public class ModifyPart1Controller {

    @FXML
    private Button modifyPartSaveButton;

    @FXML
    private Button modifyPartCancelButton;

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
    private TextField partCompanyNameTextField;

    @FXML
    private TextField partMinTextField;
    
    @FXML
    private ToggleGroup partLocation;
    
    @FXML
    private RadioButton inHouseButton;

    @FXML
    private RadioButton outsourcedButton;
    
    Inventory inventory;
    Part part;
    
    @FXML
    void ModifyPartCancelButtonHandler(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Cancel");
        alert.setContentText("Are you sure you want to cancel?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Stage stage;
            Parent root;

            stage = (Stage) modifyPartCancelButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
    
    public void setPart(Part part) {
        this.part = part;
        
        Outsourced tempOut = null;
        String companyName;
        Inhouse tempIn = null;
        int machineID;
        
        if(part instanceof Outsourced) {
            tempOut = (Outsourced)part;
            companyName = tempOut.getCompanyName();
            tempOut.setCompanyName(companyName);
        
            partIDTextField.setText(new Integer(tempOut.getPartID()).toString());
            partNameTextField.setText(tempOut.getName());
            partInvTextField.setText(new Integer(tempOut.getInStock()).toString());
            partPriceTextField.setText(new Double(tempOut.getPrice()).toString());
            partMaxTextField.setText(new Integer(tempOut.getMax()).toString());
            partMinTextField.setText(new Integer(tempOut.getMin()).toString());
            partCompanyNameTextField.setText(tempOut.getCompanyName());
        }
        else if(part instanceof Inhouse) {
            tempIn = (Inhouse)part;
            machineID = tempIn.getMachineID();
            tempIn.setMachineID(machineID);
            
            partIDTextField.setText(new Integer(tempIn.getPartID()).toString());
            partNameTextField.setText(tempIn.getName());
            partInvTextField.setText(new Integer(tempIn.getInStock()).toString());
            partPriceTextField.setText(new Double(tempIn.getPrice()).toString());
            partMaxTextField.setText(new Integer(tempIn.getMax()).toString());
            partMinTextField.setText(new Integer(tempIn.getMin()).toString());
            partCompanyNameTextField.setText(new Integer(tempIn.getMachineID()).toString());
        }
    }
    
    @FXML
    void ModifyPartSaveButtonHandler(ActionEvent event) throws IOException {

        if (partIDTextField != null && partNameTextField != null && event.getSource() == modifyPartSaveButton) {
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
            String companyName = partCompanyNameTextField.getText();
            if(partInvTextField.getText() == null) {
            partInStock = 0;
            }
            Part newPart = new Outsourced(partID, partName, price, partInStock, partMax, partMin, companyName);
            boolean hasMatch = false;
            if(part.getPartID() != partID) {
                for(Part allParts : inventory.getAllParts()) {
                    if (allParts.getPartID() == partID) {
                        hasMatch = true;
                        break;
                    }
                }
            }
            if (partInStock <= partMax && partInStock >= partMin && partMax >= partMin && !hasMatch) {    
                Inventory.allParts.remove(part);
                Inventory.getAllParts().add(newPart);
                
                Stage stage;
                Parent root;

                stage = (Stage) modifyPartSaveButton.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
                root = loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cannot Modify Part");
            alert.setHeaderText("Warning!");
            alert.setContentText("Please ensure that inventory level is within max/min limits, max has a value higher than min, and Part ID is unique.");
            alert.showAndWait();
            }
        } 
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cannot Add Part");
            alert.setHeaderText("Warning!");
            alert.setContentText("Error. Please Try Again.");
            alert.showAndWait();
        }
    }

    @FXML
    void InHouseButtonHandler(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        stage = (Stage) inHouseButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyPart2.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
        ModifyPart2Controller controller = loader.getController();
        controller.setPart(part);
    }

    @FXML
    void outsourcedButtonHandler(ActionEvent event) {

    }
}
